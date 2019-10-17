package com.example.walkinclinic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import java.lang.String;


public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onCreateAcccount(View view){

        //getting the username input
        TextView username = (TextView) findViewById(R.id.txtEditUsername);
        String firstName = username.getText().toString().trim();

        //getting the value of the radio button selected
        RadioGroup radioAccountTypeGroup = (RadioGroup) findViewById(R.id.AccountType);

        // get selected radio button from radioGroup
        int selectedId = radioAccountTypeGroup.getCheckedRadioButtonId();

        // find the radiobutton by returned id
        RadioButton selectedRadioButtonAccount = (RadioButton) findViewById(selectedId);
        String type = selectedRadioButtonAccount.getText().toString().trim();

        //creating the string
        String postLogginString = ("Welcome " + firstName + "! You are logged-in as a " + type);

        //opening the PostLoggin class and sending the message with it
        Intent i = new Intent(this, PostLoggin.class);
        i.putExtra("message", postLogginString);
        startActivity(i);

    }
}
