package Taxi_Packages;
import java.util.*;

public class Journey implements Comparable<Journey> {
    // IV
    String dest_place;
    Double distance;
    String taxi_reg;
    Integer no_of_passengers;
    Double cost;

    // internal method to calculate the cost accessible only within this class
    private void calculateCost(Integer no_of_passengers, Double distance) {
        cost = distance * 1 + (0.1 * no_of_passengers);
    }

    // Contstructor to set the information required and calculate the cost.
    public void setJourney(String dest_place, Double distance, String taxi_reg, Integer no_of_passengers) {

        this.dest_place = dest_place;
        this.distance = distance;
        this.taxi_reg = taxi_reg;
        this.no_of_passengers = no_of_passengers;
        calculateCost(this.no_of_passengers, this.distance);

    }

    // Method to return the Journey information as an ArrayList
    public ArrayList getJourney() {
        ArrayList<Object> arr = new ArrayList<Object>();
        arr.add(this.dest_place);
        arr.add(this.distance);
        arr.add(this.taxi_reg);
        arr.add(this.no_of_passengers);
        arr.add(this.cost);
        return arr;
    }

    // Override of compareTo from the Conparable Class this Compares the Costs of the class to sort them in ascending order.
    @Override
    public int compareTo(Journey o) {
        return cost < o.cost ? -1 : cost > o.cost ? 1 : 0;
    }
}

