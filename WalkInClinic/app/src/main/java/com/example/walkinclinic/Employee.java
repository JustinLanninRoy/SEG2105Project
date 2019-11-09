package com.example.walkinclinic;

public class Employee {
    public String fName;
    public String lName;
    public String employeeNum;
    public String clinic;
    public String _time;
    public String email;
    public String phone;
    public String user;
    public String password;

    public Employee(){

    }
    public Employee(String first, String last, String eNum, String pos, String mail, String phoneNum, String userName, String pass, String time){
        fName = first;
        lName = last;
        employeeNum = eNum;
        clinic = pos;
        _time= time;
        email = mail;
        phone = phoneNum;
        user = userName;
        password = pass;
    }
}
