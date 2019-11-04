package com.example.walkinclinic;

public class Patient {
    private String fName;
    private String lName;
    private String address;
    private String postalCode;
    private int age;
    private String email;
    private String phone;
    private String user;
    private String password;

    public Patient(){

    }
    public Patient(String first, String last, String add, String zip, int x, String mail, String phoneNum, String userName, String pass){
        fName = first;
        lName = last;
        address = add;
        postalCode = zip;
        age = x;
        email = mail;
        phone = phoneNum;
        user = userName;
        password = pass;
    }
}
