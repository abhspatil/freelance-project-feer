
from django.shortcuts import render
from django.conf import settings
import json
from django.http import JsonResponse
from django.views.decorators.csrf import csrf_exempt
from users.models import User
from django.core.mail import send_mail
from users.tasks import sendConfirmationMail, resetPassword
from users.helper import EncryptDecrypt
#from django.contrib.sites.models import Site


# https://stackoverflow.com/questions/2345708/how-can-i-get-the-full-absolute-url-with-domain-in-django

@csrf_exempt
def users(request):
    if request.method == "POST":
        json_data=json.loads(request.body)

        validate = Validate(json_data).isValid()
        
        if not validate["status"]:
            return JsonResponse(validate)

        users = User.objects.filter(emailId = json_data["emailId"])
        if len(users) != 0:
            return JsonResponse({"status":False,"details":"user with email already exist"})

        print(json_data)

        try:
            user = User(**json_data)
            user.save()
        except:
            return JsonResponse({"status":False,"details":"some data not found"})

        baseUrl = request.META['HTTP_HOST']

        confirmationMail(baseUrl,user)

        return JsonResponse({"status":True,"details":"User Created Successfully"})

    elif request.method == "GET":
        if "userId" in request.GET:
            id = request.GET.get("userId")
            print(id)
            users = User.objects.filter(id=id)
            if len(users)==0:
                return JsonResponse({"status":False,"details":"user does not exist"})

            data = user_to_json(users[0])

            if data["isActive"] == "0":
                return JsonResponse({"status":False,"details":"Email Not Varified"})
            
            data["status"]=True

            return JsonResponse(data)

        elif "emailId" in request.GET:
            emailId = request.GET.get("emailId")
            print(emailId)
            users = User.objects.filter(emailId=emailId)
            if len(users)==0:
                return JsonResponse({"status":False,"details":"user does not exist"})

            data = user_to_json(users[0])

            if data["isActive"] == "0":
                return JsonResponse({"status":False,"details":"Email Not Varified"})
            
            data["status"]=True

            return JsonResponse(data)
            
        else:
            users = User.objects.all()

            json_data={"users":[]}
            json_data["status"]=False

            for user in users:
                json_data["users"].append(user_to_json(user))

            if len(json_data["users"]) > 0:
                json_data["status"]=True 
                
            return JsonResponse(json_data)

        return JsonResponse({"status":False,"details":"Invalid parameter"})

    elif request.method == "PUT":
        return JsonResponse({"status":False,"details":"method not allowed"})

    elif request.method == "DELETE":
        if "userId" not in request.GET and "emailId" not in request.GET:
            return JsonResponse({"status":False,"details":"user id/email required"})

        if "userId" in request.GET:

            try:
                user = User.objects.get(id=request.GET.get("userId"))
                user.delete()
                return JsonResponse({"status":True,"details":"user deleted successfully"})
            
            except:
                return JsonResponse({"status":False,"details":"user id not found"})
        
        elif "emailId" in request.GET:

            try:
                user = User.objects.get(emailId=request.GET.get("emailId"))
                user.delete()
                return JsonResponse({"status":True,"details":"user deleted successfully"})
            
            except:
                return JsonResponse({"status":False,"details":"user email not found"})


    else:
        return JsonResponse({"status":False,"details":"Invalid method request"})
    
@csrf_exempt
def Login(request):
    if request.method == "POST":
        json_data=json.loads(request.body)

        if "emailId" not in json_data or "password" not in json_data:
            return JsonResponse({"status" : False, "deatail":" email and password required"})

        print(json_data)

        emailId = json_data['emailId']
        password = json_data['password']

        print(emailId,password)
        user = User.objects.filter(emailId=emailId)
        print(user)
        if len(user)==0:
            return JsonResponse({"status":False,"details":"user does not exist"})

        if user[0].isActive == "0":
            return JsonResponse({"status":False,"details":"email not varified"})
            
        if password == user[0].password:
            return JsonResponse({"status":True,"details":"success"})

        return JsonResponse({"status":False,"details":"email/password does not match"})

    return JsonResponse({"status":False,"details":"method doesn't exist"})

