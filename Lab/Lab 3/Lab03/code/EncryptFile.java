import java.io.*;
import javax.crypto.*;
import javax.crypto.spec.*;

public class EncryptFile {
public static void main(String args[]){
	if (args.length < 1) {
		System.out.println("Usage: java EncryptFile <file name>");
		System.exit(-1);
    }
	try  {
		File desFile = new File("encrypt.des");
		FileInputStream fis;
		FileOutputStream fos;
		CipherInputStream cis;
		
		// Create a Secret key
		byte key[] = "abcdEFGH".getBytes(); 
		SecretKeySpec secretKey = new SecretKeySpec(key,"DES");

		// Create Cipher object
		Cipher encrypt = Cipher.getInstance("DES/ECB/PKCS5Padding");
		encrypt.init(Cipher.ENCRYPT_MODE, secretKey);
		
		// Open the Plaintext file
		try {
			fis = new FileInputStream(args[0]);
			cis = new CipherInputStream(fis, encrypt);
		
			// Write to the Encrypted file
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
		} catch(IOException err) {
			System.out.println("Cannot open file!");
			System.exit(-1);
		}
	} catch(Exception e){
		e.printStackTrace();
    }
  }
}
