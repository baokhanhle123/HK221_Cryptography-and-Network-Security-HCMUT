/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.socket;

import java.io.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.ui.ChatFrame;

public class Sender implements Runnable{
    public String addr;
    public int port;
    public Socket socket;
    public FileInputStream ip;
    public OutputStream op;
    public File file;
    public ChatFrame mGUI;
    
    public Sender(String addr, int port, File filepath, ChatFrame mgui){
        super();
        try {
            file = filepath;
            mGUI = mgui;
            socket = new Socket(InetAddress.getByName(addr), port);
            op = socket.getOutputStream();
            ip = new FileInputStream(filepath);
        } 
        catch (Exception ex) {
            System.out.println("Exception [Upload : Upload(...)]");
        }
    }
    
    @Override
    public void run() {
        try {       
            byte[] buffer = new byte[1024];
            int count;
            
            while((count = ip.read(buffer)) >= 0){
                op.write(buffer, 0, count);
            }
            op.flush();
            
//            mGUI.txtChatLogModify("[Applcation > Me] : File upload complete\n");
//            mGUI.activeBtn("File");
//            mGUI.activeBtn("Send");
            
            if(ip != null){ 
                ip.close(); 
            }
            if(op != null){ 
                op.close(); 
            }
            if(socket != null){ 
                socket.close(); 
            }
        }
        catch (Exception ex) {
            System.out.println("Exception [Upload : run()]");
            ex.printStackTrace();
        }
    }
}