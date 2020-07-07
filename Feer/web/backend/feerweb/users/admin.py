from django.contrib import admin
from users.models import User
from import_export.admin import ImportExportModelAdmin

# Register your models here.

#admin.site.register(User)

@admin.register(User)
class UserAdmin(ImportExportModelAdmin):
    pass 