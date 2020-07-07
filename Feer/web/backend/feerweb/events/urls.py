from django.urls import path
from .views import views


from django.contrib.staticfiles.urls import staticfiles_urlpatterns

app_name='events'

urlpatterns=[
    path('',views.users,name='users'),
]


