package com.example.walkinclinic;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import android.content.Intent;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import java.lang.String;

import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onLoggin(View view){

        //getting the username input
        TextView userName = (TextView) findViewById(R.id.txtEditUsername);
        String username = userName.getText().toString().trim();

        //getting the password input
        TextView passWord = (TextView) findViewById(R.id.txtEditPassword);
        String password = passWord.getText().toString().trim();

        if(username.equalsIgnoreCase("admin") && password.equalsIgnoreCase("5T5ptQ")){
            //getting the value of the radio button selected
            RadioGroup radioAccountTypeGroup = (RadioGroup) findViewById(R.id.AccountType);

            // get selected radio button from radioGroup
            int selectedId = radioAccountTypeGroup.getCheckedRadioButtonId();

            // find the radiobutton by returned id
            RadioButton selectedRadioButtonAccount = (RadioButton) findViewById(selectedId);
            String type = selectedRadioButtonAccount.getText().toString().trim();

            //creating the string
            String postLogginString = ("Welcome " + username + "! You are logged-in as a " + type);

            //opening the PostLoggin class and sending the message with it
            Intent i = new Intent(this, PostLoggin.class);
            i.putExtra("message", postLogginString);
            startActivity(i);
        } else {
            //give error warning to user
            Toast.makeText(getApplicationContext(),"Account login is currently under development. The only way to log in is by selecting admin, inputting username: admin, password: 5T5ptQ",Toast.LENGTH_LONG).show();

            //forcefully input loggin for now
            TextView setUsername = (TextView) findViewById(R.id.txtEditUsername);
            setUsername.setText("admin");
            TextView userPassword = (TextView) findViewById(R.id.txtEditPassword);
            userPassword.setText("5T5ptQ");

            //forcefully select admin for now
            RadioButton Admin = (RadioButton) findViewById(R.id.Admin);
            Admin.setChecked(true);
        }
    }

    public void onCreateAccount(View view){

        //getting the value of the radio button selected
        RadioGroup radioAccountTypeGroup = (RadioGroup) findViewById(R.id.AccountType);

        // get selected radio button from radioGroup
        int selectedId = radioAccountTypeGroup.getCheckedRadioButtonId();

        // find the radiobutton by returned id
        RadioButton selectedRadioButtonAccount = (RadioButton) findViewById(selectedId);

        String type = selectedRadioButtonAccount.getText().toString().trim();

        //selecting the correct window to open
        if(type.equalsIgnoreCase("Patient")){
            Intent i = new Intent(this, CreatePatient.class);
            startActivity(i);
            Toast.makeText(getApplicationContext(),"New Patient Account",Toast.LENGTH_LONG).show();
        }
        if(type.equalsIgnoreCase("Admin")){
            Toast.makeText(getApplicationContext(),"New Admin account cannot be made please select Patient or Employee before creating a new account",Toast.LENGTH_LONG).show();
        }
        if(type.equalsIgnoreCase( "Employee")){
            Intent i = new Intent(this, CreateEmployee.class);
            startActivity(i);
            Toast.makeText(getApplicationContext(),"New Employee Account",Toast.LENGTH_LONG).show();
        }
    }
}
