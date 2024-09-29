package org.example.pro;

import org.example.pro.boundries.PeopleBoundary;
import org.example.pro.entities.PeopleEntity;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ApplicationTests {
    WebClient webClient;

    @BeforeEach
    void setup() {
        webClient = WebClient.create("http://localhost:8080/people");
        System.out.println("WebClient has been initialized.");
    }

    @Test
    void contextLoads() {
        PeopleEntity person = new PeopleEntity();

        // הגדרת נתונים
        person.setEmail("john.doe@gmail.com");
        person.setCountry("IL");
        person.setCity("New York");
        person.setZip("10001");
        person.setRoles(new String[]{"ADMIN", "USER"});
        person.setBirthdate(LocalDate.of(1990, 5, 15));
        person.setPassword("securepassword123");
        person.setAge(34);
        person.setFirst("John");
        person.setLast("Doe");

        System.out.println("Sending the following person to the server: " + person);

        assertThat(  Flux.just( webClient.post()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(new PeopleBoundary(person))
                        .retrieve()
                        .bodyToMono(PeopleBoundary.class)
                        .block(),
                webClient.post()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(new PeopleBoundary(person).setEmail("fg@gmail.com"))
                        .retrieve()
                        .bodyToMono(PeopleBoundary.class)
                        .block(),
                webClient.post()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(new PeopleBoundary(person).setEmail("fgkl@gmail.com"))
                        .retrieve()
                        .bodyToMono(PeopleBoundary.class)
                        .block()).collectList().block());


        System.out.println("Test passed successfully.");
    }

    @AfterEach
    void cleanup() {
        System.out.println("Cleaning up data...");
        webClient.delete()
                .retrieve()
                .bodyToMono(Void.class)
                .block();
        System.out.println("Data cleanup completed.");
    }
}
