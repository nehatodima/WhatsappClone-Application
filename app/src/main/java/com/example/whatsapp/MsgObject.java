package com.example.whatsapp;

public class MsgObject {
    String messageID;
    String senderID;
    String message;

    public MsgObject(String messageID, String senderID, String message) {
        this.messageID = messageID;
        this.senderID = senderID;
        this.message = message;
    }

    public String getMessageID() {
        return messageID;
    }

    public void setMessageID(String messageID) {
        this.messageID = messageID;
    }

    public String getSenderID() {
        return senderID;
    }

    public void setSenderID(String senderID) {
        this.senderID = senderID;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
