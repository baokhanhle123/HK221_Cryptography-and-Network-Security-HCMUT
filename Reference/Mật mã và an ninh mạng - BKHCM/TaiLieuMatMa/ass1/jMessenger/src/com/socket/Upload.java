package com.socket;

import static com.socket.IdeaFileEncryption.Mode.ECB;
import static com.socket.IdeaFileEncryption.pumpData;
import static com.socket.IdeaFileEncryption.readDataLength;
import static com.socket.IdeaFileEncryption.writeDataLength;
import com.ui.ChatFrame;
import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.spec.SecretKeySpec;

public class Upload implements Runnable{

    public String addr;
    public int port;
    public Socket socket;
    public FileInputStream In;
    public OutputStream Out;
    public File file;
    public ChatFrame ui;
    
    public Upload(String addr, int port, File filepath, ChatFrame frame){
        super();
        try {
            file = filepath; ui = frame;
            socket = new Socket(InetAddress.getByName(addr), port);
            Out = socket.getOutputStream();
            In = new FileInputStream(filepath);
        } 
        catch (Exception ex) {
            System.out.println("Exception [Upload : Upload(...)]");
        }
    }
    
    @Override
    public void run() {
        try { 
                try {
			
                    byte[] key = "NUYGKHKT".getBytes();
                    SecretKeySpec secretKey = new SecretKeySpec(key, "DES");
                    Cipher cipher = Cipher.getInstance("DES");
                    cipher.init(Cipher.ENCRYPT_MODE, secretKey);
                    CipherOutputStream cos = new CipherOutputStream(Out, cipher);
                    byte[] buffer = new byte[1024];
                    int read;
                    while((read=In.read(buffer)) != -1){
                            cos.write(buffer,0,read);

                    }
                    cos.flush();
                    cos.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
            ui.jTextArea1.append("[Applcation > Me] : File upload complete\n");
            ui.jButton5.setEnabled(true); ui.jButton6.setEnabled(true);
            ui.jTextField5.setVisible(true);
            
            if(In != null){ In.close(); }
            if(Out != null){ Out.close(); }
            if(socket != null){ socket.close(); }
        }
        catch (Exception ex) {
            System.out.println("Exception [Upload : run()]");
            ex.printStackTrace();
        }
    }
    

}