def forgotEmail(request):
    if request.method == "GET":
        json_data = json.loads(request.body)
        if "emailId" not in json_data:
            return JsonResponse({"status":False,"details":"email required"})

        resetPassword(json_data["emailId"])
        
        return JsonResponse({"status":True,"details":"email sent successfully for resetting"})

    elif request.method == "POST":
        json_data = json.loads(request.body)

        if "emailId" not in json_data or "password" not in json_data:
            return JsonResponse({"status":False,"details":"email/password required"})

        try:
            user = User.objects.get(emailId = json_data["emailId"])
        except:
            return JsonResponse({"status":False,"details":"user does not exist"})

        user.password = json_data["password"]
        user.save()

        return JsonResponse({"status":True,"details":"password resent successful"})

    return JsonResponse({"status":False,"details":"method not found"})

        
def activateEmail(request):
    
    if "query" not in request.GET:
        #return JsonResponse({"status":False,"details":"query not found"})
        return render(request,"users/emailConfirmation.html",{"status":False,"message":"Email Verification Failed"})

    emailId = request.GET.get("query")

    try:
        encryptDecrypt = EncryptDecrypt()
        emailId = encryptDecrypt.decrypt(emailId)

        user = User.objects.get(emailId=emailId)

        if user.isActive=="1":
            return render(request,"users/emailConfirmation.html",{"status":False,"message":"Email already Verified"})
        user.isActive="1"
        user.save()

        #return JsonResponse({"status":True,"details":"Email Varified, Please login"})
        return render(request,"users/emailConfirmation.html",{"status":True,"message":"Email Verification Successful"})

    except:
        #return JsonResponse({"status":False,"details":"Email Varification Failed. check id and salt or time out"})
        return render(request,"users/emailConfirmation.html",{"status":False,"message":"Email Verification Failed"})

def confirmationMail(baseUrl,user):
    
    print(f'"Sending email :{user.emailId} with host : {baseUrl}"')
    sendConfirmationMail.delay(user.emailId,baseUrl)

def test(req):
    return JsonResponse({"status":True,"details": "no errors"})

class Validate:
    def __init__(self,data):
        self.data = data
        self.keys = ['fullName', 'emailId', 'gender','profession',
            'collegeName','yearOfGraduation','linkedInId']

    def isValid(self):
        
        if not self.isAllKeysPresent():
            return {"status":False,"details":"some data not found"}

        if not self.isValidEmail():
            return {"status":False,"details":"Invalid Email Id"}

        if not self.isValidPhone():
            return {"status":False,"details":"Invalid Phone Number"} 

        return {"status" : True}
    
    def isAllKeysPresent(self):

        for key in self.keys:
            if key not in self.data:
                return False
            
        return True


    def isValidEmail(self):
        print(self.data["emailId"])

        for email in ['@gmail.com', '@outlook.com']:
            if email in self.data['emailId']:
                return True

        return False

    def isValidPhone(self):
        print(self.data['phoneNumber'])
        if not self.data['phoneNumber'].isnumeric():
            return False

        return True

def user_to_json(user):

    to_serialize = ['id', 'fullName', 'emailId', 'gender','profession',
            'collegeName','yearOfGraduation','linkedInId']
    data = {}

    data["id"] = user.id 
    data["firstName"] = user.fullName
    data["id"] = user.id
    data["emailId"] = user.emailId
    data["gender"] = user.gender
    data["profession"] = user.profession
    data["collegeName"] = user.collegeName
    data["yearOfGraduation"] = user.yearOfGraduation
    data["linkedInId"] = user.linkedInId
    data["isActive"] = user.isActive

    return data 