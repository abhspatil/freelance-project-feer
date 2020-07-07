from cryptography.fernet import Fernet
# #key = Fernet.generate_key() #this is your "password"

# key = b'4wr3DO_3f1RFvp5180MptP4hoM7iAJkGC8QUcVJrdPs='
# cipher_suite = Fernet(key)

# print(key)

# st="Hello stackoverflow!"
# st = bytes(st, 'utf-8')

# encoded_text = cipher_suite.encrypt(st)
# decoded_text = cipher_suite.decrypt(encoded_text)

# print(encoded_text.decode("utf-8") )
# print(decoded_text.decode("utf-8") )

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


encdec = EncryptDecrypt()

out = encdec.encrypt('patil')
print(out)
out = encdec.decrypt(out)
print(out)
