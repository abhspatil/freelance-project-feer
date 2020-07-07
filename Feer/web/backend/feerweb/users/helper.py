
from __future__ import absolute_import, unicode_literals

from celery import task
from cryptography.fernet import Fernet


class EncryptDecrypt:

    def __init__(self):
        self.KEY = b'4wr3DO_3f1RFvp5180MptP4hoM7iAJkGC8QUcVJrdPs='
        self.cipher_suite = Fernet(self.KEY)

    def encrypt(self,text):
        encoded_text = self.cipher_suite.encrypt(bytes(text, 'utf-8'))
        return encoded_text.decode("utf-8")

    def decrypt(self,text):
        decoded_text = self.cipher_suite.decrypt(bytes(text,'utf-8'))
        return decoded_text.decode("utf-8")

