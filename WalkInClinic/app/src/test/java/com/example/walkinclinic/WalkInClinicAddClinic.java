package com.example.walkinclinic;

import android.widget.EditText;

import androidx.test.annotation.UiThreadTest;
import androidx.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.*;


public class WalkInClinicAddClinic {

    @Rule
    public ActivityTestRule<AddClinic> mActivityTestRule = new ActivityTestRule<AddClinic>(AddClinic.class);
    private AddClinic addClinicTest = null;

    @Before
    public void setUp(){
        addClinicTest = mActivityTestRule.getActivity();
    }

    @Test
    public void checkEnteredClinicName(){
        assertNotNull(addClinicTest.findViewById(R.id.clinicToAdd));
        EditText clinicAdded = addClinicTest.findViewById(R.id.clinicToAdd);
        clinicAdded.setText("Rideau Clinic");
        String clinicGiven = clinicAdded.getText().toString();
        assertEquals("Rideau Clinic", clinicGiven);
    }
}
