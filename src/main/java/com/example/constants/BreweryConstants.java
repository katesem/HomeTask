package com.example.constants;

/**
 * Class that stores constant values.
 * It aims to avoid code duplications and to ease project maintenance
 */

public abstract  class BreweryConstants {
    //Endpoints
    public static final String SEARCH_ENDPOINT = "/search";
    public static final String AUTOCOMPLETE_ENDPOINT = "/autocomplete";

    //Query parameters
    public static final String QUERY_PARAMETER = "query";
    public static final String PER_PAGE_PARAMETER = "per_page";
}