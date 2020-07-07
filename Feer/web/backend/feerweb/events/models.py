from django.db import models

# Create your models here.

class Events(models.Model):
    id                  =  models.AutoField(primary_key=True)
    eventId             =  models.CharField(max_length=10)
    eventName           =  models.CharField(max_length=250)
    eventDescription    =  models.CharField(max_length=2000)
    eventDate           =  models.CharField(max_length=20)
    eventTime           =  models.CharField(max_length=20)
    

class RegisterforEvent(models.Model):
    id                  =  models.AutoField(primary_key=True)
    userId              =  models.IntegerField()
    eventId             =  models.IntegerField() 
