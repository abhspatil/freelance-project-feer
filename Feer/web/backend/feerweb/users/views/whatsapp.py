from django.shortcuts import render
from django.conf import settings
import json
from django.http import JsonResponse
from django.views.decorators.csrf import csrf_exempt
from users.models import User
from django.core.mail import send_mail
from users.tasks import sendConfirmationMail
from users.helper import EncryptDecrypt
from django.contrib.sites.models import Site


@csrf_exempt
def whatsappApi(request):
    pass