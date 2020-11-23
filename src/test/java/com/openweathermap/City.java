package com.openweathermap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

import static org.codehaus.groovy.runtime.DefaultGroovyMethods.round;

public class City {

    private HashMap<String, Object> city;

    /*List of city ID city.list.json.gz can be downloaded here http://bulk.openweathermap.org/sample/
    The service isn't provide the list of postal codes so i get it from http://www.geopostcodes.com/Ukraine*/
    public static final ArrayList<HashMap> CITIES = new ArrayList<HashMap>(Arrays.asList(
            new HashMap() {{
                put("name", "New York");
                put("id", 5128581);
                put("country", "US");
                put("lon", -74.006);
                put("lat", 40.7143);
               
            }},
            new HashMap() {{
                put("name", "California");
                put("id", 4350049);
                put("country", "US");
                put("lon", -76.5074);
                put("lat", 38.3004);
                
            }}
            
    ));

    public City(){
        city = CITIES.get(new Random().nextInt(CITIES.size()));
    }

    public String getCityName(){
        return city.get("name").toString();
    }

      
    public Double getRawLat(){
        return (Double) city.get("lat");
    }

    public Double getRawLon(){
        return (Double) city.get("lon");
    }

    public Integer getCityId(){
        return (Integer) city.get("id");
    }

    public String getCountryCode(){
        return city.get("country").toString();
    }

  
}
