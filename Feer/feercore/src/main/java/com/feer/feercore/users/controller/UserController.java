package com.feer.feercore.users.controller;

import com.feer.feercore.users.model.Response;
import com.feer.feercore.users.model.User;
import com.feer.feercore.users.model.ValiadateUser;
import com.feer.feercore.users.resource.UserRepository;
import com.feer.feercore.users.service.EmailCfg;
import com.feer.feercore.users.service.EncryptDecrypt;
import com.feer.feercore.users.service.VerifyMail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.concurrent.*;

@RestController
@RequestMapping(value = "api/v1/users", produces = "application/hal+json")
public class UserController {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private ValiadateUser valiadateUser;

    @Autowired
    private EncryptDecrypt encryptDecrypt;

    @Autowired
    private EmailCfg emailCfg;

    @Autowired
    private JavaMailSender javaMailSender;

    @PostMapping
    public Response createUser(@RequestBody User user) throws ExecutionException, InterruptedException {
        user.setIsActive("0");

        if(!valiadateUser.validate(user)){
            return new Response(false,"invalid email/phone number");
        }

        user.setPassword(encryptDecrypt.encrypt(user.getPassword()));
        User savedUser = userRepo.save(user);

        sendConfirmationEmail(savedUser);

        return new Response(true,"user created successfully");
    }

    @GetMapping("{id}")
    public User getUser(@PathVariable(value = "id") Long userId){
        User user = userRepo.findById(userId).orElse(null);

        if(user==null){
            return null;
        }

        user.setPassword("xxx");
        user.setPhoneNumber("xxx");

        if(user.getIsActive().equals("0")){
            return null;
        }

        return user;
    }

    @GetMapping
    public List<User> getAllUsers(){
        List<User> users = userRepo.findAll();

        for(User user: users){
            user.setPassword("xxx");
            user.setPhoneNumber("xxx");
        }

        return users;
    }

    @PutMapping("{id}")
    public User updateUser(@PathVariable(value = "id") Long userId, @RequestBody User newuser){
        User user = userRepo.findById(userId).orElse(null);

        if(user!=null){
            user.setFullName(newuser.getFullName());
            user.setEmailId(newuser.getEmailId());
            user.setPhoneNumber(newuser.getPhoneNumber());
            user.setPassword(encryptDecrypt.encrypt(newuser.getPassword()));
            user.setGender(newuser.getGender());
            user.setProfession(newuser.getProfession());
            user.setCollegeName(newuser.getCollegeName());
            user.setYearOfGraduation(newuser.getYearOfGraduation());
            user.setLinkedInId(newuser.getLinkedInId());
            user.setIsActive(newuser.getIsActive());

            userRepo.save(user);
        }else{
            userRepo.save(newuser);
        }

        return user;
    }

    @DeleteMapping("{id}")
    public Response deleteUser(@PathVariable(value = "id") Long userId) throws Exception {
        User user = userRepo.findById(userId).orElse(null);

        if (user == null) {
            return new Response(false, "user not found");
        }

        userRepo.delete(user);

        return new Response(true, "user deleted successfully");
    }

    @GetMapping("/activate/{salt}")
    public Response activateUser(@PathVariable(value = "salt") String salt){
        String emailId = encryptDecrypt.decrypt(salt);
        User user = userRepo.findByEmailId(emailId);

        if(user==null){
            return new Response(false,"email verification failed, user does not exist with given email");
        }

        user.setIsActive("1");

        return new Response(true,"email verified successfully");
    }

    public void sendConfirmationEmail(User user) throws ExecutionException, InterruptedException {

        ExecutorService service = Executors.newFixedThreadPool(10);

//        String id = user.getEmailId();
//        String encryptedId = encryptDecrypt.encrypt(id);
//
//        String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
//        baseUrl += "/api/v1/users/activate/"+encryptedId;
//
//        SimpleMailMessage mailMessage = new SimpleMailMessage();
//
//        mailMessage.setFrom(this.emailCfg.getUsername());
//        mailMessage.setTo(user.getEmailId());
//        mailMessage.setSubject("Email Confirmation from @Feer");
//        mailMessage.setText(baseUrl);
//
//        //Send mail
//        javaMailSender.send(mailMessage);
        String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();

        Callable callable = () ->{
            String id = user.getEmailId();
            String encryptedId = encryptDecrypt.encrypt(id);

            String endurl = "/api/v1/users/activate/"+encryptedId;

            SimpleMailMessage mailMessage = new SimpleMailMessage();

            mailMessage.setFrom(this.emailCfg.getUsername());
            mailMessage.setTo(user.getEmailId());
            mailMessage.setSubject("Email Confirmation from @Feer");
            mailMessage.setText(baseUrl+endurl);

            //Send mail
            javaMailSender.send(mailMessage);
            System.out.println("Email sent");

            return "sent successfully";
        };

        Future submit = service.submit(callable);

        System.out.println(submit.get());
    }
}