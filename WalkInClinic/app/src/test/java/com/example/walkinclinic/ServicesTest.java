package com.example.walkinclinic;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class ServicesTest {

    @Test
    public void serviceExists() throws Exception{
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

    @Test
    public void wrongRole() throws Exception{
        String inputService = "Vaccination";
        String inputRole = "Jeff";
        ArrayList<String> inputList = new ArrayList<>();
        inputList.add("Prescription: Doctor");
        inputList.add("Vaccination: Nurse");
        inputList.add("Registration: Staff");
        int expected = 2;
        Services services = new Services();
        int output = services.invalidService(inputService, inputRole, inputList);
        assertEquals(expected, output);
    }

    @Test
    public void missingInput() throws Exception{
        String inputService = "";
        String inputRole = "Nurse";
        ArrayList<String> inputList = new ArrayList<>();
        inputList.add("Prescription: Doctor");
        inputList.add("Vaccination: Nurse");
        inputList.add("Registration: Staff");
        int expected = 1;
        Services services = new Services();
        int output = services.invalidService(inputService, inputRole, inputList);
        assertEquals(expected, output);
    }
}