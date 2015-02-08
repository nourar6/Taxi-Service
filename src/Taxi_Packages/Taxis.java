package Taxi_Packages;

public class Taxis extends Driver_Name {

    Driver_Name driver;
    String registration;

    public void setDriver(String name, String reg) {
        driver = new Driver_Name();
        String[] names = name.split(" ");
        driver.setFirstName(names[0]);
        driver.setLastName(names[1]);
        registration = reg;
    }

    public String getRegistration() {
        return registration;
    }

    public String getDriver() {
        return driver.getFullName();
    }
}
