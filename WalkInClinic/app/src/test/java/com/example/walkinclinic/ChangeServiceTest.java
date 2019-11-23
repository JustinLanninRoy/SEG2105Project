package com.example.walkinclinic;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class ChangeServiceTest {

    @Test
    public void validateServiceUpdate() {
        String service = "vaccination";
        String role = "jeff";
        ArrayList<String> services = new ArrayList<String>();
        services.add("vaccination: nurse");
        services.add("xray: doctor");
        int expected = 3;
        ChangeService changeService = new ChangeService();
        int output = changeService.validateServiceUpdate(service, role, services);
        assertEquals(expected, output);
    }

    @Test
    public void validateServiceDelete() {
        String remove = "xray: Doctor";
        ArrayList<String> services = new ArrayList<String>();
        services.add("vaccination: nurse");
        services.add("xray: doctor");
        services.add("registration: staff");
        int expected = 1;
        ChangeService changeService = new ChangeService();
        int output = changeService.validateServiceDelete(remove, services);
        assertEquals(expected, output);
    }
}