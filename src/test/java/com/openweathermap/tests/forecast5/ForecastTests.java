package com.openweathermap.tests.forecast5;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.builder.RequestSpecBuilder;

import com.jayway.restassured.specification.RequestSpecification;
import com.openweathermap.City;


import java.util.ArrayList;


import org.testng.annotations.AfterTest;


import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static com.jayway.restassured.RestAssured.given;
import static com.openweathermap.Common.*;
import static org.hamcrest.CoreMatchers.equalTo;


//http://openweathermap.org/forecast5
public class ForecastTests {
    public final String endpointURL = BASE_API_URL+"/forecast?appid="+API_KEY+"&";

    private ArrayList<RequestSpecification> requestParams = new ArrayList<RequestSpecification>();

    private City city;
    private String cityName;
    private String countyCode;
    private Integer cityId;
    private Double lat;
    private Double lon;

   

    
    @BeforeTest
    public void setUp()
    {
    	
    	RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        city = new City();
        cityName = city.getCityName();
        countyCode = city.getCountryCode();
        cityId = city.getCityId();
        lat = city.getRawLat();
        lon = city.getRawLon();

        requestParams.add(new RequestSpecBuilder().addParam("id", cityId).setPort(DEFAULT_PORT).build());
        requestParams.add(new RequestSpecBuilder().addParam("q", cityName).setPort(DEFAULT_PORT).build());
        requestParams.add(new RequestSpecBuilder().addParam("lat", lat).setPort(DEFAULT_PORT).addParam("lon", lon).build());
    }

    @AfterTest
    public void tearDown() {
        cityName = null;
        countyCode = null;
        cityId = null;
        lat = null;
        lon = null;
        requestParams.clear();
    }

    /* _______________________________________________________________
                    Metadata tests
    __________________________________________________________________ */

    @Test(priority=0)
    public void status200WhenIdCorrectByCityId(){
        given().
                param("id", cityId).
        when().
                get(endpointURL).
        then().
                assertThat().statusCode(200);
    }

    @Test(priority=1)
    public void status200WhenNameCorrectByCityName(){
        given().
                param("q", cityName).
        when().
                get(endpointURL).
        then().
                assertThat().statusCode(200);
    }


   
    
    
    
    /* _______________________________________________________________
                        Error messages tests
    __________________________________________________________________ */

  
    @Test(priority=2)
    public void checkBodyMessageWhenUnauthorized(){
        for (RequestSpecification paramFromList: requestParams) {
            given().
                    spec(paramFromList).
            when().
                    get(BASE_API_URL+"/forecast").
            then().
                    assertThat().body("cod", equalTo(401)).and().
                    body("message", equalTo("Invalid API key. Please see http://openweathermap.org/faq#error401 for more info."));
        }
    }

    @Test(priority=3)
    public void checkBodyMessageWhenIdIncorrectByCityId(){
        given().
                param("id", randomNumber()).
                
        when().
                get(endpointURL).
        then().
                assertThat().body("cod", equalTo("404")).and().
                body("message", equalTo("city not found"));
    }

    

	@Test(priority=4)
    public void checkBodyMessageWhenNameIncorrectByCityName(){
        given().
                param("q", randomString()).
                param("mode", "json").
        when().
                get(endpointURL).
        then().
                assertThat().body("cod", equalTo("404")).and().
                body("message", equalTo("city not found"));
    }

   

    /* _______________________________________________________________
                        Payload tests/ Test whether list contains 24/3*5 = 40 elements
    __________________________________________________________________ */

    @Test(priority=5)
    public void checkCityParamsInResponseJSON(){
        //Verify that response identical by all possibles request params
        for (RequestSpecification paramFromList: requestParams) {
            given().
                    spec(paramFromList).
                    param("mode", "json").
            when().
                    get(endpointURL).
            then().
                    assertThat().body("city.id", equalTo(cityId)).and().
                    body("city.name", equalTo(cityName)).and().
                    body("city.country", equalTo(countyCode)).and().
                    body("list.size()", equalTo(40));         
                
        }
    }



   

   
}
