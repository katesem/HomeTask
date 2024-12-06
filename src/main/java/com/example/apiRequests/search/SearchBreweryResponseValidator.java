package com.example.apiRequests.search;

import com.example.dto.AutocompleteSearchBreweryDTO;
import com.example.dto.SearchBreweryDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * Class for operations with API responses
 */
public class SearchBreweryResponseValidator {

    // Logger instance for logging messages
    private static final Logger logger = LogManager.getLogger(SearchBreweryResponseValidator.class);

    /**
     * Returns JSON Array size
     **/
    public static int getResponseArraySize(Response response) {
        return response.jsonPath().getList("").size();
    }

    /**
     * Returns JSON Array size
     **/
    public static void validateAutocompleteJsonSchemaAndThatFieldsNotNull(Response response) throws JsonProcessingException {

        // Deserializing JSON response into a list of AutocompleteSearchBreweryDTO objects using Jackson
        List<AutocompleteSearchBreweryDTO> breweries = constructDTO(response, new TypeReference<>() {
        });

        breweries.forEach(brewery -> {
            // Verify that ID and Name fields are not null
            if (brewery.getId() == null || brewery.getName() == null) {
                throw new AssertionError("Brewery object contains null fields: " + brewery);
            }
        });
    }

    /**
     * Validates whether the JSON response structure matches the expected DTO and verifies that all objects
     * in the response contain at least one field matching the provided search query parameter value.
     **/
    public static void validateResponseStructureAndQueryMatch(Response response, String queryParamValue) throws Exception {

        // Deserializing JSON response into a list of SearchBreweryDTO objects using Jackson
        List<SearchBreweryDTO> breweries = constructDTO(response, new TypeReference<>() {
        });
        List<String> unmatchedIds = new ArrayList<>(); // Store breweries ID's with no search matches

        // Iterating through each BreweryDTO object
        for (SearchBreweryDTO brewery : breweries) {

            // Calling the checkFieldsForValue method for every brewery object to verify if it matches the search query parameter value
            boolean matchFound = verifyDTOFieldsMatchTheSearchQuery(brewery, queryParamValue);

            // This code block is optional. It logs information about matches and mismatches in the Brewery object.
            // If at least one field value in the Brewery object matches the search parameter,
            // it logs the brewery's name at the INFO level.
            // Otherwise, it logs a warning with the brewery's name and adds its ID to the unmatchedIds list for further tracking.
            if (matchFound) {
                logger.info("Match found in Brewery: {}", brewery.getName());
            } else {
                logger.warn("No match found in Brewery: {}", brewery.getName());
                unmatchedIds.add(brewery.getId());
            }
        }

        // If no matches are found for the search query in the brewery objects,
        // the IDs of unmatched breweries are logged and test fails with an AssertionError.
        if (!unmatchedIds.isEmpty()) {
            logger.error("No match found in the following breweries (IDs): \n{}", unmatchedIds);
            throw new AssertionError("Test failed because some breweries did not contain the query parameter value.");
        }
    }

    /**
     * Method for deserializing JSON response into a list of DTO objects of target class
     * Throws JsonProcessingException If deserialization fails
     */
    public static <T> List<T> constructDTO(Response response, TypeReference<List<T>> dtoType) throws JsonProcessingException {

        // Initialize ObjectMapper with strict property validation
        // if non DTO fields will be present in response - test execution will stop with error
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);

        // Deserialize the response into a list of DTO objects
        return objectMapper.readValue(response.getBody().asString(), dtoType);
    }


    /**
     * This method validates if the provided query parameter value matches any field value in the DTO.
     * As an alternative it can be used a reflection to dynamically access the fields of the DTO
     * But for this static DTO, using getters is simpler, despite the fact that the List fieldValues seems a little hardcoded
     */
    private static boolean verifyDTOFieldsMatchTheSearchQuery(SearchBreweryDTO brewery, String queryParamValue) {

        // If queryParamValue contains underscores, it should be replaced with spaces for search results validation.
        // This is implemented according to the API documentation instruction:
        // "For the query parameter, you can use underscores or URL encoding for spaces."
        String searchQueryParamValue = queryParamValue.replace("_", " ").toLowerCase();

        System.out.println(searchQueryParamValue);
        // Creation of the list with DTO field values
        List<String> fieldValues = Stream.of(
                        brewery.getName(),
                        brewery.getCity(),
                        brewery.getState(),
                        brewery.getPostalCode(),
                        brewery.getBreweryType(),
                        brewery.getAddress1(),
                        brewery.getAddress2(),
                        brewery.getAddress3(),
                        brewery.getCountry(),
                        brewery.getStateProvince(),
                        brewery.getPhone(),
                        brewery.getWebsiteUrl()
                ).filter(Objects::nonNull) // Exclude null values
                .collect(Collectors.toList());// Collect into a List

        // Using the stream API methods to check if any DTO field value contains the query parameter
        // if any match found -> this method returns true, otherwise - `false`.
        return fieldValues.stream()
                .filter(Objects::nonNull) // Ignore null field values
                .anyMatch(value -> value.toLowerCase().contains(searchQueryParamValue));
    }


}