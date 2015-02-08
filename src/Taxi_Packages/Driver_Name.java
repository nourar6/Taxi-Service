package Taxi_Packages;

// Name class for our Taxi Drivers
public class Driver_Name {
    //Instance Variables
    String FirstName = "";
    String LastName = "";

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }
    public void setLastName(String lastName) {
        LastName = lastName;
    }

    //Method to retrieve FirstName Variable
    public String getFirstName() {
        return FirstName;
    }
    //Method to retrieve LastName Variable
    public String getLastName() {
        return LastName;
    }
    //Method to retrieve Full Name Variable
    public String getFullName() {
        return FirstName + " " + LastName;
    }
}