//package com.maple.userauth.api.v1.controller
//
//import com.fasterxml.jackson.databind.ObjectMapper
//import com.manner.user.api.v1.request.UserRequest
//import com.manner.user.api.v1.response.UserResponse
//
//import com.maple.userauth.IntegrationTestBase
//import io.restassured.http.ContentType
//import org.springframework.beans.factory.annotation.Autowired
//import org.springframework.http.HttpStatus
//
//import static io.restassured.RestAssured.given
//import static org.junit.jupiter.api.Assertions.assertEquals
//import static org.junit.jupiter.api.Assertions.assertNotNull
//
//class UserControllerSpecification extends IntegrationTestBase {
//
//    @Autowired
//    private ObjectMapper objectMapper
//
//    def "Test happy cases with create, get one, get all, put, patch, delete user API"() {
//
//        given:
//        def example = UUID.randomUUID().toString()
//        def createRequest = UserRequest.builder()
//                .example(example)
//                .build()
//
//        when: "User is created"
//        def createResponse = given()
//                .body(objectMapper.writeValueAsString(createRequest))
//                .contentType(ContentType.JSON)
//                .post("/api/v1/users")
//                .then()
//                .statusCode(HttpStatus.CREATED.value())
//                .extract()
//                .body()
//                .as(Response.class)
//        then:
//        createResponse
//        def createUser = objectMapper.convertValue(createResponse.getContent(), UserResponse.class);
//        validateResponse(createUser, example)
//        assertEquals(createUser.get_meta().getCreatedDate(), createUser.get_meta().getLastModifiedDate())
//
//        when: "One User is fetched"
//        def getResponse = given()
//                .get("/api/v1/users/${createUser.getId()}")
//                .then()
//                .statusCode(HttpStatus.OK.value())
//                .extract()
//                .body()
//                .as(Response.class)
//        then:
//        getResponse
//        def getUser = objectMapper.convertValue(getResponse.getContent(), UserResponse.class);
//        getUser.getId() == createUser.getId()
//        validateResponse(getUser, example)
//
//        when: "All users are fetched"
//        def getAllResponse = given()
//                .get("/api/v1/users")
//                .then()
//                .statusCode(HttpStatus.OK.value())
//                .extract()
//                .body()
//                .as(Response.class)
//        then:
//        getAllResponse
//        def getAllUser = objectMapper.convertValue(getAllResponse.getContent(), List.class);
//        getAllUser.size() == 1
//        validateResponse(objectMapper.convertValue(getAllUser.get(0), UserResponse.class), example)
//
//        when: "User is updated"
//        def putExample = UUID.randomUUID().toString()
//        def updateRequest = UserRequest.builder()
//                .example(putExample)
//                .build()
//
//        def updateResponse = given()
//                .body(objectMapper.writeValueAsString(updateRequest))
//                .contentType(ContentType.JSON)
//                .put("/api/v1/users/${createUser.getId()}")
//                .then()
//                .statusCode(HttpStatus.OK.value())
//                .extract()
//                .body()
//                .as(Response.class)
//        then:
//        updateResponse
//        def updateUser = objectMapper.convertValue(updateResponse.getContent(), UserResponse.class);
//        updateUser.getId() == createUser.getId()
//        validateResponse(updateUser, putExample)
//        updateUser.get_meta().getLastModifiedDate().isAfter(updateUser.get_meta().getCreatedDate())
//
//        when: "User is patched"
//        def patchExample = UUID.randomUUID().toString()
//        def patchRequest = UserRequest.builder()
//                .example(patchExample)
//                .build()
//
//        def patchResponse = given()
//                .body(objectMapper.writeValueAsString(patchRequest))
//                .contentType(ContentType.JSON)
//                .patch("/api/v1/users/${createUser.getId()}")
//                .then()
//                .statusCode(HttpStatus.OK.value())
//                .extract()
//                .body()
//                .as(Response.class)
//        then:
//        patchResponse
//        def patchUser = objectMapper.convertValue(patchResponse.getContent(), UserResponse.class);
//        patchUser.getId() == createUser.getId()
//        // Only non null fields are updated
//        validateResponse(patchUser, patchExample)
//        patchUser.get_meta().getLastModifiedDate().isAfter(patchUser.get_meta().getCreatedDate())
//
//        when: "User is deleted"
//        given()
//                .delete("/api/v1/users/${createUser.getId()}")
//                .then()
//                .statusCode(HttpStatus.NO_CONTENT.value())
//        then:
//        given()
//                .get("/api/v1/users/${createUser.getId()}")
//                .then()
//                .statusCode(HttpStatus.NOT_FOUND.value())
//    }
//
//    def "Test sad cases with create user API"() {
//
//        when: "Required fields are null when creating user"
//        def createRequest1 = UserRequest.builder().build()
//
//        then:
//        given()
//                .body(objectMapper.writeValueAsString(createRequest1))
//                .contentType(ContentType.JSON)
//                .post("/api/v1/users")
//                .then()
//                .statusCode(HttpStatus.BAD_REQUEST.value())
//
//        when: "Required fields are empty when creating user"
//        def createRequest2 = UserRequest.builder()
//                .example("")
//                .build()
//
//        then:
//        given()
//                .body(objectMapper.writeValueAsString(createRequest2))
//                .contentType(ContentType.JSON)
//                .post("/api/v1/users")
//                .then()
//                .statusCode(HttpStatus.BAD_REQUEST.value())
//    }
//
//    def "Test sad cases with update user API"() {
//
//        given:
//        def createResponse = given()
//                .body(objectMapper.writeValueAsString(UserRequest.builder()
//                        .example(UUID.randomUUID().toString())
//                        .build()))
//                .contentType(ContentType.JSON)
//                .post("/api/v1/users")
//                .then()
//                .statusCode(HttpStatus.CREATED.value())
//                .extract()
//                .body()
//                .as(Response.class)
//        def createUser = objectMapper.convertValue(createResponse.getContent(), UserResponse.class);
//
//        when: "User is not found"
//        def updateRequest1 = UserRequest.builder()
//                .example(UUID.randomUUID().toString())
//                .build()
//
//        then:
//        given()
//                .body(objectMapper.writeValueAsString(updateRequest1))
//                .contentType(ContentType.JSON)
//                .put("/api/v1/users/1")
//                .then()
//                .statusCode(HttpStatus.NOT_FOUND.value())
//
//        when: "Required fields are null when updating user"
//        def updateRequest2 = UserRequest.builder().build()
//
//        then:
//        given()
//                .body(objectMapper.writeValueAsString(updateRequest2))
//                .contentType(ContentType.JSON)
//                .put("/api/v1/users/${createUser.getId()}")
//                .then()
//                .statusCode(HttpStatus.BAD_REQUEST.value())
//
//        when: "Required fields are empty when updating user"
//        def updateRequest3 = UserRequest.builder()
//                .example("")
//                .build()
//
//        then:
//        given()
//                .body(objectMapper.writeValueAsString(updateRequest3))
//                .contentType(ContentType.JSON)
//                .put("/api/v1/users/${createUser.getId()}")
//                .then()
//                .statusCode(HttpStatus.BAD_REQUEST.value())
//    }
//
//    def "Test sad cases with patch user API"() {
//
//        given:
//        def createResponse = given()
//                .body(objectMapper.writeValueAsString(UserRequest.builder()
//                        .example(UUID.randomUUID().toString())
//                        .build()))
//                .contentType(ContentType.JSON)
//                .post("/api/v1/users")
//                .then()
//                .statusCode(HttpStatus.CREATED.value())
//                .extract()
//                .body()
//                .as(Response.class)
//        def createUser = objectMapper.convertValue(createResponse.getContent(), UserResponse.class);
//
//        when: "User is not found"
//        def patchRequest1 = UserRequest.builder()
//                .example(UUID.randomUUID().toString())
//                .build()
//
//        then:
//        given()
//                .body(objectMapper.writeValueAsString(patchRequest1))
//                .contentType(ContentType.JSON)
//                .patch("/api/v1/users/1")
//                .then()
//                .statusCode(HttpStatus.NOT_FOUND.value())
//
//        when: "Required fields are empty when updating user"
//        def patchRequest2 = UserRequest.builder()
//                .example("")
//                .build()
//
//        then:
//        given()
//                .body(objectMapper.writeValueAsString(patchRequest2))
//                .contentType(ContentType.JSON)
//                .patch("/api/v1/users/${createUser.getId()}")
//                .then()
//                .statusCode(HttpStatus.BAD_REQUEST.value())
//    }
//
//    def "Test sad cases with delete user API"() {
//
//        when: "User is not found"
//        then:
//        given()
//                .contentType(ContentType.JSON)
//                .delete("/api/v1/users/1")
//                .then()
//                .statusCode(HttpStatus.NOT_FOUND.value())
//    }
//
//    def validateResponse(UserResponse response, String example) {
//        assertNotNull(response)
//        assertNotNull(response.getId())
//        assertNotNull(response.getData())
//        assertEquals(response.getData().getExample(), example)
//        assertNotNull(response.get_meta().getCreatedDate())
//        assertNotNull(response.get_meta().getLastModifiedDate())
//        true
//    }
//}