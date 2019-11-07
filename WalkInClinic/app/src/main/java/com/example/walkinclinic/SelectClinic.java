package com.example.walkinclinic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;

public class SelectClinic extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_clinic);
    }

    public void onSelect(View view){
        Intent i = new Intent(this, ChooseClinic.class);
        startActivity(i);
    }

    public void onMoveOn(View view){

    }
}
