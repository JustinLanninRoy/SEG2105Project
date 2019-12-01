package com.example.walkinclinic;

import org.junit.Test;

import static org.junit.Assert.*;

public class PostLoginTest {

    @Test
    public void getKey() {
        PostLogin postLogin = new PostLogin();
        String message = "Welcome Employee Bill! You are logged-in.";
        String expected = "Employee";
        String output = postLogin.getKey(message);
        assertEquals(expected, output);
    }
}