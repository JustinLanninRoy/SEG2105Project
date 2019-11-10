package com.example.walkinclinic;

import android.widget.EditText;

import androidx.test.annotation.UiThreadTest;
import androidx.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


import static org.junit.Assert.*;

public class WalkInClinicEmployeeTesting {

    @Rule
    public ActivityTestRule<CreateEmployee> mActivityTestRule = new ActivityTestRule<CreateEmployee>(CreateEmployee.class);
    private CreateEmployee test = null;

    @Before
    public void setUp(){
        test = mActivityTestRule.getActivity();
    }

    @Test
    public void checkEmployeeFirstName(){
        assertNotNull(test.findViewById(R.id.FirstName));
        EditText testText = test.findViewById(R.id.FirstName);
        testText.setText("Steven");
        String nameGiven = testText.getText().toString();
        assertEquals("Steven", nameGiven);
    }

    @Test
    public void checkEmployeePhoneNumber(){
        assertNotNull(test.findViewById(R.id.phoneNumber));
        EditText testNum = test.findViewById(R.id.phoneNumber);
        testNum.setText("123-092-0987");
        String numGiven = testNum.getText().toString();
        assertNotEquals(null, numGiven);
    }




}
