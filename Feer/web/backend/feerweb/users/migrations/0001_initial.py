# Generated by Django 3.0.7 on 2020-06-19 10:03

from django.db import migrations, models


class Migration(migrations.Migration):

    initial = True

    dependencies = [
    ]

    operations = [
        migrations.CreateModel(
            name='User',
            fields=[
                ('id', models.AutoField(primary_key=True, serialize=False)),
                ('firstName', models.CharField(max_length=250)),
                ('lastName', models.CharField(max_length=250)),
                ('emailId', models.EmailField(max_length=254)),
                ('phoneNumber', models.CharField(max_length=15)),
                ('organization', models.CharField(max_length=150)),
                ('password', models.CharField(max_length=50)),
                ('gender', models.CharField(max_length=10)),
                ('profession', models.CharField(max_length=150)),
                ('collegeName', models.CharField(max_length=150)),
                ('yearOfGraduation', models.CharField(max_length=4)),
                ('linkedInId', models.CharField(max_length=50)),
            ],
        ),
    ]
