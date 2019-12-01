package com.example.walkinclinic;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class ClinicProfileTest {

    @Test
    public void updateBools() {
        String[] bools = {"false", "true", "false", "false", "true"};
        boolean[] checked = {false, false, false, false, false};
        int x = 5;
        boolean[] expected = {false, true, false, false, true};
        ClinicProfile clinicProfile = new ClinicProfile();
        boolean[] output = clinicProfile.updateBools(bools, checked, x);
        assertArrayEquals(expected, output);
    }

    @Test
    public void updateInts() {
        String[] ints = {"5", "73", "31"};
        ArrayList<Integer> selected = new ArrayList<>();
        ArrayList<Integer> expected = new ArrayList<>();
        expected.add(5);
        expected.add(73);
        expected.add(31);
        ClinicProfile clinicProfile = new ClinicProfile();
        ArrayList<Integer> output = clinicProfile.updateInts(ints, selected);
        assertEquals(expected, output);
    }

    @Test
    public void hoursValid() {
        ClinicProfile clinicProfile = new ClinicProfile();
        String[] times = {"1","0","1","1"};
        int expected = 1;
        int output = clinicProfile.hoursValid(times);
        assertEquals(expected, output);
    }

    @Test
    public void createBoolStrings() {
        boolean[] checked = {false, true, false, true, true, false};
        String expected = "false, true, false, true, true, false, ";
        ClinicProfile clinicProfile = new ClinicProfile();
        String output = clinicProfile.createBoolStrings(checked);
        assertEquals(expected, output);
    }

    @Test
    public void createIntStrings() {
        ArrayList<Integer> checked = new ArrayList<>();
        checked.add(18);
        checked.add(73);
        checked.add(41);
        String expected = "18, 73, 41, ";
        ClinicProfile clinicProfile = new ClinicProfile();
        String output = clinicProfile.createIntStrings(checked);
        assertEquals(expected, output);
    }

    @Test
    public void valid() {
        ClinicProfile clinicProfile = new ClinicProfile();
        clinicProfile.flag = true;
        String phone = "000-000-0000";
        int itemSize = 5;
        int paymentSize = 2;
        int serviceSize1 = 5;
        int serviceSize2 = 0;
        int expected = 4;
        int output = clinicProfile.valid(phone, itemSize, paymentSize, serviceSize1, serviceSize2);
        assertEquals(expected, output);
    }
}