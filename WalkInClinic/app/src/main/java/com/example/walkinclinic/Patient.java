package com.example.walkinclinic;

public class Patient {
    public String _fName;
    public String _lName;
    public String _address;
    public String _age;
    public String _email;
    public String _phone;
    public String _user;
    public String _password;

    public Patient(){

    }

    public Patient(String first, String last, String add, String x, String mail, String phoneNum, String userName, String pass){
        _fName = first;
        _lName = last;
        _address = add;
        _age = x;
        _email = mail;
        _phone = phoneNum;
        _user = userName;
        _password = pass;
    }
}
