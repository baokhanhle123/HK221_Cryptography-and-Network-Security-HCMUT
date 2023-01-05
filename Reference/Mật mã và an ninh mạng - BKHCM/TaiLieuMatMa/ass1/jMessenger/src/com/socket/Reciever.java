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
/**
 *
 * @author KienLgk
 */
public class Reciever implements Runnable{
    public ServerSocket server;
    public Socket socket;
    public int port;
    public String saveTo = "";//saveTo
    public InputStream ip;
    public FileOutputStream op;
    public ChatFrame mGUI;
    
    public Reciever(String saveTo, ChatFrame mGUI){
        try {
            server = new ServerSocket(0);
            port = server.getLocalPort();
            this.saveTo = saveTo;
            this.mGUI = mGUI;
        } 
        catch (IOException ex) {
            System.out.println("Exception [Download : Download(...)]");
        }
    }

    @Override
    public void run() {
        try {
            socket = server.accept();
            System.out.println("Download : "+socket.getRemoteSocketAddress());
            
            ip = socket.getInputStream();
            op = new FileOutputStream(saveTo);
            
            byte[] buffer = new byte[1024];
            int count;
            
            while((count = ip.read(buffer)) >= 0){
                op.write(buffer, 0, count);
            }
            
            op.flush();
            
            //mGUI.txtChatLogModify("[Application > Me] : Download complete\n");
            
            if(op != null){ op.close(); }
            if(ip != null){ ip.close(); }
            if(socket != null){ socket.close(); }
        } 
        catch (Exception ex) {
            System.out.println("Exception [Download : run(...)]");
        }
    }
}

