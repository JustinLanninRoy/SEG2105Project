package com.example.walkinclinic;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import java.lang.String;

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
}
