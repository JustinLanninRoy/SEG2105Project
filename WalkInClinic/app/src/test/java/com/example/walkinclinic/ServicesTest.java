package com.example.walkinclinic;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class ServicesTest {

    @Test
    public void invalidService() throws Exception{
        String inputService = "Vaccination";
        String inputRole = "Nurse";
        ArrayList<String> inputList = new ArrayList<>();
        inputList.add("Prescription: Doctor");
        inputList.add("Vaccination: Nurse");
        inputList.add("Registration: Staff");
        int expected = 3;
        Services services = new Services();
        int output = services.invalidService(inputService, inputRole, inputList);
        assertEquals(expected, output);
    }
}