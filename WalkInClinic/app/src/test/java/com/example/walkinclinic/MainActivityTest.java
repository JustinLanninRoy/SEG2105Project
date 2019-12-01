package com.example.walkinclinic;

import org.junit.Test;

import static org.junit.Assert.*;

public class MainActivityTest {

    //do tests for several cases
    @Test
    public void valid() {
        String exists = "john123";
        String username = "john123";
        String password = "bjdkfuwi";
        String epassword = null;
        String ppassword = "bjdkfuwi";
        int expected = 2;
        MainActivity mainActivity = new MainActivity();
        int output = mainActivity.valid(exists, username, password, epassword, ppassword);
        assertEquals(expected, output);
    }
}