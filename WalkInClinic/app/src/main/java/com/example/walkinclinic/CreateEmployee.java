package com.example.walkinclinic;

import android.os.Bundle;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class CreateEmployee extends CreatePerson {
    List<Employee> employees;
    DatabaseReference databaseEmployees;
    EditText employeeNum;
    EditText position;
    EditText supervisor;
    RadioGroup time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_employee);
        databaseEmployees = FirebaseDatabase.getInstance().getReference("patients");
        employees = new ArrayList<>();
        employeeNum = findViewById(R.id.employeeNumber);
        position = findViewById(R.id.position);
        supervisor = findViewById(R.id.supervisor);
        time = findViewById(R.id.workTime);
    }

    public void onCreatePerson(View view) {
        super.onCreatePerson(view);
        if (!checkAllFields()){
            addEmployee();
        }
    }

    @Override
    boolean checkAllFields(){
        if(checkEmpty(firstName)){
            return true;
        }
        else if(checkEmpty(lastName)){
            return true;
        }
        else if (checkEmpty(Email)){
            return true;
        }
        else if (checkEmpty(Phone)){
            return true;
        }
        else if (checkEmpty(userName)){
            return true;
        }
        else if (checkEmpty(userPassword)){
            return true;
        }
        else if (checkEmpty(employeeNum)) {
            return true;
        }
        else if (checkEmpty(position)){
            return true;
        }
        else if (checkEmpty(supervisor)){
            return true;
        }
        else{
            return false;
        }
    }

    @Override
    protected void onStart() {

        super.onStart();
        databaseEmployees.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()){
                    Employee employee = postSnapshot.getValue(Employee.class);
                    employees.add(employee);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    void openPostLoggin() {
        //creating the string
        String postLogginString = ("Welcome " + firstName.getText().toString() + "! You are logged-in as an Employee.");
        //opening the PostLoggin class and sending the message with it
        Intent i = new Intent(this, PostLoggin.class);
        i.putExtra("message", postLogginString);
        startActivity(i);
        Toast.makeText(getApplicationContext(),"Account Created",Toast.LENGTH_LONG).show();
    }

    private void addEmployee() {
        String fName = firstName.getText().toString().trim();
        String lName = lastName.getText().toString().trim();
        String superv = supervisor.getText().toString().trim();
        String pos = position.getText().toString().trim();
        String value = employeeNum.getText().toString().trim();
        int eNum = Integer.parseInt(value);
        String email = Email.getText().toString().trim();
        String phone = Phone.getText().toString().trim();
        String user = userName.getText().toString().trim();
        String password = userPassword.getText().toString().trim();
        String id = databaseEmployees.push().getKey();
        int selected = time.getCheckedRadioButtonId();
        RadioButton chosen = findViewById(selected);
        String work = chosen.getText().toString().trim();
        Employee employee = new Employee(id, fName, lName, eNum, pos, superv, email, phone, user, password, work);
        employees.add(employee);
        //databaseEmployees.child(id).setValue(employee);
    }
}
