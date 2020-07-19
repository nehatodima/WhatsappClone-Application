package com.example.whatsapp;

public class UserObject {
    public UserObject() {
    }

    private String name,phone,uid;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }


    public UserObject(String name, String phone, String uid) {
        this.name = name;
        this.phone = phone;
        this.uid = uid;
    }

    public UserObject(String name, String phone) {
        this.name = name;
        this.phone = phone;
    }
}
