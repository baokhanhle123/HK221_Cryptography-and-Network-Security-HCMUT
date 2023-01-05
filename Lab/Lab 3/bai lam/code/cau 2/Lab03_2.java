import java.io.*;
import javax.crypto.*;
import javax.crypto.spec.*;
import java.util.*;
import java.security.*;
import java.security.spec.*;
import java.nio.file.Files;
import java.nio.file.Paths;

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
      System.out.println("Encryption time: " + (endTime - startTime) + " ms");
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
      System.out.println("Decryption time: " + (endTime - startTime) + " ms");
   }
   
   public static void main(String[] args)
    		throws Exception {
      Scanner cin = new Scanner(System.in);
      System.out.println("Type \"1\" for key generation");
      System.out.println("Type \"2\" for encryption");
      System.out.println("Type \"3\" for decryption");
      int choice = cin.nextInt();
      cin.nextLine();
      if (choice == 1) {
         System.out.println("Enter the key file:");
         String keyBase = cin.nextLine();
         doGenkey(keyBase);
      }
      else if (choice == 2) {
         System.out.println("Enter the plaintext file:");
         String plainFileName = cin.nextLine();
         System.out.println("Enter the public key file:");
         String keyFileName = cin.nextLine();
         doEncryptRSAWithAES(plainFileName, keyFileName);		
      }
      else if (choice == 3) {
         System.out.println("Enter the ciphertext file:");
         String cipherFileName = cin.nextLine();
         System.out.println("Enter the private key file");
         String keyFileName = cin.nextLine();
         doDecryptRSAWithAES(cipherFileName, keyFileName);
      }
      
      cin.close();
   }
}
