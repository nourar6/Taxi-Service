import Taxi_Packages.FileFormatException;
import Taxi_Packages.Journey;
import Taxi_Packages.Taxis;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class Taxi_ServiceTest {

    @Test
    public void test_costCalculation() {
        System.out.println("Testing that cost is calculated correctly");
        //create a new instance of journey
        Journey J = new Journey();
        //set the data
        J.setJourney("Edinburgh", 33.3, "E2Z 452", 3);
        //get the data
        ArrayList journey = J.getJourney();
        //check that the data is correct
        assertTrue((String) journey.get(0) == "Edinburgh");
        assertTrue((Double) journey.get(1) == 33.3);
        assertTrue((String) journey.get(2) == "E2Z 452");
        assertTrue((Integer) journey.get(3) == 3);
        //check the cost is calculated correctly
        assertTrue((Double) journey.get(4) == (33.3 * (1 + (0.1 * 3))));
        //check the cost is calculated incorrectly
        assertFalse((Double) journey.get(4) == (33 * (1 + (0.1 * 3))));
        System.out.println("Test Passed");
    }

    @Test
    public void test_DriverRegistration() throws FileFormatException, IndexOutOfBoundsException {
        System.out.println("Testing that the Registration and Name is in the correct format");
        //create a bew instance of Taxi
        Taxis T = new Taxis();
        //Set the Data
        T.setDriver("Bob Smith", "E2Z 452");
        //Check the data is what it was set as
        assertTrue("E2Z 452".equals(T.getRegistration()));
        assertTrue("Bob Smith".equals(T.getDriver()));
        assertFalse("E2E 452".equals(T.getRegistration()));
        assertFalse("BobSmith".equals(T.getDriver()));
        System.out.println("Test Passed");

    }

    @Test
    public void test_ExceptNameDriverRegistration() throws FileFormatException, IndexOutOfBoundsException {
        System.out.println("Testing that Last Name is missing");
        Taxis T = new Taxis();
        //test that the First or LastName is not Set
        try { T.setDriver("BobSmith", "E2E 452"); }
        //Catch the Expected Exception and Pass the Test
        catch( IndexOutOfBoundsException IO){System.out.println("Test Passed");}


    }

    @Test
    public void test_ExceptRegDriverRegistration() throws FileFormatException, IndexOutOfBoundsException {
        System.out.println("Testing that registration is invalid format");
        Taxis T = new Taxis();
        //Test that the Registration is not set in the correct format
        try { T.setDriver("Bob Smith", "EEE 452"); }
        //Catch the expected Exception and test is passed
        catch( FileFormatException ff){System.out.println("Test Passed");}

    }

}