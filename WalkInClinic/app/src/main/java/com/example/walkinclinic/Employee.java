package com.example.walkinclinic;

public class Employee {
    private String fName;
    private String lName;
    private int employeeNum;
    private String position;
    private String supervisor;
    private boolean fullTime;
    private String email;
    private String phone;
    private String user;
    private String password;

    public Employee(){

    }
    public Employee(String first, String last, int eNum, String pos, String superv, boolean time, String mail, String phoneNum, String userName, String pass){
        fName = first;
        lName = last;
        employeeNum = eNum;
        position = pos;
        supervisor = superv;
        fullTime= time;
        email = mail;
        phone = phoneNum;
        user = userName;
        password = pass;
    }
}
