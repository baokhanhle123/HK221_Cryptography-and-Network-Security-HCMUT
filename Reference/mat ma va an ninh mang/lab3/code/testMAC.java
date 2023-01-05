package lab3;
import java.io.*;
import java.security.*;
import javax.crypto.*;

class testMAC {

	public static void main(String[] args) throws Exception{
   
		// Generate a key for the HMAC-MD5 keyed-hashing algorithm
		// In practice, you would save this key.
		KeyGenerator keyGen = KeyGenerator.getInstance("HmacMD5");
		SecretKey key = keyGen.generateKey();

		// Create a MAC object using HMAC-MD5 and initialize with key
		Mac mac = Mac.getInstance(key.getAlgorithm());
		mac.init(key);

		String str = "This message will be digested";
		// Encode the string into bytes using utf-8 and digest it
		byte[] utf8 = str.getBytes("UTF8");
		byte[] digest = mac.doFinal(utf8);
		System.out.println("HMAC = "+ new String(digest, "UTF8"));	
	}
}

    