/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.socket;

/**
 *
 * @author DELL
 */
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Cipher;
import javax.xml.bind.DatatypeConverter;

public class MD5Checksum {
    public MD5Checksum(){

    }

    public String fileToMD5Convert(String filePath){
            byte[] b;
            try {
                System.out.println(filePath);
                    b = Files.readAllBytes(Paths.get(filePath));
                    byte[] hash = MessageDigest.getInstance("MD5").digest(b);
                    System.out.println(hash.toString());
                    return DatatypeConverter.printHexBinary(hash);
            } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
            } 

            return "";
    }
    public String fileToMD5Convert(File filea){
            byte[] b;
            try {
                System.out.println(Paths.get(filea.getPath()));
                    b = Files.readAllBytes(Paths.get(filea.getPath()));
                    byte[] hash = MessageDigest.getInstance("MD5").digest(b);
                    System.out.println(hash);
                    return DatatypeConverter.printHexBinary(hash);
            } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
            } 

            return "";
    }
}
