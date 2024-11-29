package com.example.apiRequests.search;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

import static com.example.constants.BreweryConstants.BASE_URI;

/**
 * Class for performing requests to Search Brewery API
 */
public class SearchBreweryAPI {

    // Global Configuration for RestAssured Base URI
    static {
        RestAssured.baseURI = BASE_URI;
    }

    // Logger instance for logging messages
    private static final Logger logger = LogManager.getLogger(SearchBreweryAPI.class);

    /**
     * Sends a GET request to the specified endpoint with any number of query parameters from Map.
     *
     * @param queryParams A map containing the query parameters and their values.
     * @param endpoint    The API endpoint to send the request to.
     * @return Response   The response received from the GET request.
     */
    public static Response sendGetRequestWithQueryParameters(Map<String, String> queryParams, String endpoint) {
        RequestSpecification requestSpec = RestAssured.given();
        queryParams.forEach(requestSpec::queryParam);

        // Send the GET request
        Response response = requestSpec.get(endpoint);

        // Log info that request was sent
        logger.info(String.format("Sent GET request to %s with query parameters: %s", endpoint, queryParams));
        return response;
    }
}

