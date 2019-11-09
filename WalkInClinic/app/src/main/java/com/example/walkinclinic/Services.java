package com.example.walkinclinic;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.CheckBox;

public class Services extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services);
        //checkBox1.setChecked(checked);
    }

    @Override
    public void onBackPressed(){
        CheckBox check3 = (CheckBox) findViewById(R.id.checkBox3);
        boolean referral = check3.isChecked();
        CheckBox check2 = (CheckBox) findViewById(R.id.checkBox2);
        boolean prescription = check2.isChecked();
        CheckBox check5 = (CheckBox) findViewById(R.id.checkBox5);
        boolean immunization = check5.isChecked();
        CheckBox check4 = (CheckBox) findViewById(R.id.checkBox4);
        boolean physical = check4.isChecked();
        CheckBox check7 = (CheckBox) findViewById(R.id.checkBox7);
        boolean injury = check7.isChecked();
        super.onBackPressed();
    }
}
