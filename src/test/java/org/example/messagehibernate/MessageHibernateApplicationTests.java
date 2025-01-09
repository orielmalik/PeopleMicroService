package org.example.messagehibernate;

import org.example.messagehibernate.Boundries.PeopleBoundary;
import org.example.messagehibernate.Controller.RestPeopleController;
import org.example.messagehibernate.Tools.ValidationUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@EnableWebSecurity
@SpringBootTest(webEnvironment = WebEnvironment.NONE)
class MessageHibernateApplicationTests {

    private RestTemplate restTemplate;
    private final String baseUrl = "http://localhost:8080/people";

    @BeforeEach
    void setup() {
        restTemplate = new RestTemplate();
    }

    static Stream<Arguments> createRandomJsons() {
        Stream.Builder<Arguments> lst = Stream.builder();
        for (int i = 0; i < 10; i++) {
            lst.add(Arguments.of(ValidationUtils.createRandomPerson()));
        }
        return lst.build();
    }

    @Test
    void getAll() {
        // ביצוע בקשת GET
        ResponseEntity<String> response = restTemplate.getForEntity(baseUrl, String.class);
        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(response.getBody()).isNotNull();
    }

    @ParameterizedTest
    @MethodSource("createRandomJsons")
    void testWithHashMap(PeopleBoundary map) {
        // ביצוע בקשת POST
        ResponseEntity<PeopleBoundary> response = restTemplate.postForEntity(baseUrl, map, PeopleBoundary.class);
        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(response.getBody()).isNotNull();
        System.out.println("Created Person ID: " + response.getBody().getId());
    }

    @AfterEach
    void tearDown() {
        // ביצוע בקשת DELETE
        restTemplate.delete(baseUrl,"*");
    }
}
