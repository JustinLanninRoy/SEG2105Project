package com.example.walkinclinic;

import org.junit.Test;

import static org.junit.Assert.*;

public class RateClinicTest {

    @Test
    public void checkComment() {
        RateClinic rateClinic = new RateClinic();
        String comment = "I hate this clinic and everyone in it";
        int expected = 0;
        rateClinic.checkComment(comment);
        int output = rateClinic.x;
        assertEquals(expected, output);
    }

    @Test
    public void checkComment2() {
        RateClinic rateClinic = new RateClinic();
        String comment = "    ";
        int expected = 1;
        rateClinic.checkComment(comment);
        int output = rateClinic.x;
        assertEquals(expected, output);
    }
}