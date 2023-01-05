import java.io.*;
import java.security.*;
import javax.crypto.*;
import java.security.spec.*;
import java.math.BigInteger;

public class testRSA {
	private KeyPair keyPair;

	public testRSA() throws Exception {
		keyGen();
	}
	
	//Create a RSA key pair
	public void keyGen() throws Exception{
		KeyPairGenerator keygen = KeyPairGenerator.getInstance("RSA");
		keygen.initialize(2048);
		keyPair = keygen.generateKeyPair();
	}
	
	public void savePublicKeyToFile(String fileName) throws Exception {
		KeyFactory fact = KeyFactory.getInstance("RSA");
		RSAPublicKeySpec pub = fact.getKeySpec(keyPair.getPublic(), RSAPublicKeySpec.class);
		ObjectOutputStream oout = new ObjectOutputStream(
			new BufferedOutputStream(new FileOutputStream(fileName)));
		oout.writeObject(pub.getModulus());
		oout.writeObject(pub.getPublicExponent());
		oout.close();
	}
	
	public void savePrivateKeyToFile(String fileName) throws Exception {
		KeyFactory fact = KeyFactory.getInstance("RSA");
		RSAPrivateKeySpec pri = fact.getKeySpec(keyPair.getPrivate(), RSAPrivateKeySpec.class);
		ObjectOutputStream oout = new ObjectOutputStream(
			new BufferedOutputStream(new FileOutputStream(fileName)));
		oout.writeObject(pri.getModulus());
		oout.writeObject(pri.getPrivateExponent());
		oout.close();
	}
			
	// Encrypt data with RSA
	public byte[] encrypt(String plaintext)  throws Exception {
		PublicKey key = keyPair.getPublic();
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.ENCRYPT_MODE, key);
		byte[] ciphertext = cipher.doFinal(plaintext.getBytes("UTF8"));
		return ciphertext;
	}

	// Decrypt data with RSA
	public String decrypt(byte[] ciphertext)  throws Exception{
		PrivateKey key = keyPair.getPrivate();
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.DECRYPT_MODE, key);
		byte[] plaintext = cipher.doFinal(ciphertext);
		return new String(plaintext, "UTF8");
	}

	public static void main(String args[]) throws Exception {
		System.out.println("Generate a key pair ...");
		testRSA app = new testRSA();

		app.savePublicKeyToFile("public.key");
		app.savePrivateKeyToFile("private.key");
		System.out.println("Enter a line: ");
		java.io.InputStreamReader sreader = new java.io.InputStreamReader(System.in);
		java.io.BufferedReader breader = new java.io.BufferedReader(sreader);
		String input = breader.readLine();

		System.out.println("Plaintext = " + input);

		byte[] ciphertext = app.encrypt(input);
		System.out.println("After Encryption Ciphertext = " + new String(ciphertext));
		System.out.println("After Decryption Plaintext = " + app.decrypt(ciphertext));
	}
}
		
		