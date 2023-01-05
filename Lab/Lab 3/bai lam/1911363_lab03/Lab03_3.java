import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Scanner;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.spec.SecretKeySpec;

public class Lab03_3 {
   static String readFile(String filename)
   {
      String content = null;
      File file = new File(filename);
      FileReader reader = null;
      try {
         reader = new FileReader(file);
         char[] chars = new char[(int) file.length()];
         reader.read(chars);
         content = new String(chars);
         reader.close();
      } catch (IOException e) {
         e.printStackTrace();
      } finally {
         if(reader != null){
            try {
               reader.close();
            } catch (IOException e) {
               e.printStackTrace();
            }
         }
      }
      return content;
   }
   
   static void writeFile(String fileName, String content) {
      File file = new File(fileName);
      try {
         Files.write( Paths.get(fileName), content.getBytes());
      } catch (IOException e) {
      	// TODO Auto-generated catch block
         e.printStackTrace();
      }
   }
   
   static private void processFileDES(String fileName, Cipher crypt, File desFile) {
      FileInputStream fis;
      FileOutputStream fos;
      CipherInputStream cis;
      try {
         fis = new FileInputStream(fileName);
         cis = new CipherInputStream(fis, crypt);
      	
      	// Write to the Encrypted/Decryped file
         fos = new FileOutputStream(desFile);
         byte[] b = new byte[8];
         int i = cis.read(b);
         while (i != -1) {
            fos.write(b, 0, i);
            i = cis.read(b);
         }
         fos.flush();    
         fos.close();    
         cis.close();
         fis.close();	
      } catch (IOException e) {
         e.printStackTrace();
      }
   }
   
   static private void encryptDES(String fileName, String DESKeyFileName)
   		throws Exception {
      File desFile = new File(fileName.substring(0, fileName.length() - 4) + ".enc");
   	
   	// Create a Secret key
      byte key[] = readFile(DESKeyFileName).getBytes();
      SecretKeySpec secretKey = new SecretKeySpec(key,"DES");
   	
   	// Create Cipher object
      Cipher encrypt = Cipher.getInstance("DES/ECB/PKCS5Padding");
      encrypt.init(Cipher.ENCRYPT_MODE, secretKey);
   	
      processFileDES(fileName, encrypt, desFile);
   }
   
   static private void decryptDES(String fileName, String DESKeyFileName)
   		throws Exception {
      File desFile = new File(fileName.substring(0, fileName.length() - 4) + ".dec");
   	
   	// Create a Secret key
      byte key[] = readFile(DESKeyFileName).getBytes();
      SecretKeySpec secretKey = new SecretKeySpec(key,"DES");
   	
   	// Create Cipher object
      Cipher decrypt = Cipher.getInstance("DES/ECB/PKCS5Padding");
      decrypt.init(Cipher.DECRYPT_MODE, secretKey);
   	
      processFileDES(fileName, decrypt, desFile);
   	
   }
   
   static private void doGenkey(String fileBase)
    		throws java.security.NoSuchAlgorithmException,
    		       java.io.IOException {
      KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
      kpg.initialize(2048);
      KeyPair kp = kpg.generateKeyPair();
      FileOutputStream out1 = new FileOutputStream(fileBase + ".key");
      out1.write(kp.getPrivate().getEncoded());
    		
      FileOutputStream out2 = new FileOutputStream(fileBase + ".pub");
      out2.write(kp.getPublic().getEncoded());
   }
   
   static public byte[] sign(String data, String pvtKeyFile) 
   throws InvalidKeyException, Exception { 
   
      byte[] bytes = Files.readAllBytes(Paths.get(pvtKeyFile));
      PKCS8EncodedKeySpec ks = new PKCS8EncodedKeySpec(bytes);
      KeyFactory kf = KeyFactory.getInstance("RSA");
      PrivateKey pvt = kf.generatePrivate(ks);
   	
      Signature dsa = Signature.getInstance("SHA1withRSA"); 
      dsa.initSign(pvt);
      dsa.update(data.getBytes());
      return dsa.sign();
   }
   
   static public boolean verifySignature(byte[] data, byte[] signature, String pubKeyFile) 
   throws Exception {
      byte[] bytes = Files.readAllBytes(Paths.get(pubKeyFile));
      X509EncodedKeySpec ks = new X509EncodedKeySpec(bytes);
      KeyFactory kf = KeyFactory.getInstance("RSA");
      PublicKey pub = kf.generatePublic(ks);
   	
      Signature sig = Signature.getInstance("SHA1withRSA");
      sig.initVerify(pub);
      sig.update(data);	
      return sig.verify(signature);	
   }
	
   static private void signAndEncrypt(String fileName, String pvtKeyFile, String DESKeyFileName)
   	throws InvalidKeyException, Exception {
      String message = readFile(fileName);
   
   	//Sign
      byte[] signStr = sign(message, pvtKeyFile);
      String str = Base64.getEncoder().encodeToString(signStr);
      System.out.println("Message: " + message);
      System.out.println("Sign: " + str);
      writeFile(fileName.substring(0, fileName.length() - 4) + "_s.txt", str);
      encryptDES(fileName, DESKeyFileName);
      encryptDES(fileName.substring(0, fileName.length() - 4) + "_s.txt", DESKeyFileName);
   }
   
   static private void DecryptAndVerify(String fileName, String signFileName, String pubKeyFile, String DESKeyFileName)
   	throws Exception {
   	
      decryptDES(fileName, DESKeyFileName);
      decryptDES(signFileName, DESKeyFileName);
   	
      String message = readFile(fileName.substring(0, fileName.length() - 3) + "dec");
   	
      String str = readFile(signFileName.substring(0, signFileName.length() - 3) + "dec");
      byte[] signStr = Base64.getDecoder().decode(str);
   
   	//Verify
      boolean result = verifySignature(message.getBytes(), signStr, pubKeyFile);
      System.out.println("Result: " + result);
   }
	
   public static void main(String[] args) throws Exception {
      Scanner cin = new Scanner(System.in);
      System.out.println("Type \"1\" for key generation");
      System.out.println("Type \"2\" for encryption and signature");
      System.out.println("Type \"3\" for decryption and signature verification");
      int choice = cin.nextInt();
      cin.nextLine();
      if (choice == 1) {
         System.out.println("Enter the key file");
         String keyBase = cin.nextLine();
         doGenkey(keyBase);
      }
      else if (choice == 2) {
         System.out.println("Enter the file needed to encrypted and signed:");
         String fileName = cin.nextLine();
         System.out.println("Enter the private key file");
         String pvtKeyFile = cin.nextLine();
         System.out.println("Enter the DES key file");
         String DESKeyFile = cin.nextLine();
         signAndEncrypt(fileName, pvtKeyFile, DESKeyFile);
      	
      }
      else if (choice == 3) {
         System.out.println("Enter the file needed to decrypted and verified");
         String fileName = cin.nextLine();
         System.out.println("Enter the signature file");
         String signFileName = cin.nextLine();
         System.out.println("Enter the public key file");
         String pubKeyFile = cin.nextLine();
         System.out.println("Enter the DES key file");
         String DESKeyFile = cin.nextLine();
         DecryptAndVerify(fileName, signFileName, pubKeyFile, DESKeyFile);
      }
   	
      cin.close();
   }	

}