import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.spec.SecretKeySpec;

public class Lab03_1 {
	public static String readFile(String filename)
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
	public static void main(String[] args) {
		Scanner cin = new Scanner(System.in);
		System.out.println("Nhap 1 de ma hoa, 2 de giai ma");
		int choice = cin.nextInt();
		cin.nextLine();
		if (choice == 1) {
			System.out.println("Nhap ten file can ma hoa");
			String plainFileName = cin.nextLine();
			System.out.println("Nhap ten file key");
			String keyFileName = cin.nextLine();		
			String keyCtx = readFile(keyFileName);
			System.out.println("Nhap che do:\n"
					+ "1. DES/ECB/PKCS5Padding\n"
					+ "2. DES/ECB/NoPadding\n"
					+ "3. DES/CBC/PKCS5Padding\n"
					+ "4. DES/CBC/NoPadding");
			choice = cin.nextInt();
			cin.nextLine();
			
			
			try  {
				File desFile = new File("output.enc");
				FileInputStream fis;
				FileOutputStream fos;
				CipherInputStream cis;
				
				// Create a Secret key
				byte key[] = keyCtx.getBytes();///lay noi dung key 
				SecretKeySpec secretKey = new SecretKeySpec(key,"DES");

				// Create Cipher object
				Cipher encrypt = null;
				if(choice == 1) {
					encrypt = Cipher.getInstance("DES/ECB/PKCS5Padding");
				}
				if(choice == 2) {
					encrypt = Cipher.getInstance("DES/ECB/NoPadding");
				}
				if(choice == 3) {
					encrypt = Cipher.getInstance("DES/CBC/PKCS5Padding");
				}
				if(choice == 4) {
					encrypt = Cipher.getInstance("DES/CBC/NoPadding");
				}
				
				encrypt.init(Cipher.ENCRYPT_MODE, secretKey);
				
				// Open the Plaintext file
				try {
					final long startTime = System.currentTimeMillis();
					fis = new FileInputStream(plainFileName);
					cis = new CipherInputStream(fis, encrypt);
				
					// Write to the Encrypted file
					fos = new FileOutputStream(desFile);
					byte[] b = new byte[8];
					int i = cis.read(b);
					while (i != -1) {
						fos.write(b, 0, i);
						i = cis.read(b);
					}
					final long endTime = System.currentTimeMillis();
					System.out.println("Thoi gian ma hoa: " + (endTime - startTime) + " ms");
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
		else if (choice == 2 ) {
			System.out.println("Nhap ten file can giai ma");
			String cipherFileName = cin.nextLine();
			System.out.println("Nhap ten file key");
			String keyFileName = cin.nextLine();		
			String keyCtx = readFile(keyFileName);
			System.out.println("Nhap che do:\n"
					+ "1. DES/ECB/PKCS5Padding\n"
					+ "2. DES/ECB/NoPadding\n"
					+ "3. DES/CBC/PKCS5Padding\n"
					+ "4. DES/CBC/NoPadding");
			choice = cin.nextInt();
			cin.nextLine();
			
			
		     try {
		    	 final long startTime = System.currentTimeMillis();
		         File desFile = new File("output.dec");
		         FileInputStream fis;
		         FileOutputStream fos;
		         CipherInputStream cis;

		         // Create a Secret key
		         byte key[] = keyCtx.getBytes(); 
		         SecretKeySpec secretKey = new SecretKeySpec(key,"DES");

		         // Create Cipher object
		         Cipher decrypt = null;
					if(choice == 1) {
						decrypt = Cipher.getInstance("DES/ECB/PKCS5Padding");
					}
					if(choice == 2) {
						decrypt = Cipher.getInstance("DES/ECB/NoPadding");
					}
					if(choice == 3) {
						decrypt = Cipher.getInstance("DES/CBC/PKCS5Padding");
					}
					if(choice == 4) {
						decrypt = Cipher.getInstance("DES/CBC/NoPadding");
					}
		         decrypt.init(Cipher.DECRYPT_MODE, secretKey); 

		         // Open the Encrypted file
		         fis = new FileInputStream(cipherFileName);
		         cis = new CipherInputStream(fis, decrypt); 

		         // Write to the Decrypted file
		         fos = new FileOutputStream(desFile);
		         byte[] b = new byte[8];
		         int i = cis.read(b);
		         while (i != -1) {
		              fos.write(b, 0, i);
		              i = cis.read(b);
		         }
		         final long endTime = System.currentTimeMillis();
		         System.out.println("Thoi gian giai ma: " + (endTime - startTime) + " ms");
		         fos.flush();    
		         fos.close();    
		         cis.close();
		         fis.close();
		         }  catch(Exception e){
		              e.printStackTrace();
		         }
		}
		cin.close();
	}

}
