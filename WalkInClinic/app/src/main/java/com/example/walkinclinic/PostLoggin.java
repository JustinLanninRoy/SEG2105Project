package com.example.walkinclinic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import java.lang.String;
import android.view.View;
import android.widget.Toast;

public class PostLoggin extends AppCompatActivity {
    String message;
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_loggin);
        Intent received = getIntent();
        message = received.getStringExtra("message");
        TextView textview = findViewById(R.id.viewYouAreLoggedIn);
        textview.setText(message);

    }

    public void next(View view){
        TextView text = findViewById(R.id.viewYouAreLoggedIn);
        String message = text.getText().toString().trim();
        String[] split = message.split(" ");
        String key = split[1];
        if (key.equals("Admin!")){
            Intent i = new Intent(this, Admin.class);
            startActivity(i);
            Toast.makeText(getApplicationContext(),"Opening Admin Activity",Toast.LENGTH_LONG).show();
        }
        if (key.equals("Employee")){
            Intent received = getIntent();
            username = received.getStringExtra("username");
            Intent i = new Intent(this, Employee.class);
            i.putExtra("username", username);
            startActivity(i);
        }
        if (key.equals("Patient")){
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
        }
    }
}
