package com.example.walkinclinic;

public class Patient {
    public String _fName;
    public String _lName;
    public String _address;
    public String _postalCode;
    public int _age;
    public String _email;
    public String _phone;
    public String _user;
    public String _password;
    public String _iD;
    public String _gender;

    public Patient(){

    }

    public Patient(String id, String first, String last, String add, String zip, int x, String mail, String phoneNum, String userName, String pass, String gender){
        _iD = id;
        _fName = first;
        _lName = last;
        _address = add;
        _postalCode = zip;
        _age = x;
        _email = mail;
        _phone = phoneNum;
        _user = userName;
        _password = pass;
        _gender = gender;
    }
}
