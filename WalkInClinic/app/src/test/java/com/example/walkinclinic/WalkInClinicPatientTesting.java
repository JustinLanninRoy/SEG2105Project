package com.example.walkinclinic;

import android.widget.EditText;

import androidx.test.annotation.UiThreadTest;
import androidx.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.*;

public class WalkInClinicPatientTesting {

    @Rule
    public ActivityTestRule<CreatePatient> mActivityTestRule = new ActivityTestRule<CreatePatient>(CreatePatient.class);
    private CreatePatient patientTest = null;

    @Before
    public void setUp(){
        patientTest = mActivityTestRule.getActivity();
    }

    @Test
    public void checkPatientAge(){
        assertNotNull(patientTest.findViewById(R.id.Age));
        EditText patientAge = patientTest.findViewById(R.id.Age);
        patientAge.setText("18");
        String ageGiven = patientAge.getText().toString();
        assertEquals("18", ageGiven);
    }

    @Test
    public void checkPatientEmail(){
        assertNotNull(patientTest.findViewById(R.id.Email));
        EditText patientEmail = patientTest.findViewById(R.id.Email);
        patientEmail.setText("steve@gmail.com");
        String emailEntered = patientEmail.getText().toString();
        assertNotEquals(null, emailEntered);
    }
}
