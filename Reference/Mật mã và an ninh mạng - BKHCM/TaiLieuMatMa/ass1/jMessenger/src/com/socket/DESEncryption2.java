package com.socket;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.spec.SecretKeySpec;



public class DESEncryption2 {

	
	public DESEncryption2() {
		// TODO Auto-generated constructor stub
	}
	
	public void DESFileEncrypt(String input, String output){
		try {
			FileInputStream fis = new FileInputStream(input);
			FileOutputStream fos = new FileOutputStream(output);
			byte[] key = "NUYGKHKT".getBytes();
			SecretKeySpec secretKey = new SecretKeySpec(key, "DES");
			Cipher cipher = Cipher.getInstance("DES");
			cipher.init(Cipher.ENCRYPT_MODE, secretKey);
			CipherOutputStream cos = new CipherOutputStream(fos, cipher);
			byte[] buffer = new byte[1024];
			int read;
			while((read=fis.read(buffer)) != -1){
				cos.write(buffer,0,read);
			}
			fis.close();
			fos.flush();
			cos.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void DESFileDecrypt(String input, String output){
		try {
			FileInputStream fis = new FileInputStream(input);
			FileOutputStream fos = new FileOutputStream(output);
			byte[] key = "NUYGKHKT".getBytes();
			SecretKeySpec secretKey = new SecretKeySpec(key, "DES");
			Cipher cipher = Cipher.getInstance("DES");
			cipher.init(Cipher.DECRYPT_MODE, secretKey);
			CipherOutputStream cos = new CipherOutputStream(fos, cipher);
			byte[] buffer = new byte[1024];
			int read;
			while((read=fis.read(buffer)) != -1){
				cos.write(buffer,0,read);
			}
			fis.close();
			fos.flush();
			cos.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String DESStringEncrypt(String input){
		try {
			byte[] temp  = input.getBytes("UTF8");
			byte[] key = "NUYGKHKT".getBytes();
			SecretKeySpec secretKey = new SecretKeySpec(key, "DES");
			Cipher cipher;
			cipher = Cipher.getInstance("DES");
			cipher.init(Cipher.ENCRYPT_MODE, secretKey);
			byte[] encryptedBytes = cipher.doFinal(temp);
			return Base64.getEncoder().encodeToString(encryptedBytes);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return "";
	}
	
	public String DESStringDecrypt(String input){
		byte[] temp  = Base64.getDecoder().decode(input);
		byte[] key = "NUYGKHKT".getBytes();
		SecretKeySpec secretKey = new SecretKeySpec(key, "DES");
		Cipher cipher;
		try {
			cipher = Cipher.getInstance("DES");
			cipher.init(Cipher.DECRYPT_MODE, secretKey);
			byte[] decryptedBytes = cipher.doFinal(temp);
			return new String(decryptedBytes, "UTF8");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return "";
	}
}
