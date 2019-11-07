package com.example.walkinclinic;

public class Employee {
    public String fName;
    public String lName;
    public int employeeNum;
    public String position;
    public String supervisor;
    public String _time;
    public String email;
    public String phone;
    public String user;
    public String password;
    public String _iD;

    public Employee(){

    }
    public Employee(String id, String first, String last, int eNum, String pos, String superv, String mail, String phoneNum, String userName, String pass, String time){
        _iD = id;
        fName = first;
        lName = last;
        employeeNum = eNum;
        position = pos;
        supervisor = superv;
        _time= time;
        email = mail;
        phone = phoneNum;
        user = userName;
        password = pass;
    }
}
