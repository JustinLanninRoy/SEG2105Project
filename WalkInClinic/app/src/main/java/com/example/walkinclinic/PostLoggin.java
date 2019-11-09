package com.example.walkinclinic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import java.lang.String;
import android.view.View;
import android.widget.Toast;

public class PostLoggin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_loggin);

        //finding the message from my last intent put extra
        String newMessage;
        if (savedInstanceState == null) {
            //opening the message
            Bundle extras = getIntent().getExtras();
            //checking if there is a message
            if(extras == null) {
                //copying null state
                newMessage= null;
            //copping the message
            } else {
                newMessage= extras.getString("message");
            }
        //saving the message
        } else {
            newMessage= (String) savedInstanceState.getSerializable("message");
        }
        //sending the message to the TextView
        TextView textview = findViewById(R.id.viewYouAreLoggedIn);
        textview.setText(newMessage);

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
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
            //Intent i = new Intent(this, SelectClinic.class);
            //startActivity(i);
            //Toast.makeText(getApplicationContext(),"Opening Employee Activity",Toast.LENGTH_LONG).show();
        }
        if (key.equals("Patient")){
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
        }
    }
}
