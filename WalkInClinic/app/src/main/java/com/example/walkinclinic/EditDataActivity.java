package com.example.walkinclinic;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.content.Intent;
import android.widget.Toast;

public class EditDataActivity extends AppCompatActivity {

    private static final String TAG = "EditDataActivity";

    private Button delete;
    private TextView name;
    private TextView address;
    private TextView age;
    private TextView email;
    private TextView phone;
    private TextView username;
    private TextView password;

    DatabaseHelper databaseHelper;

    private String selectedName;
    private String selectedType;
    private int selectedID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_data);
        delete = findViewById(R.id.delete);
        name = findViewById(R.id.textView3);
        address = findViewById(R.id.textView4);
        age = findViewById(R.id.textView5);
        email = findViewById(R.id.textView6);
        phone = findViewById(R.id.textView7);
        username = findViewById(R.id.textView9);
        password = findViewById(R.id.textView8);

        databaseHelper = new DatabaseHelper(this);

        Intent received = getIntent();
        selectedID = received.getIntExtra("id", -1);
        selectedName = received.getStringExtra("name");
        selectedType = received.getStringExtra("type");
        String[] split = selectedName.split(", ");

        if (selectedType.equals("patient")){
            name.setText(split[0] + " " + split[1]);
            address.setText("Address: " + split[2]);
            age.setText("Age: " + split[3]);
            email.setText("Email: " + split[4]);
            phone.setText("Phone: " + split[5]);
            username.setText("Username: " + split[6]);
            password.setText("Password: " + split[7]);
        } else if (selectedType.equals("employee")){
            name.setText(split[0] + " " + split[1] + " (" + split[8] + ")");
            address.setText("Employee #: " + split[2]);
            age.setText("Clinic: " + split[3]);
            email.setText("Email: " + split[4]);
            phone.setText("Phone: " + split[5]);
            username.setText("Username: " + split[6]);
            password.setText("Password: " + split[7]);
        }

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedType.equals("patient")){
                    databaseHelper.deleteName(selectedID, selectedName);
                } else if (selectedType.equals("employee")){
                    databaseHelper.deleteNameA(selectedID, selectedName);
                }
                toastMessage("removed from database");
                Intent i = new Intent(EditDataActivity.this, ListActivity.class);
                i.putExtra("type", selectedType);
                startActivity(i);
            }
        });
    }

    private void toastMessage(String message){
        Toast.makeText(this, message,Toast.LENGTH_SHORT).show();
    }
}
