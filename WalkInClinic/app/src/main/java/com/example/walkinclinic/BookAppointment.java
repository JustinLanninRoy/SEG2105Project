package com.example.walkinclinic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.Toast;

import java.util.Date;
import java.util.Calendar;


public class BookAppointment extends AppCompatActivity {

    private CalendarView calendarView;
    int selectedMonth;
    int selectedYear;
    int selectedDate;
    int dayOfWeek;
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_appointment);
        calendarView = findViewById(R.id.calendarView);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DATE,Calendar.getInstance().getActualMinimum(Calendar.DATE));
        long date = calendar.getTime().getTime();
        calendarView.setMinDate(date);

        Intent recieved = getIntent();
        username = recieved.getStringExtra("username");

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
            Intent i = new Intent(BookAppointment.this, PatientActivity.class);
            toastMessage("Appointment request sent. The clinic will contact you with possible appointment times.");
            i.putExtra("username", username);
            startActivity(i);
        }
    }

    public boolean dateValid(){
        Calendar c = Calendar.getInstance();
        Date today = c.getTime();
        c.set(Calendar.YEAR, selectedYear);
        c.set(Calendar.MONTH, selectedMonth);
        c.set(Calendar.DAY_OF_MONTH, selectedDate);
        Date dateSpecified = c.getTime();
        if (dateSpecified.before(today)) {
            toastMessage("Date specified is before today. Please select a different date.");
            return false;
        }
        return true;
    }

    private void toastMessage(String message){
        Toast.makeText(this, message,Toast.LENGTH_SHORT).show();
    }
}
