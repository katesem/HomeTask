package com.example.search;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import utils.BreweryDataProviders;

import java.util.List;
import java.util.Map;

import static com.example.apiRequests.search.SearchBreweryAPI.sendGetRequestWithQueryParameters;
import static com.example.apiRequests.search.SearchBreweryResponseValidator.*;
import static com.example.constants.BreweryConstants.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.lessThanOrEqualTo;

/**
 * Class for test methods that cover main features of Open Brewery API Search method
 */
public class SearchBreweryTest {

    /**
     * This test method does the following:
     * - sends GET request to /search endpoint with valid query parameter and value
     * - verifies that searched query appears in every object of the response Array
     * - validates JSON schema of response body with Jackson DTO
     * - validates response status code is 200 OK
     * - validates that response Array size is equals to per_page parameter value
     * It uses TestNG @DataProvider annotation to run test multiple times with different sets of data
     *
     * @param queryParams - Map that contains query param and it's value in format: {parameter_name: parameter_value, ...}
     */
    @Test(description = "Scenario: Search with valid query parameters for /search endpoint",
            dataProvider = "caseSensitivitySpacesAndSizeData", dataProviderClass = BreweryDataProviders.class)
    public void validateSearchResultsForDifferentQueryValues(Map<String, String> queryParams) throws Exception {

        Response response = sendGetRequestWithQueryParameters(queryParams, SEARCH_ENDPOINT);
        response.then().assertThat().statusCode(200); //verify that status code is 200 OK

        validateResponseStructureAndQueryMatch(response, queryParams.get(QUERY_PARAMETER));

        //validate that per_page value matches the response Array size
        assertThat(getResponseArraySize(response), equalTo(Integer.parseInt(queryParams.get(PER_PAGE_PARAMETER))));
    }

    /**
     * This test method does the following:
     * - sends GET request to /autocomplete endpoint with valid query parameter and value
     * - verifies the maximum number of breweries returned in response JSON array is 15.
     * - validates JSON schema of response body with Jackson DTO
     * - validates response status code is 200 OK
     */
    @Test(description = "Scenario: Autocomplete Search with valid query parameters",
            dataProvider = "autocompleteData", dataProviderClass = BreweryDataProviders.class)
    public void testAutocompleteWithMultipleQueryValues(Map<String, String> queryParams) throws JsonProcessingException {
        Response response = sendGetRequestWithQueryParameters(queryParams, AUTOCOMPLETE_ENDPOINT);
        response.then().assertThat().statusCode(200);
        validateAutocompleteJsonSchemaAndThatFieldsNotNull(response);
        // validates that per_page value doesn't exceed its maximum value = 15 according to the API documentation
        assertThat(getResponseArraySize(response), lessThanOrEqualTo(15));


    }


    /**
     * This test does the following:
     * - sends requests with an empty "query" parameter
     * - verifies that it did not crash the program
     * - verifies that empty JSON array is returned in response
     * It uses TestNG @DataProvider annotation to run test multiple times with different sets of data
     **/
    @Test(description = "Scenario: Request with empty query parameter")
    public void testSearchWithAnEmptyQueryParameter() {

        //List that contains two endpoint for further usage in stream to avoid code repetition
        List<String> endpoints = List.of(SEARCH_ENDPOINT, AUTOCOMPLETE_ENDPOINT);

        //Creation of the SoftAssert to perform multiple assertions without stopping the test execution
        SoftAssert softAssert = new SoftAssert();

        endpoints.stream()
                .map(endpoint -> sendGetRequestWithQueryParameters(Map.of(QUERY_PARAMETER, ""), endpoint))
                .forEach(response -> {
                    // soft assert that status code = 200
                    softAssert.assertEquals(response.getStatusCode(), 200);
                    // soft assert that body content is an empty JSON array
                    softAssert.assertEquals(response.getBody().asString(), "[]");
                });

        // Call assertAll to collect results of softAsserts and fail the test if any assertion fails
        softAssert.assertAll();
    }
}