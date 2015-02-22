import Taxi_Packages.Driver_Visits;
import Taxi_Packages.Journey;
import Taxi_Packages.Places_Visited;
import Taxi_Packages.Taxis;
import Taxi_Packages.FileFormatException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.io.*;

import java.util.*;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Taxi_Service {

    // Load text files and generate instances of the clases to be built
    // Read Drivers file

    public static void main(String[] args) throws FileNotFoundException,IOException, FileFormatException, Exception {

        TreeMap Taxis_Map = new TreeMap();
        Places_Visited places =  new Places_Visited();
        Driver_Visits driver_visits = new Driver_Visits();
        ArrayList<Object> journey_list = new ArrayList<Object>();
        ArrayList<Object> driver_visits_list = new ArrayList<Object>();

        // CSV READER Setting
        String DELIMITER = ",";
        BufferedReader reader = new BufferedReader(new FileReader(System.getProperty("user.home").concat("\\Desktop\\Taxi-Service\\src\\drivers.csv")));
        String line = null;

        //CSV Loop
        int lineNum = 0;
        while ((line = reader.readLine()) != null) {
            lineNum++;
            //Try Extract information from our Drivers text file
            try {
                //Split the data on each "," how .csv's work
                String[] split = line.split(DELIMITER);
                //create a new Taxt instance
                Taxis taxi = new Taxis();
                // Set the data into the instance
                taxi.setDriver(split[0], split[1]);
                //Add the Instance to our Data Structure for looking up later on with Reg as the key.
                Taxis_Map.put(taxi.getRegistration(), taxi);
            }
            //Catch for Registration not being in the correct format (Our own exception)
            catch (FileFormatException ff){
              throw new FileFormatException("Registration did not match valid format on line Number: " + Integer.toString(lineNum));
            }
            //catch for no registration found
            catch(IndexOutOfBoundsException in){
                throw new FileFormatException("Registration is not set on line Number: " + Integer.toString(lineNum));
            }
            //catch for anything else
            catch(Exception ex){
                throw new Exception("Unknown Exception occurred on line Number: " + Integer.toString(lineNum));
            }
        }

        // new Reader for our next file
        reader = new BufferedReader(new FileReader(System.getProperty("user.home").concat("\\Desktop\\Taxi-Service\\src\\destinations_2014.csv")));
        // specify year for places_visited
        int year = 2014;
        // List of places_visited set as empty
        Set<String> places_visited = new HashSet<String>();

        String pattern = "^[A-z -]+$";
        // Create a Pattern object
        Pattern r = Pattern.compile(pattern);

        // While loop to loop over each line of places
        lineNum = 0;
        while ((line = reader.readLine()) != null) {
            lineNum++;
            //add the places to the list
            Matcher m = r.matcher(line);
            if (!m.find( )){throw new FileFormatException("Place did not match valid format line Number: " + Integer.toString(lineNum));}
            places_visited.add(line);
        }
        //once list is finished add the list with the year to our places_visited class
        places.set_places_visited(places_visited, year);

        // new Reader for our next file
        reader = new BufferedReader(new FileReader(System.getProperty("user.home").concat("\\Desktop\\Taxi-Service\\src\\places_and_distances.csv")));
        line = null;
        //CSV Loop
        HashMap<String, Double> distances = new HashMap<String, Double>();

        pattern = "^[A-z -]+$";
        // Create a Pattern object
        r = Pattern.compile(pattern);
        lineNum = 0;
        while ((line = reader.readLine()) != null) {
            String[] split = line.split(DELIMITER);

            Matcher m = r.matcher(split[0]);
            if (!m.find( )){throw new FileFormatException("Place did not match valid format line Number: " + Integer.toString(lineNum));}
            try{
                Double.parseDouble(split[1]);
            }
            //Catch for being unable to convert distance as a string to a double
            catch(NumberFormatException e) {
                e.printStackTrace();
                throw new FileFormatException("Distance did not match valid format line Number: " + Integer.toString(lineNum));
            }
            //catch for no registration found
            catch(IndexOutOfBoundsException in){
                throw new FileFormatException("Distance is not set on line Number: " + Integer.toString(lineNum));
            }
            distances.put(split[0], Double.parseDouble(split[1]));

        }


        //Create FileReader object which points to json file
        FileReader fileReader = new FileReader(System.getProperty("user.home").concat("\\Desktop\\Taxi-Service\\src\\destinations_2015.json"));
        //Create JSONParser to use on the input of file
        JSONParser parser = new org.json.simple.parser.JSONParser();

        // try for parsing the file in case the file is not the correct format.
        try {
            //do the parsing
            JSONObject jsonObject = (JSONObject) parser.parse(fileReader);
            //get the keys from the root of the json file.
            Set<String> json_keys = jsonObject.keySet();
            //transform Set of keys to an iterator to loop through them.
            Iterator<String> json_iter = json_keys.iterator();

            //Reset the places_visited Array
            places_visited = new HashSet<String>();

            //Loop over the Keys
            while (json_iter.hasNext())
            {
                //fetch the key from iterator
                String key = json_iter.next();
                //fetch the jsonObject using the key
                Object arr = jsonObject.get(key);

                // If the Key is not in the Taxi Map continue as no name can be put to this driver or it may be invalid.
                if (!Taxis_Map.containsKey(key))
                {
                    continue;
                }

                //Cast Array to the expected type
                ArrayList<ArrayList<String>> visited = (ArrayList<ArrayList<String>>) arr;

                //Create new ArrayList for Drivers visited
                ArrayList<String> driver_visited = new ArrayList<String>();


                //loop over the list of visited places
                for(int i= 0; i < visited.size(); i++)
                {
                    Journey journey = new Journey();
                    // TODO Validate the data
                    //get the first element(string) for each array(i).
                    String place = visited.get(i).get(0);
                   // System.out.println(place);
                    int no_pass = Integer.parseInt(visited.get(i).get(1));
                    //System.out.println(no_pass);
                    //add place to places_visited
                    places_visited.add(place);
                    //add place to driver_visited
                    driver_visited.add(place);

                    try {
                        journey.setJourney(place, distances.get(place), key, no_pass);

                        journey_list.add(journey);
                    }
                    catch (NullPointerException ex)
                    {
                        System.out.println(ex);
                    }
                }

                //set the map to the driver_visits class
                driver_visits.setPlaces_visited(key, driver_visited);

            }
            //set places visited for 2015
            places.set_places_visited(places_visited, 2015);
        }
        // catch for the possible parsing excpetion
        catch (ParseException ex) {
            //print the stacktrace for the exception
            ex.printStackTrace();
            throw new FileFormatException("did not match valid format");

        }
        // Generate the Report for Fares
        report_fares(journey_list);

        //Generate the Report of places drivers has visited
        driver_visited_report(driver_visits, Taxis_Map);

        int[] years = {2014, 2015};
        //Generate the Report for places visited in the array of years set above
        places_visited_report(places, years);


    }

    public static void  report_fares(ArrayList list_journeys) throws FileNotFoundException,UnsupportedEncodingException {
        // create a new list to be sorted later on
        List<Journey> ordered_list = new ArrayList<Journey>();
        // add all the journeys from our normal list to our one to be sorted
        for (int i = 0; i < list_journeys.size(); i++) {
            ordered_list.add((Journey) list_journeys.get(i));

        }
        // sort our new list using Javas built in Collections Module.
        Collections.sort(ordered_list);

        PrintWriter writer = new PrintWriter(System.getProperty("user.home").concat("\\Desktop\\Taxi-Service\\out\\journey_report.txt"), "UTF-8");
            //depending on sorting in ascending or descending do the following


        // print the header
        writer.println("CHARGES FOR THE TOP 5 JOURNEYS");
        // loop through the ordered list in reverse to get the top 5
        for (int i = ordered_list.size() - 1; i > (ordered_list.size() - 6); i--) {
            // retreive the journey from the ordered list
            ArrayList journey_vals = ordered_list.get(i).getJourney();
            // print the information in a nice format
            writer.printf("%s   %s    %.1f miles  %d people   Cost £%.2f\n",
                    (String) journey_vals.get(2),
                    (String) journey_vals.get(0),
                    (Double) journey_vals.get(1),
                    (Integer) journey_vals.get(3),
                    (Double) journey_vals.get(4));
        }
        // print new line for padding
        writer.println();



        // Same as above but not in reverse
        writer.println("CHARGES FOR THE CHEAPEST 5 JOURNEYS");

        for (int i = 0; i < 5; i++) {

            //System.out.println(ordered_list.get(i).getJourney());
            ArrayList journey_vals = ordered_list.get(i).getJourney();
            writer.printf("%s   %s    %.1f miles  %d people   Cost £%.2f\n",
                    (String) journey_vals.get(2),
                    (String) journey_vals.get(0),
                    (Double) journey_vals.get(1),
                    (Integer) journey_vals.get(3),
                    (Double) journey_vals.get(4));
        }
        writer.println();


        writer.close();

    }

    public static void driver_visited_report(Driver_Visits driver_visit, TreeMap Taxis_Map) throws FileNotFoundException, UnsupportedEncodingException {

        PrintWriter writer2 = new PrintWriter(System.getProperty("user.home").concat("\\Desktop\\Taxi-Service\\out\\driver_visited_report.txt"), "UTF-8");

        // Create Iterator of Registrations
        Iterator regs = Taxis_Map.keySet().iterator();
        // loop over iterator
        while(regs.hasNext()){
            //set reg to a string var
            String driver = regs.next().toString();
            //Fetch the Driver details using the Registration
            Taxis taxi = (Taxis) Taxis_Map.get(driver);
            //set the driver name to a string
            String driver_name = taxi.getDriver();
            //set the places visited to an ArrayList
            ArrayList<String> places = driver_visit.getPlaces_visited(driver);
            // print the drivers name
            writer2.printf("%s\n", driver_name);
            // Sort the Places Names
            Collections.sort(places);
            // print the places in the a for loop
            for (int i = 0; i < places.size(); i++) {
                writer2.printf("\t%s\n", places.get(i));
            }

        }
        writer2.close();


    }

    public static void places_visited_report(Places_Visited place, int[] years) throws FileNotFoundException, UnsupportedEncodingException {

        PrintWriter writer3 = new PrintWriter(System.getProperty("user.home").concat("\\Desktop\\Taxi-Service\\out\\places_visited_report.txt"), "UTF-8");

        // create a new List of Sets
        List<Set> list_places = new ArrayList<Set>();
        // add the sets from our places visited to this one.
        for(int i=0; i< years.length; i++) {
            // set the places in the new list
            Set places = place.get_places_visited(years[i]);
            list_places.add(i,places);
        }

        // Create new Sets for year 1 and 2 to compare
        Set<String> year1 = new HashSet<String>(list_places.get(0));
        Set<String> year2 = new HashSet<String>(list_places.get(1));
        // Create new Sets that will be used to find the difference between the years
        Set<String> year1_only = new HashSet<String>(year1);
        Set<String> year2_only = new HashSet<String>(year2);
        //remove all year 2 places from year 1
        year1_only.removeAll(year2);
        //remove all year 1 places from year 2
        year2_only.removeAll(year1);

        //Create new Set for Both Years
        Set<String> both_year = new HashSet<String>(year1);
        // Add year2 to the Year1
        both_year.addAll(year2);
        // remove the difference in year1 and year2
        both_year.removeAll(year1_only);
        both_year.removeAll(year2_only);

        //get sizez of year2 only
        int new_num = year2_only.size();
        //generarte iterator for year2
        Iterator year2_iter = year2_only.iterator();
        // print the Heafer
        writer3.printf("%d New Places Visited in %d\n", new_num, years[1]);
        // iterate and pritn the places
        while(year2_iter.hasNext()){
            writer3.printf("\t%s\n", year2_iter.next());
        }

        // Same As above should probably make this a method to stop code duplication
        new_num = year1_only.size();
        Iterator year1_iter = year1_only.iterator();
        writer3.printf("%d New Places Visited in %d\n", new_num, years[0]);

        while(year1_iter.hasNext()){
            writer3.printf("\t%s\n", year1_iter.next());
        }
        // Same as last comment
        new_num = both_year.size();
        Iterator both_year_iter = both_year.iterator();
        writer3.printf("%d Places Visited in BOTH  %d and %d\n", new_num, years[0], years[1]);

        while(both_year_iter.hasNext()){
            writer3.printf("\t%s\n", both_year_iter.next());
        }

        writer3.close();

    }
}
