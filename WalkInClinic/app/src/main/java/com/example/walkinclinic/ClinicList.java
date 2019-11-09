package com.example.walkinclinic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.view.View;

public class ClinicList extends AppCompatActivity {

    TextView appletree;
    TextView rideau;
    TextView bank;
    TextView uOttawa;
    TextView southbank;
    Services a;
    Services r;
    Services b;
    Services u;
    Services s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clinic_list);
        appletree = findViewById(R.id.appletree);
        rideau = findViewById(R.id.rideau);
        bank = findViewById(R.id.bank);
        uOttawa = findViewById(R.id.uOttawa);
        southbank = findViewById(R.id.southbank);
        a = new Services();
        r = new Services();
        b = new Services();
        u = new Services();
        s = new Services();
    }

    public void selectAppletree(View view){
        Intent i = new Intent(this, Services.class);
        startActivity(i);
    }

    public void selectRideau(View view){
        Intent i = new Intent(this, Services.class);
        startActivity(i);
    }

    public void selectBank(View view){
        Intent i = new Intent(this, Services.class);
        startActivity(i);
    }

    public void selectOttawa(View view){
        Intent i = new Intent(this, Services.class);
        startActivity(i);
    }

    public void selectSouth(View view){
        Intent i = new Intent(this, Services.class);
        startActivity(i);
    }
}
