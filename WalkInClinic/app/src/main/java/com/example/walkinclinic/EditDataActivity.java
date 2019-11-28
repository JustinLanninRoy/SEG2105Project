package com.example.walkinclinic;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
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
        selectedType = received.getStringExtra("type");

        if (selectedType.equals("patient")){
            Cursor data = databaseHelper.getPatient(selectedID);
            while (data.moveToNext()){
                name.setText(data.getString(1) + " " + data.getString(2));
                address.setText("Address: " + data.getString(3));
                age.setText("Age: " + data.getString(4));
                email.setText("Email: " + data.getString(5));
                phone.setText("Phone: " + data.getString(6));
                username.setText("Username: " + data.getString(7));
                password.setText("");
            }
        } else if (selectedType.equals("employee")){
            Cursor data = databaseHelper.getEmployee(selectedID);
            while (data.moveToNext()){
                name.setText(data.getString(1) + " " + data.getString(2));
                address.setText("Employee #: " + data.getString(3));
                age.setText("Clinic: " + data.getString(4));
                email.setText("Email: " + data.getString(5));
                phone.setText("Phone: " + data.getString(6));
                username.setText("Username: " + data.getString(7));
                password.setText(data.getString(9));
            }
        }

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedType.equals("patient")){
                    databaseHelper.deleteName(selectedID);
                } else if (selectedType.equals("employee")){
                    databaseHelper.deleteNameA(selectedID);
                }
                toastMessage("removed from database");
                Intent i = new Intent(EditDataActivity.this, ListActivity.class);
                i.putExtra("type", selectedType);
                databaseHelper.close();
                startActivity(i);
            }
        });
    }

    private void toastMessage(String message){
        Toast.makeText(this, message,Toast.LENGTH_SHORT).show();
    }
}
