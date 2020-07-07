from django.shortcuts import render
from django.conf import settings
import json
from django.http import JsonResponse
from django.views.decorators.csrf import csrf_exempt
from .models import Events,RegisterforEvent
from django.core.mail import send_mail


@csrf_exempt
def Events(request):
    if request.method == "POST":
        json_data = json.dumps(request.body)

        if "eventName" not in json_data or "eventDescription" not in json_data:
            return JsonResponse("status":False,"details":"data not found")

    
    return JsonResponse("status":False,"details":"method not found")

