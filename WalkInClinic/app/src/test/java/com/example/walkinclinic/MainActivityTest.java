package com.example.walkinclinic;

import org.junit.Test;

import static org.junit.Assert.*;

public class MainActivityTest {

    @Test
    public void validateLogin() {
        String username = "admin";
        String password = "5T5ptQ";
        int expected = 1;
        MainActivity mainActivity = new MainActivity();
        int output = mainActivity.validateLogin(username, password);
        assertEquals(expected, output);
    }
}