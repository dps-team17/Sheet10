# Distributed Systems Lab Homework
## Security: Secret key and key pairs (10 points)
Within this assignment, some of the concepts regarding the security within distributed systems covered
within the lecture should be further investigated.
### Assignments:

#### 1. Secret key
Write your own encryption algorithm in Java to allow secure communication between two Java
sockets. The key should be the input parameter for the program and 2 Java programs should be
created that communicate encrypted using that key. Allow reading of the command line and
transfer this text to the other party, where it should be printed first as received and then
decrypted. (4 point)

#### 2. RSA: Private Public Key
Write an application that generates a RSA key pair and is started with a public key of a second
key pair is able to communicate using the RSA encryption. Again use sockets for communication.
Two instances of the application should be able to communicate safely if each is started with the
public key of the other instance. Therefore the generated key pairs must be stored in different
files (4 in total). Similar to ex1 it should be possible to input some text, get it transferred
encrypted and decrypt it again. (4 points)

#### 3. Hybrid approach
Combine ex1 and ex2 to simulate a SSL based security system. RSA is used to initiate
communication, exchange a secret key and then use the secret key for the rest of the
communication. (2 points)
Useful link: http://www.javamex.com/tutorials/cryptography/rsa_encryption.shtml