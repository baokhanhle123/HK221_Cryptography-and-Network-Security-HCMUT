package com.socket;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.io.*;
import java.util.Scanner;

public class rsa {
    public BigInteger n, d, e, p, q;
    private int bitlen = 1024;
    //public BigInteger p, q;
    /** Create an instance that can encrypt using someone elses public key. */
    public rsa(BigInteger newn, BigInteger newe) {
        n = newn;
        e = newe;
    }

    /** Create an instance that can both encrypt and decrypt. */
    public rsa(int bits) {
        bitlen = bits;
        SecureRandom r = new SecureRandom();//random ra 1 số nguyên lớn.
         p = new BigInteger(bitlen / 2, 100, r);//random so nguyen lớn p
         q = new BigInteger(bitlen / 2, 100, r);//random so nguyen lớn q
        n = p.multiply(q);
        BigInteger m = (p.subtract(BigInteger.ONE)).multiply(q
                .subtract(BigInteger.ONE));
        e = new BigInteger("3");
        while (m.gcd(e).intValue() > 1) {
            e = e.add(new BigInteger("2"));
        }
        d = e.modInverse(m);
        System.out.println(d);
        System.out.println(n);
    }

    public rsa() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /** Encrypt the given plaintext message. */
   public synchronized String encrypt(String message) {
        return (new BigInteger(message.getBytes())).modPow(e, n).toString();
    }

    /** Encrypt the given plaintext message. */
    public synchronized BigInteger encrypt(BigInteger message) {
        return message.modPow(e, n);
    }

    /** Decrypt the given ciphertext message. */
    public synchronized String decrypt(String message, BigInteger d, BigInteger n) {
        return new String((new BigInteger(message)).modPow(d, n).toByteArray());
    }

    /** Decrypt the given ciphertext message. */
    public synchronized BigInteger decrypt(BigInteger message,BigInteger  d, BigInteger n) {
        return message.modPow(d, n);
        
       
    }

    /** Return the modulus. */
    public synchronized BigInteger getN() {
        return n;
    }

    /** Return the public key. */
    public synchronized BigInteger getE() {
        return e;
    }

    /** Trivial test program. */
    public static String  Encryt(String input) {

        rsa rsa = new rsa(1024);
        String text1=input;
        BigInteger plaintext = new BigInteger(text1.getBytes());

        BigInteger ciphertext = rsa.encrypt(plaintext);
        //plaintext = rsa.decrypt(ciphertext);
        return ciphertext.toString();
    }
}
