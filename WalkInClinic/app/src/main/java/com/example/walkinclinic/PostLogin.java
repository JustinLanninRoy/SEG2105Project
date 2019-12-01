package com.example.walkinclinic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import java.lang.String;
import android.view.View;
import android.widget.Toast;

public class PostLogin extends AppCompatActivity {
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
        switch (getKey( message)){
            case "Admin!":
                Intent i = new Intent(this, Admin.class);
                startActivity(i);
                PostLogin.this.finish();
                break;
            case "Employee":
                Intent received = getIntent();
                username = received.getStringExtra("username");
                Intent x = new Intent(this, Employee.class);
                x.putExtra("username", username);
                startActivity(x);
                PostLogin.this.finish();
                break;
            case "Patient":
                Intent received2 = getIntent();
                username = received2.getStringExtra("username");
                Intent y = new Intent(this, PatientActivity.class);
                y.putExtra("username", username);
                startActivity(y);
                PostLogin.this.finish();
                break;
            default:
                toastMessage("Error");
        }
    }

    public String getKey(String message){
        String[] split = message.split(" ");
        String key = split[1];
        return key;
    }

    private void toastMessage(String message){
        Toast.makeText(this, message,Toast.LENGTH_SHORT).show();
    }
}
