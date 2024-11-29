package com.example.constants;

/**
 * Class that stores constant values.
 * It aims to avoid code duplications and to ease project maintenance
 */

public abstract  class BreweryConstants {

    //Base URI
    public static final String BASE_URI = "https://api.openbrewerydb.org/v1/breweries";
    //Endpoints
    public static final String SEARCH_ENDPOINT = "/search";
    public static final String AUTOCOMPLETE_ENDPOINT = "/autocomplete";

    //Query parameters
    public static final String QUERY_PARAMETER = "query";
    public static final String PER_PAGE_PARAMETER = "per_page";
}