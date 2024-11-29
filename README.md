
#  "Part 1. Search Breweries" method: List of the scenarios not covered by auto-tests in this Assignment Task

### 1. Test Scenario: Response doesn't contain duplicate records
**Steps:**
Send GET request to URLs: <br>

https://api.openbrewerydb.org/v1/breweries/search
https://api.openbrewerydb.org/v1/breweries/autocomplete
with any valid query parameter `query` value which is expected to return search results.

**Test Data Example:**
Query parameters: `query=California`

**Expected Result:**  
Each brewery object in the JSON response is unique.

---

### 2. Test Scenario: Missing query parameter
**Steps:**  
Send GET request to URLs: 

https://api.openbrewerydb.org/v1/breweries/search
https://api.openbrewerydb.org/v1/breweries/autocomplete <br> 
without any query parameters

Expected Result: An empty JSON array ([]), meaning no breweries were found or matched the search criteria.

---


### 3. Test Scenario: Unique query value with no matching results
**Steps:**  
Send GET request to URLs:

https://api.openbrewerydb.org/v1/breweries/search
https://api.openbrewerydb.org/v1/breweries/autocomplete <br>
with query parameter value that does not match any of the fields values in all breweries. 
To automate it - the unique value can be generated using Java randomizer method.

**Test Data Example:**
Query parameters: `query=&&&&&&&&&&&&&&&&&&&&&&&&`

**Expected Result:**  
An empty JSON array ([]), meaning no breweries were found or matched the search criteria.

---


### 4. Test Scenario: Incorrect endpoint format**
**Steps:**  
Send GET request to URLs with invalid endpoint like:

https://api.openbrewerydb.org/v1/breweries/srch
https://api.openbrewerydb.org/v1/breweries/acomplete <br>
with valid query parameter "query" value which is expected to return search results.

**Test Data Example:**
Query parameters: `query=California`
**Expected Result:** response contains an error message. Status code is 404.
---


### 5. Test Scenario: Invalid per_page value
**Steps:**  
Send GET request to URLs: <br>

https://api.openbrewerydb.org/v1/breweries/search
with valid query parameter `query`and `per_page` value less than 1. <br>

**Test Data Example:** <br>

Query parameters: `query=California&per_page=0` (`per_page` value is less than min value 1) <br>

**Expected Result:**  
An empty JSON array ([])

---

### 6. Test Scenario:  URL that exceeds the maximum allowed length for a GET request
**Steps:**  
Send GET request to URLs: <br>

https://api.openbrewerydb.org/v1/breweries/search
https://api.openbrewerydb.org/v1/breweries/autocomplete <br>
with query parameter `query` with a value that causes the resulting request URL to exceed 
the maximum allowed length for a GET request (2048 characters).

**Test Data Example:**
Query parameters: `query=... (a value approximately 2020 characters long).`

**Expected Result:** 
The response should contain an error message indicating the issue with the request.
---



# "Part 2. List Breweries" method: Automation strategy

<b>Tools: </b>
- Java
- Maven/Gradle - for project build and dependencies management
- Rest-Assured - for implementation of API requests
- Jackson/GSON - for JSON serialization and deserialization + usage of Lombok plugin for at least @Getter annotation to avoid creation of many getters inside the class
- TestNG - for tests construction
- Log4j library - for logging purposes
- Allure reports - for generating comprehensive test reports
- Gitlab CI/CD - for continuous integration and scheduled test runs.


#### Realisation details:
- **Automation tests should cover both positive and negative scenarios.**
- **Usage of @DataProviders:** since this test suite requires many data sets for different combinations of query parameters and their values. It is reasonable to apply TestNG @DataProvider for this purpose
- Response status codes should be verified
- **Validation of JSON schema should be applied:** required fields are present, no null fields when it is not expected, no extra fields in response, correct field types, correct structure of JSON array
- **DTO**s should be created via Jackson to validate individual field values 
- To faster identify errors/failures it is important to apply **logging**
- Automation framework structure can be similar to the one I created for this task and include additional things: at least folder for Allure reporting and .gitlab-ci.yml config file

#### Positive Scenarios:

- Validate that the API returns the expected status code (200 OK) for valid query parameters.
- Response payload/JSON schema validation: Verify the response payload matches the structure defined in the Open Brewery API documentation, all mandatory fields are present.
- Verify case sensitivity 
- Ensure specific query parameters filter results as expected (e.g., by_city=Denver returns only breweries in Denver).
- Response Headers Validation: verify that headers like "Content-Type: application/json" are returned.


#### Negative Scenarios:

- Ensure invalid query parameters or values return appropriate status codes (e.g., 400 Bad Request or ignores it).
- Verify that the API responds correctly to missing mandatory query parameters.
- Test invalid query structures `(per_page=string, by_city=[5678]) ` appropriate status codes (e.g., 400 Bad Request)



### Test design techniques:

**Error guessing:** <br>
Since the requirements are not very detailed, I can use my own experience and intuition to anticipate possible errors or problems that may occur, for example: <br>

- Usage Special characters as a query parameter value<br>
- Empty query parameters
- Usage of unsupported HTTP methods etc. 


**Combinatorial Testing:** <br>
it can be achieved by using combinations of different query parameters in one request: `by_city=Denver&by_state=Colorado)` <br><br>


**Boundary values analysis:** <br>
(Example: per_page parameter: had max value = 200, so I can test it with  `1, 200 and 0, 201 ` ) <br> <br>


**Equivalence partitioning**: <br>
(Example: per_page parameter had max value = 200, so I can test it with  `1, 200  and 0, 201 and 100 ` ) <br>
