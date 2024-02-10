package com.example.mobilestore;



public class User {

    public String name;
    public String email;
    public String password;
    public String phone;
    public String caddress;

    public User() {
    }

    public User(String name, String email, String password, String phone, String caddress) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.caddress = caddress;
    }
}
