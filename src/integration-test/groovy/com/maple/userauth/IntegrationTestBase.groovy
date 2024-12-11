package com.maple.userauth

import com.maple.userauth.dao.DataSourceInitializer
import io.restassured.RestAssured
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.jdbc.core.JdbcUser
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT

@ActiveProfiles("integrationtest")
@SpringBootTest(classes = [ServiceApplication], webEnvironment = RANDOM_PORT)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(initializers = [DataSourceInitializer])
class IntegrationTestBase extends Specification {

    @LocalServerPort
    private int serverPort
    @Autowired
    private JdbcUser jdbcUser

    def setup() {
        RestAssured.port = serverPort;
    }

    def cleanup() {
        jdbcUser.update("DELETE FROM user")
    }
}