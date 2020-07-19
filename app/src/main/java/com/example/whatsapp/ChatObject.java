package com.example.whatsapp;

public class ChatObject {
    public ChatObject() {
    }

    public ChatObject(String chatID) {
        this.chatID = chatID;
    }

    public String getChatID() {
        return chatID;
    }

    public void setChatID(String chatID) {
        this.chatID = chatID;
    }

    private String chatID;
}
