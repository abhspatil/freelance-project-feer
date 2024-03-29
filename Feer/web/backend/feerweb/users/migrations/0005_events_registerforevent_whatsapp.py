# Generated by Django 3.0.7 on 2020-06-25 14:36

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('users', '0004_auto_20200625_0906'),
    ]

    operations = [
        migrations.CreateModel(
            name='Events',
            fields=[
                ('id', models.AutoField(primary_key=True, serialize=False)),
                ('eventName', models.CharField(max_length=250)),
                ('eventDescription', models.CharField(max_length=1000)),
            ],
        ),
        migrations.CreateModel(
            name='RegisterforEvent',
            fields=[
                ('id', models.AutoField(primary_key=True, serialize=False)),
                ('userId', models.IntegerField()),
                ('eventId', models.IntegerField()),
            ],
        ),
        migrations.CreateModel(
            name='Whatsapp',
            fields=[
                ('id', models.AutoField(primary_key=True, serialize=False)),
                ('link', models.CharField(max_length=250)),
                ('users', models.IntegerField(default=0)),
            ],
        ),
    ]
