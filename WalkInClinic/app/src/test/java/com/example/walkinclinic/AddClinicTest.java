package com.example.walkinclinic;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class AddClinicTest {

    @Test
    public void invalidClinic() {
        String clinic = "Riverdale";
        ArrayList<String> list = new ArrayList<String>();
        list.add("Southside");
        list.add("Halifax");
        list.add("Riverdale");
        int expected = 2;
        AddClinic addClinic = new AddClinic();
        int output = addClinic.invalidClinic(clinic, list);
        assertEquals(expected, output);
    }
}