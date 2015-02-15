package Taxi_Packages;

public class Taxis extends Driver_Name {
    // IV
    Driver_Name driver;
    String registration;

    // Constructor to set the info to the class object.
    public void setDriver(String name, String reg) {
        // TODO Validate the reg if false throw class exception
        driver = new Driver_Name();
        String[] names = name.split(" ");
        driver.setFirstName(names[0]);
        driver.setLastName(names[1]);
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
