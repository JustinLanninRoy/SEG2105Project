package com.example.walkinclinic;

import android.widget.EditText;

import androidx.test.annotation.UiThreadTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;


import static org.junit.Assert.*;

public class WalkInClinicTesting {

    @Rule
    public ActivityTestRule<CreateEmployee> mActivityTestRule = new ActivityTestRule<CreateEmployee>(CreateEmployee.class);
    private CreateEmployee test = null;

    @Before
    public void setUp(){
        test = mActivityTestRule.getActivity();
    }

    @Test
    @UiThreadTest
    public void checkEmployeeFirstName(){
        assertNotNull(test.findViewById(R.id.FirstName));
        EditText testText = test.findViewById(R.id.FirstName);
        testText.setText("Steven");
        String nameGiven = testText.getText().toString();
        assertEquals("Steven", nameGiven);
    }

    

}
