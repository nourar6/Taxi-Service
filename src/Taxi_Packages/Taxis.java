package Taxi_Packages;

import com.sun.xml.internal.txw2.output.IndentingXMLStreamWriter;

import java.util.InputMismatchException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Taxis extends Driver_Name {
    // IV
    Driver_Name driver;
    String registration;

    // Constructor to set the info to the class object.
    public void setDriver(String name, String reg) throws FileFormatException, IndexOutOfBoundsException {

        String pattern = "^[A-Z][0-9][A-Z] [0-9]{3}$";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(reg);

        if (!m.find( )){throw new FileFormatException();}

        driver = new Driver_Name();
        String[] names = name.split(" ");
        driver.setFirstName(names[0]);
        try {
            driver.setLastName(names[1]);
        }
        catch (IndexOutOfBoundsException e){
            throw new IndexOutOfBoundsException("Last name not Set");
        }
        registration = reg;
    }
    // method to return the registration
    public String getRegistration() {
        return registration;
    }
    // method to return the Drivers name
    public String getDriver() {
        return driver.getFullName();
    }
}
