import java.io.*;
import javax.crypto.*;
import javax.crypto.spec.*;

public class DecryptFile {
     public static void main(String args[]) {
     try {
     File desFile = new File("encrypt.des");
     File desFileBis = new File("decrypt.doc");
     FileInputStream fis;
     FileOutputStream fos;
     CipherInputStream cis;

     // Create a Secret key
     byte key[] = "abcdEFGH".getBytes(); 
     SecretKeySpec secretKey = new SecretKeySpec(key,"DES");

     // Create Cipher object
     Cipher decrypt = Cipher.getInstance("DES/ECB/PKCS5Padding");
     decrypt.init(Cipher.DECRYPT_MODE, secretKey); 

     // Open the Encrypted file
     fis = new FileInputStream(desFile);
     cis = new CipherInputStream(fis, decrypt); 

     // Write to the Decrypted file
     fos = new FileOutputStream(desFileBis);
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
     }  catch(Exception e){
          e.printStackTrace();
     }
     }
}
