from django.db import models

# Create your models here.

class User(models.Model):
    id                  =  models.AutoField(primary_key=True)
    fullName            =  models.CharField(max_length=250)
    emailId             =  models.EmailField()
    phoneNumber         =  models.CharField(max_length=15)
    password            =  models.CharField(max_length=50)
    gender              =  models.CharField(max_length=10)
    profession          =  models.CharField(max_length=150)
    collegeName         =  models.CharField(max_length=150)
    yearOfGraduation    =  models.CharField(max_length=4)
    linkedInId          =  models.CharField(max_length=50)
    isActive            =  models.CharField(max_length=1,default="0")

    @classmethod
    def from_json(cls, data):
        return cls(**data)

    def to_json(self):
        to_serialize = ['id', 'fullName', 'emailId', 'phoneNumber','password','gender','profession',
            'collegeName','yearOfGraduation','linkedInId']
        d = {}
        for attr_name in to_serialize:
            d[attr_name] = self.attr_name
        return d

    def to_json_min(self):
        to_serialize = ['id', 'fullName', 'emailId', 'gender','profession',
            'collegeName','yearOfGraduation','linkedInId']
        d = {}
        for attr_name in to_serialize:
            d[attr_name] = getattr(self, attr_name)
        return d 

    def __unicode__(self):
	    return self.firstName


class Whatsapp(models.Model):
    id                  =  models.AutoField(primary_key=True)
    link                =  models.CharField(max_length=250)
    users               =  models.IntegerField(default=0)
