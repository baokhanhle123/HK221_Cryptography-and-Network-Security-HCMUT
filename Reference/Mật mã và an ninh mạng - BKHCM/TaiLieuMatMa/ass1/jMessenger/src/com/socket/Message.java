package com.socket;

import java.io.Serializable;

public class Message implements Serializable{
    
    private static final long serialVersionUID = 1L;
    public String type, sender, content, recipient, key1, key2;
    
    public Message(String type, String sender, String content, String recipient, String key1, String key2){
        this.type = type; this.sender = sender; this.content = content; this.recipient = recipient; this.key1 = key1; this.key2 = key2;
    }
    
    @Override
    public String toString(){
        return "{type='"+type+"', sender='"+sender+"', content='"+content+"', recipient='"+recipient+"'}"+"', key1='"+key1+"'}"+"', key2='"+key2+"'}";
    }
}
