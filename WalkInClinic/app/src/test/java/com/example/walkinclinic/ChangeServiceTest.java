package com.example.walkinclinic;

import org.junit.Test;

import static org.junit.Assert.*;

public class ChangeServiceTest {

    @Test
    public void validateServiceUpdate() {
        String service = "vaccination";
        String role = "jeff";
        String[] services = {"vaccination: nurse", "xray: doctor"};
        int expected = 3;
        ChangeService changeService = new ChangeService();
        int output = changeService.validateServiceUpdate(service, role, services);
        assertEquals(expected, output);
    }

    @Test
    public void validateServiceDelete() {
        String remove = "xray: Doctor";
        String[] services = {"registration: staff", "vaccination: nurse", "xray: doctor"};
        int expected = 2;
        ChangeService changeService = new ChangeService();
        int output = changeService.validateServiceDelete(remove, services);
        assertEquals(expected, output);
    }
}