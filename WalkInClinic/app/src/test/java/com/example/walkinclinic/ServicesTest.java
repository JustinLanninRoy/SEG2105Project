package com.example.walkinclinic;

import org.junit.Test;

import static org.junit.Assert.*;

public class ServicesTest {

    @Test
    public void invalidService() throws Exception{
        String inputService = "Vaccination";
        String inputRole = "Nurse";
        String[] inputList = {"Prescription: Doctor","Vaccination: Nurse","Registration: Staff"};
        int expected = 3;
        Services services = new Services();
        int output = services.invalidService(inputService, inputRole, inputList);
        assertEquals(expected, output);
    }
}