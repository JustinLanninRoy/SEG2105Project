package com.example.walkinclinic;

import org.junit.Test;

import static org.junit.Assert.*;

public class ClinicSearchResultsTest {

    @Test
    public void noClinicsStored() {
        ClinicSearchResults clinicSearchResults = new ClinicSearchResults();
        assertFalse(clinicSearchResults.noClinicsStored(3));
    }
}