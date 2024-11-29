package utils;

import org.testng.annotations.DataProvider;

import java.util.Map;

import static com.example.constants.BreweryConstants.PER_PAGE_PARAMETER;
import static com.example.constants.BreweryConstants.QUERY_PARAMETER;

/**
 * Class for storing test data providers
 */
public class BreweryDataProviders {

    //This data provider contains a set of different values for query parameter to test /autocomplete endpoint
    // To test search different formats for query parameter value
    @DataProvider(name = "autocompleteData")
    public static Object[][] autocompleteData() {
        return new Object[][]{
                {Map.of(QUERY_PARAMETER, "12345")},    // numeric value
                {Map.of(QUERY_PARAMETER, "%%$")},    // Special characters
                {Map.of(QUERY_PARAMETER, "los_angeles")}   // Space as URL encoding
        };
    }

    // This data provider contains a fata set for query parameter to test /search endpoint
    // To test search case sensitivity, different representations of space and different variants of expected response sizes
    @DataProvider(name = "caseSensitivitySpacesAndSizeData")
    public static Object[][] caseSensitivitySpacesAndSizeData() {
        return new Object[][]{
                // Combine query and pagination parameters
                {Map.of(QUERY_PARAMETER, "san diego", PER_PAGE_PARAMETER, "3")},    // Valid per_page value
                {Map.of(QUERY_PARAMETER, "SAN DIEGO", PER_PAGE_PARAMETER, "15")},    // Maximum per_page value
                {Map.of(QUERY_PARAMETER, "san_diego", PER_PAGE_PARAMETER, "20")}   // Greater than maximum per_page value
        };
    }
}