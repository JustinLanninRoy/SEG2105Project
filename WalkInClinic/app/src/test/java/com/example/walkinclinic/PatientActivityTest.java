package com.example.walkinclinic;

import android.location.Address;
import android.location.Geocoder;

import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class PatientActivityTest {

    @Test
    public void generateServices() {
        PatientActivity patientActivity = new PatientActivity();
        ArrayList<String> allServices = new ArrayList<>();
        allServices.add("vaccination: nurse");
        allServices.add("xray: doctor");
        allServices.add("registration: staff");
        patientActivity.allServices = allServices;
        String[] expected = {"vaccination: nurse", "xray: doctor", "registration: staff"};
        String[] output = patientActivity.generateServices();
        assertArrayEquals(expected, output);
    }
}