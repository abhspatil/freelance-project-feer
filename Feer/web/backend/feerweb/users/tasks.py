from __future__ import absolute_import, unicode_literals

from celery import task
from cryptography.fernet import Fernet
from django.core.mail import send_mail
from django.conf import settings
from users.helper import EncryptDecrypt

from email.mime.multipart import MIMEMultipart
from email.mime.text import MIMEText
import smtplib
 

EMAIL_HOST = settings.EMAIL_HOST
EMAIL_HOST_PORT = settings.EMAIL_HOST_PORT
EMAIL_HOST_USER = settings.EMAIL_HOST_USER
EMAIL_HOST_PASSWORD = settings.EMAIL_HOST_PASSWORD

HOST_HTTP_S = settings.HOST_HTTP_S
FRONTEND_HOST_URL = settings.FRONTEND_HOST_URL

TEST_MAIL_HOST = settings.TEST_MAIL_HOST
TEST_MAIL_PORT = settings.TEST_MAIL_PORT
TEST_MAIL_USER = settings.TEST_MAIL_USER
TEST_MAIL_PASSWORD = settings.TEST_MAIL_PASSWORD


@task
def adding_task(x, y):
    print(x,y)
    return x + y


@task 
def sendConfirmationMail(email,baseUrl):
    
    #print("*"*100,email,baseUrl)
    encryptDecrypt = EncryptDecrypt()
    encryptedEmail = encryptDecrypt.encrypt(email)
    #print(email)
    url = HOST_HTTP_S + baseUrl + "/api/v1/users/activate?query="+encryptedEmail
    #print(f'"URL ### : {url}"')
    msg = "Thank you for registering with @Feer. Please click on below link to activate your account\n\n"+ \
        url +"\n\n"+ "Note: If you did not ask to reset password, please ignore this message" + \
            "\n\n"+"Thank you,\nAbhishek Patil, @Feer"
    
    #print(msg)
    sendMail(email,msg)

@task
def resetPassword(email):
    url = FRONTEND_HOST_URL +"/forgotpassword?email="+email

    sendMail(email,url)
    
def sendMail(email,content):

    print(f'"Content ### : {content}"')

    msg = MIMEMultipart()

    msg['From'] = EMAIL_HOST_USER
    msg['To'] = email
    msg['Subject'] = "Email Verification @Feer"

    msg.attach(MIMEText(content, 'plain'))

    server = smtplib.SMTP('smtp.gmail.com: 587')
    server.starttls()
    server.login(msg['From'], EMAIL_HOST_PASSWORD)
    server.sendmail(msg['From'], msg['To'], msg.as_string())
    
    server.quit()
    
    print(f'Email sent successfully to {msg["To"]}"')


'''
import smtplib 
    s = smtplib.SMTP(TEST_MAIL_HOST, TEST_MAIL_PORT) 
    s.starttls() 
    s.login(TEST_MAIL_USER,TEST_MAIL_PASSWORD) 
    s.sendmail(TEST_MAIL_USER, email, content)
    s.quit()


    # #print(f'"EMAIL : ## {email}"')
    # #try:
    # import smtplib 
    # s = smtplib.SMTP(EMAIL_HOST, EMAIL_HOST_PORT) 
    # s.starttls() 
    # s.login(EMAIL_HOST_USER,EMAIL_HOST_PASSWORD) 
    # s.sendmail(EMAIL_HOST_USER, email, content)
    # s.quit()
    
    # print("Sent Email")

    #except Exception:
    #    print("Some Error Happened Sending Email.. Try again or Contact @Feer")
'''