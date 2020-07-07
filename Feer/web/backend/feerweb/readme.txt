
running postgresql 

    $ docker run --name feer-postgres -e POSTGRES_PASSWORD=pass@123 -p 5432:5432 -d postgres
    $ docker-compose ps 
        -> copy <imageid>
    $ docker exec -it <imageid> bash

    or use single command 

    $ docker run --name feercore-postgres -e POSTGRES_PASSWORD=pass@123 -e POSTGRES_DB=feerweb_prod -e POSTGRES_USER=root -p 5432:5432 postgres
    - it opens a root shell of postgres image

    run $ su postgres
        $ psql 

        now it opens psql shell run the below commands

           $ create database feerweb_prod;
           $ create user root with encrypted password 'pass@123';
           $ grant all privileges on database feerweb_prod to root;

    now exit
    - ctrl + d

--------------------------------------------------------------------------------------------


running application server
 - create virtual env of python
 - activate venv
 - python3 -m requirements.txt
 - python3 manage.py migrate
 - python3 manage.py makemigrations
 - python3 manage.py runserver 0.0.0.0:8000

--------------------------------------------------------------------------------------------


Model conventions

class User(models.Model):
    id                  =  models.AutoField(primary_key=True)
    firstName           =  models.CharField(max_length=250)
    lastName            =  models.CharField(max_length=250)
    emailId             =  models.EmailField()
    phoneNumber         =  models.CharField(max_length=15)
    password            =  models.CharField(max_length=50)
    gender              =  models.CharField(max_length=10)
    profession          =  models.CharField(max_length=150)
    collegeName         =  models.CharField(max_length=150)
    yearOfGraduation    =  models.CharField(max_length=4)
    linkedInId          =  models.CharField(max_length=50)
    isActive            =  models.CharField(max_length=1,default="0")


   - emailId must be gmail and outlook as of now 
   - phoneNumber must be 10 digits
   - gender Male/Female

---------------------------------------------------------------------------------------------

EndPoints

 - take a note about status True/False in json body for Successfully and unsuccessful query, we need to 
   handle accordigly in frontend

POST http://localhost:8000/api/v1/users/

body json data 
    {
    "firstName":"Patil123",
    "lastName":"Patil",
    "emailId":"patil@gmail.com",
    "phoneNumber":"7878787878",
    "password":"pass123",
    "gender":"Male",
    "profession":"student",
    "collegeName":"JSSSTU",
    "yearOfGraduation":"2020",
    "linkedInId":"lnkd.in"
    }

return :
    200      -    {"status":True,"details":"User Created Successfully"}
    5xx      -    {"status":False,"details": <Error Info>}

GET http://localhost:8000/api/v1/users?userId=<int>

    {
        "id": 1,
        "firstName": "Patil123",
        "lastName": "Patil",
        "emailId": "abhs@gmail.com",
        "gender": "Male",
        "profession": "student",
        "collegeName": "JSSSTU",
        "yearOfGraduation": "2020",
        "linkedInId": "lnkd.in",
        "isActive": "1",
        "status": true
    }

GET http://localhost:8000/api/v1/users

    {
        "users": [
            {
                "id": 1,
                "firstName": "Patil123",
                "lastName": "Patil",
                "emailId": "abhs@gmail.com",
                "gender": "Male",
                "profession": "student",
                "collegeName": "JSSSTU",
                "yearOfGraduation": "2020",
                "linkedInId": "lnkd.in",
                "isActive": "0"
            },
            {
                "id": 2,
                "firstName": "Patil123",
                "lastName": "Patil",
                "emailId": "abhs@gmail.com",
                "gender": "Male",
                "profession": "student",
                "collegeName": "JSSSTU",
                "yearOfGraduation": "2020",
                "linkedInId": "lnkd.in",
                "isActive": "0"
            },
            {
                "id": 3,
                "firstName": "Patil11113",
                "lastName": "Patil",
                "emailId": "abhs@gmail.com",
                "gender": "Male",
                "profession": "student",
                "collegeName": "JSSSTU",
                "yearOfGraduation": "2020",
                "linkedInId": "lnkd.in",
                "isActive": "0"
            }
        ],
        "status": true
    }

DELETE http://localhost:8000/api/v1/users?userId=<int>

    {"status":True,"details":"user deleted successfully"}
    {"status":False,"details":<Error Info>}


PUT  : currently method not exist

----------------------------------------------

Celery
for running Celery
$ celery -A feerweb worker -l info 
