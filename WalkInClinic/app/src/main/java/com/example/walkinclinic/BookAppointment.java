package com.example.walkinclinic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;
import java.util.Calendar;


public class BookAppointment extends AppCompatActivity {

    private CalendarView calendarView;
    TextView title;
    int selectedMonth;
    int selectedYear;
    int selectedDate;
    int dayOfWeek;
    String username;
    String clinic;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_appointment);
        calendarView = findViewById(R.id.calendarView);
        title = findViewById(R.id.textView41);
        db = new DatabaseHelper(this);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DATE,Calendar.getInstance().getActualMinimum(Calendar.DATE));
        long date = calendar.getTime().getTime();
        calendarView.setMinDate(date);

        Intent recieved = getIntent();
        username = recieved.getStringExtra("username");
        clinic = recieved.getStringExtra("clinic");
        title.setText(clinic);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                month = month + 1;
                selectedDate = dayOfMonth;
                selectedMonth = month;
                selectedYear = year;
                Calendar calendar = Calendar.getInstance();
                calendar.set(year, month, dayOfMonth);
                dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
            }
        });
    }

    public void pickTime(View view){
        if (dateValid()){
            db.addAppointment(clinic, username, selectedDate + "/" + selectedMonth + "/" + selectedYear);
            Intent i = new Intent(BookAppointment.this, PatientActivity.class);
            toastMessage("Appointment request sent. The clinic will contact you with possible appointment times.");
            i.putExtra("username", username);
            startActivity(i);
            BookAppointment.this.finish();
        } else {
            toastMessage("The requested date must be after the current date.");
        }
    }

    public boolean dateValid(){
        Calendar currentDate = Calendar.getInstance();
        Calendar chosenDate = Calendar.getInstance();
        chosenDate.set(selectedYear, selectedMonth - 1, selectedDate);
        return chosenDate.after(currentDate);
    }

    private void toastMessage(String message){
        Toast.makeText(this, message,Toast.LENGTH_SHORT).show();
    }
}
