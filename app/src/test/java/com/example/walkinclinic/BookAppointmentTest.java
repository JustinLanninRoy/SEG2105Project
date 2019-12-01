package com.example.walkinclinic;

import org.junit.Test;

import java.util.Calendar;

import static org.junit.Assert.*;

public class BookAppointmentTest {

    @Test
    public void dateValid() {
        BookAppointment bookAppointment = new BookAppointment();
        bookAppointment.selectedMonth = 9;
        bookAppointment.selectedYear = 2019;
        bookAppointment.selectedDate = 18;
        boolean output = bookAppointment.dateValid();
        assertFalse(output);
    }

    @Test
    public void dateValid2() {
        BookAppointment bookAppointment = new BookAppointment();
        bookAppointment.selectedMonth = 9;
        bookAppointment.selectedYear = 2020;
        bookAppointment.selectedDate = 18;
        boolean output = bookAppointment.dateValid();
        assertTrue(output);
    }
}