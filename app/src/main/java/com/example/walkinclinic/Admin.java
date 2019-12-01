package com.example.walkinclinic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class Admin extends AppCompatActivity {
    ImageView logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        logout = findViewById(R.id.imageView3);
    }

    public void viewClinics(View view){
        Intent i = new Intent(this, ClinicList.class);
        startActivity(i);
    }

    public void viewEmployees(View view){
        Intent i = new Intent(this, ListActivity.class);
        i.putExtra("type", "employee");
        startActivity(i);
    }

    public void viewPatients(View view){
        Intent i = new Intent(this, ListActivity.class);
        i.putExtra("type", "patient");
        startActivity(i);
    }

    public void viewServices(View view){
        Intent i = new Intent(this, Services.class);
        startActivity(i);
    }

    public void logOut(View view){
        logout.setBackgroundResource(R.drawable.common_google_signin_btn_icon_dark_normal_background);
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        Admin.this.finish();
    }
}
