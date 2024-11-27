package com.example.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Data Transfer Object for Brewery API response JSON to simplify the deserialization process
 * <p>
 * Added @JsonProperty annotation only for fields where the JSON property name differs from the Java naming convention.
 */

public class BreweryDTO {

    private String id;

    private String name;

    @JsonProperty("brewery_type")
    private String breweryType;

    @JsonProperty("address_1")
    private String address1;

    @JsonProperty("address_2")
    private String address2;

    @JsonProperty("address_3")
    private String address3;

    private String city;

    @JsonProperty("state_province")
    private String stateProvince;

    @JsonProperty("postal_code")
    private String postalCode;

    private String country;

    private String longitude;

    private String latitude;

    private String phone;

    @JsonProperty("website_url")
    private String websiteUrl;

    private String state;

    private String street;


    /**
     * Getters methods for fields
     * <p>
     * In a real project, I would prefer to avoid writing these large amount of getters code
     * by using the Lombok plugin and its @Getter annotation above the class name to generate getters automatically.
     * <p>
     * But since using Lombok plugin may require additional setup in the IDE settings, I decided not to use it
     * for this test task to simplify the review process.
     */

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getBreweryType() {
        return breweryType;
    }

    public String getAddress1() {
        return address1;
    }

    public String getAddress2() {
        return address2;
    }

    public String getAddress3() {
        return address3;
    }

    public String getCity() {
        return city;
    }

    public String getStateProvince() {
        return stateProvince;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getCountry() {
        return country;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getPhone() {
        return phone;
    }

    public String getWebsiteUrl() {
        return websiteUrl;
    }

    public String getState() {
        return state;
    }

    public String getStreet() {
        return street;
    }
}