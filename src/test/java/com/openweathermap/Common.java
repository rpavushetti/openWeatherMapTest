package com.openweathermap;

import static com.jayway.restassured.http.ContentType.HTML;
import static com.jayway.restassured.http.ContentType.JSON;
import static com.jayway.restassured.http.ContentType.XML;

import com.jayway.restassured.http.ContentType;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.HashMap;

public class Common {

    public static final String BASE_API_URL = "http://api.openweathermap.org/data/2.5";
    public static final Integer DEFAULT_PORT = 80;
    public static final String API_KEY = "33a45eef7be61f47637c51c4a20fd640";

 

    public static String randomString()
    {
        return RandomStringUtils.randomAlphanumeric(20);
    }
    
    public static String randomNumber()
    {
        return RandomStringUtils.randomNumeric(8);
    }
    
  
}
