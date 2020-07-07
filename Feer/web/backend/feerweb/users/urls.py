from django.urls import path
from .views import views


from django.contrib.staticfiles.urls import staticfiles_urlpatterns

app_name='users'

urlpatterns=[
    path('',views.users,name='users'),
    path('test/',views.test,name='test'),
    path('activate/',views.activateEmail,name="activateEmail"),
    path('login/',views.Login,name="login"),
]


