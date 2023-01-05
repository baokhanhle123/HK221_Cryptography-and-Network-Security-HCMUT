import java.util.Scanner;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
//import java.util.Base64;
import java.util.Arrays;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.spec.IvParameterSpec;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Signature;
public class Lab03_2 {
	static SecureRandom srandom = new SecureRandom();
    static private void processFile(Cipher ci,InputStream in,OutputStream out)
	throws javax.crypto.IllegalBlockSizeException, 
		   javax.crypto.BadPaddingException,
		   java.io.IOException
	{
		byte[] ibuf = new byte[1024];
		int len;
		while ((len = in.read(ibuf)) != -1) {
			byte[] obuf = ci.update(ibuf, 0, len);
			if ( obuf != null ) out.write(obuf);
		}
		byte[] obuf = ci.doFinal();
		if ( obuf != null ) out.write(obuf);
    }
    static private void doGenkey(String fileBase)
    		throws java.security.NoSuchAlgorithmException,
    		       java.io.IOException
    	    {
    		KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
    		kpg.initialize(2048);
    		KeyPair kp = kpg.generateKeyPair();
    		FileOutputStream out1 = new FileOutputStream(fileBase + ".key");
    		out1.write(kp.getPrivate().getEncoded());
    		
    		FileOutputStream out2 = new FileOutputStream(fileBase + ".pub");
    		out2.write(kp.getPublic().getEncoded());
    	    }
    static private void doEncryptRSAWithAES(String inputFile, String pubKeyFile)
	throws java.security.NoSuchAlgorithmException,
	       java.security.InvalidAlgorithmParameterException,
	       java.security.InvalidKeyException,
	       java.security.spec.InvalidKeySpecException,
	       javax.crypto.NoSuchPaddingException,
	       javax.crypto.BadPaddingException,
	       javax.crypto.IllegalBlockSizeException,
	       java.io.IOException
    {
    final long startTime = System.currentTimeMillis();
    
	byte[] bytes = Files.readAllBytes(Paths.get(pubKeyFile));
	X509EncodedKeySpec ks = new X509EncodedKeySpec(bytes);
	KeyFactory kf = KeyFactory.getInstance("RSA");
	PublicKey pub = kf.generatePublic(ks);
	
	KeyGenerator kgen = KeyGenerator.getInstance("AES");
	kgen.init(128);
	SecretKey skey = kgen.generateKey();

	byte[] iv = new byte[128/8];
	srandom.nextBytes(iv);
	IvParameterSpec ivspec = new IvParameterSpec(iv);

	FileOutputStream out = new FileOutputStream(inputFile + ".enc");
	Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
	cipher.init(Cipher.ENCRYPT_MODE, pub);
	byte[] b = cipher.doFinal(skey.getEncoded());
	out.write(b);
	System.err.println("AES Key Length: " + b.length);
	
	out.write(iv);
	System.err.println("IV Length: " + iv.length);

	Cipher ci = Cipher.getInstance("AES/CBC/PKCS5Padding");
	ci.init(Cipher.ENCRYPT_MODE, skey, ivspec);
		
	FileInputStream in = new FileInputStream(inputFile);
	processFile(ci, in, out);
	final long endTime = System.currentTimeMillis();
	System.out.println("Thoi gian ma hoa: " + (endTime - startTime) + " ms");
    }
    static private void doDecryptRSAWithAES(String inputFile, String pvtKeyFile)
	throws java.security.NoSuchAlgorithmException,
	       java.security.InvalidAlgorithmParameterException,
	       java.security.InvalidKeyException,
	       java.security.spec.InvalidKeySpecException,
	       javax.crypto.NoSuchPaddingException,
	       javax.crypto.BadPaddingException,
	       javax.crypto.IllegalBlockSizeException,
	       java.io.IOException
    {
    final long startTime = System.currentTimeMillis();
	byte[] bytes = Files.readAllBytes(Paths.get(pvtKeyFile));
	PKCS8EncodedKeySpec ks = new PKCS8EncodedKeySpec(bytes);
	KeyFactory kf = KeyFactory.getInstance("RSA");
	PrivateKey pvt = kf.generatePrivate(ks);

	FileInputStream in = new FileInputStream(inputFile);
	SecretKeySpec skey = null;
	{
		Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
		cipher.init(Cipher.DECRYPT_MODE, pvt);
		byte[] b = new byte[256];
		in.read(b);
		byte[] keyb = cipher.doFinal(b);
		skey = new SecretKeySpec(keyb, "AES");
	}

	byte[] iv = new byte[128/8];
	in.read(iv);
	IvParameterSpec ivspec = new IvParameterSpec(iv);

	Cipher ci = Cipher.getInstance("AES/CBC/PKCS5Padding");
	ci.init(Cipher.DECRYPT_MODE, skey, ivspec);

	FileOutputStream out = new FileOutputStream(inputFile+".ver");
	processFile(ci, in, out);
	final long endTime = System.currentTimeMillis();
	System.out.println("Thoi gian giai ma: " + (endTime - startTime) + " ms");
    }
    public static void main(String[] args)
    		throws Exception {
		Scanner cin = new Scanner(System.in);
		System.out.println("Nhap 1 de ma hoa, 2 de giai ma, 3 de tao khoa");
		int choice = cin.nextInt();
		cin.nextLine();
		if (choice == 1) {
			System.out.println("Nhap ten file can ma hoa");
			String plainFileName = cin.nextLine();
			System.out.println("Nhap ten file public key");
			String keyFileName = cin.nextLine();
			doEncryptRSAWithAES(plainFileName, keyFileName);		
		}
		else if (choice == 2) {
			System.out.println("Nhap ten file can giai ma");
			String cipherFileName = cin.nextLine();
			System.out.println("Nhap ten file private key");
			String keyFileName = cin.nextLine();
			doDecryptRSAWithAES(cipherFileName, keyFileName);
		}
		else if (choice == 3) {
			System.out.println("Nhap ten file key can tao");
			String keyBase = cin.nextLine();
			doGenkey(keyBase);
		}
		cin.close();
	}
}
