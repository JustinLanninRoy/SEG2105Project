package com.example.walkinclinic;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Employee extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    TextView name;
    TextView email;
    TextView phone;
    TextView user;
    TextView clinic;
    Button edit;
    String username;
    DatabaseHelper db;
    String clinicName;
    String hours;
    String clinicHours;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee);

        name = findViewById(R.id.employeeName);
        email = findViewById(R.id.email2);
        phone = findViewById(R.id.phone2);
        user = findViewById(R.id.username2);
        clinic = findViewById(R.id.clinicName);
        edit = findViewById(R.id.clinicProfile);
        db = new DatabaseHelper(this);

        Intent received = getIntent();
        username = received.getStringExtra("username");

        Cursor data = db.getEmployee(username);
        while (data.moveToNext()){
            name.setText(data.getString(1) + " " + data.getString(2));
            email.setText("Email: " + data.getString(4));
            phone.setText("Phone: " + data.getString(5));
            user.setText("Username: " + data.getString(6));
            clinic.setText("Clinic: " + data.getString(3));
            hours = data.getString(8);
            clinicName = data.getString(3);
        }


        Cursor clinicData = db.getClinicData();
        while (clinicData.moveToNext()){
            if (clinicData.getString(1).equalsIgnoreCase(clinicName)){
                clinicHours = clinicData.getString(8);
            }
        }

        if (hours.equals("")){
            hours = "0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0";
        }
    }

    public void clinicProfile(View view){
        Intent i = new Intent(this, ClinicProfile.class);
        i.putExtra("clinicName", clinicName);
        i.putExtra("username", username);
        db.close();
        startActivity(i);
        Employee.this.finish();
    }

    public void myHours(View view){
        final AlertDialog.Builder hBuilder = new AlertDialog.Builder(Employee.this);
        View mView = getLayoutInflater().inflate(R.layout.dialog_hours, null);
        final Spinner mAM = mView.findViewById(R.id.spinner2);
        final Spinner mPM = mView.findViewById(R.id.spinner3);
        final Spinner tuAM = mView.findViewById(R.id.spinner4);
        final Spinner tuPM = mView.findViewById(R.id.spinner5);
        final Spinner wAM = mView.findViewById(R.id.spinner6);
        final Spinner wPM = mView.findViewById(R.id.spinner7);
        final Spinner thAM = mView.findViewById(R.id.spinner8);
        final Spinner thPM = mView.findViewById(R.id.spinner9);
        final Spinner fAM = mView.findViewById(R.id.spinner10);
        final Spinner fPM = mView.findViewById(R.id.spinner11);
        final Spinner saAM = mView.findViewById(R.id.spinner12);
        final Spinner saPM = mView.findViewById(R.id.spinner13);
        final Spinner suAM = mView.findViewById(R.id.spinner14);
        final Spinner suPM = mView.findViewById(R.id.spinner15);

        Spinner[] spinners = new Spinner[]{mAM, mPM, tuAM, tuPM, wAM, wPM, thAM, thPM, fAM, fPM, saAM, saPM, suAM, suPM};
        final String[] hoursList = clinicHours.split(", ");

        for (int i = 0; i < 14; i++){
            ArrayList<String> swag = new ArrayList<>();
            if (i%2 == 0){
                for (int x = Integer.parseInt(hoursList[i]); x < 13; x++){
                    swag.add(getResources().getStringArray(R.array.times)[x]);
                }
            } else {
                for (int x = 1; x <= Integer.parseInt(hoursList[i]); x++){
                    swag.add(getResources().getStringArray(R.array.times)[x]);
                }
            }
            if (Integer.parseInt(hoursList[i]) == 0){
                swag.clear();
                swag.add("Closed");
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, swag);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinners[i].setAdapter(adapter);
        }

        mAM.setOnItemSelectedListener(Employee.this);
        mPM.setOnItemSelectedListener(Employee.this);
        tuAM.setOnItemSelectedListener(Employee.this);
        tuPM.setOnItemSelectedListener(Employee.this);
        wAM.setOnItemSelectedListener(Employee.this);
        wPM.setOnItemSelectedListener(Employee.this);
        thAM.setOnItemSelectedListener(Employee.this);
        thPM.setOnItemSelectedListener(Employee.this);
        fAM.setOnItemSelectedListener(Employee.this);
        fPM.setOnItemSelectedListener(Employee.this);
        saAM.setOnItemSelectedListener(Employee.this);
        saPM.setOnItemSelectedListener(Employee.this);
        suAM.setOnItemSelectedListener(Employee.this);
        suPM.setOnItemSelectedListener(Employee.this);

        String[] specificHours = hours.split(", ");
        for (int i = 0; i < 14; i++){
            spinners[i].setSelection(Integer.parseInt(specificHours[i]));
        }

        hBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //do nothing, override later
            }
        });
        hBuilder.setView(mView);
        final AlertDialog dialog = hBuilder.create();
        dialog.show();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String monA = String.valueOf(mAM.getSelectedItemPosition());
                String monP = String.valueOf(mPM.getSelectedItemPosition());
                String tueA = String.valueOf(tuAM.getSelectedItemPosition());
                String tueP = String.valueOf(tuPM.getSelectedItemPosition());
                String wedA = String.valueOf(wAM.getSelectedItemPosition());
                String wedP = String.valueOf(wPM.getSelectedItemPosition());
                String thuA = String.valueOf(thAM.getSelectedItemPosition());
                String thuP = String.valueOf(thPM.getSelectedItemPosition());
                String friA = String.valueOf(fAM.getSelectedItemPosition());
                String friP = String.valueOf(fPM.getSelectedItemPosition());
                String satA = String.valueOf(saAM.getSelectedItemPosition());
                String satP = String.valueOf(saPM.getSelectedItemPosition());
                String sunA = String.valueOf(suAM.getSelectedItemPosition());
                String sunP = String.valueOf(suPM.getSelectedItemPosition());
                String newHours = monA + ", " + monP + ", " + tueA + ", " + tueP + ", " + wedA + ", " + wedP + ", " + thuA + ", " + thuP + ", " + friA + ", " + friP + ", " + satA + ", " + satP + ", " + sunA + ", " + sunP;
                hours = newHours;
                db.updateEmployeeHours(username, hours);
                dialog.dismiss();
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void toastMessage(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
