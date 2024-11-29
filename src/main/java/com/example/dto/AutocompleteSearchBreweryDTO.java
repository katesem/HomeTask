package com.example.dto;

/**
 * Data Transfer Object for /autocomplete endpoint JSON response  to simplify the deserialization process
 * <p>
 * It helps to verify that /autocomplete endpoint JSON response  contains ONLY 2 fields: id and name.
 * and it is impossible to check with SearchBreweryDTO
 */
public class AutocompleteSearchBreweryDTO {

    private String id;

    private String name;

    //Getters
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}

