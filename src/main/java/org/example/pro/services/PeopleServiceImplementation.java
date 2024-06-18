package org.example.pro.services;

import org.example.pro.Exception.BadRequest400;
import org.example.pro.boundries.PeopleBoundary;
import org.example.pro.entities.PeopleEntity;
import org.example.pro.interfaces.PeopleCrud;
import org.example.pro.interfaces.PeopleService;
import org.example.pro.tools.ValidationUtils;
import org.springframework.data.annotation.Id;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

@Service
public class PeopleServiceImplementation implements PeopleService {
    private PeopleCrud peopleCrud;
    public PeopleServiceImplementation( PeopleCrud peopleCrud )
    {
        this.peopleCrud=peopleCrud;
    }

    //post
    @Override
    public Mono<PeopleBoundary> create(PeopleBoundary boundary) {
        return  this.peopleCrud.findById(boundary.getEmail())
                .flatMap(existingPerson -> {
                    // If a person with the given email is found, return an error Mono
                    return Mono.error(new RuntimeException("User already exists with this email"));
                })
                .switchIfEmpty(Mono.defer(() -> {
                    // Ensure that the creation of the PeopleEntity is deferred until needed
                    PeopleEntity entity = boundary.toEntity();
                    return Mono.just(entity);
                }))
                .flatMap(peopleEntity -> {
                    // Perform validation
                    if (!ValidationUtils.isEmailFormat(boundary.getEmail())) {
                        return Mono.error(new BadRequest400("ERROR WITH EMAIL"));
                    } else if (!ValidationUtils.hasTwoUppercaseLetters(boundary.getAddress().getCountry())) {
                        return Mono.error(new BadRequest400("ERROR WITH COUNTRY NAME"));
                    }
                    else if(!ValidationUtils.isValidLocalDateFormat(boundary.getBirthdate()))
                    {
                        return Mono.error(new BadRequest400("ERROR WITH DATE FORMAT"));

                    }
                    else if(boundary.getPassword().length()<5)
                    {
                        return Mono.error(new BadRequest400("PASSWORD LENGTH <5"));

                    }
                    // Save the entity and return the saved entity
                    return this.peopleCrud.save((PeopleEntity) peopleEntity); // Ensure type casting
                })
                .map(peopleEntity -> {
                    // Mask the password
                    peopleEntity.setPassword("");
                    // Convert the saved entity to a boundary object and return it
                    return new PeopleBoundary(peopleEntity);
                })
                .log();


    }

//delete
    @Override
    public Mono<Void> deleteAll() {
        return this.peopleCrud.deleteAll();
    }

//put-we dont throw error everyTime
    @Override
    public Mono<Void> update( String email, String password, PeopleBoundary peopleBoundary) {
        return this.peopleCrud.findById(email)
                .flatMap(peopleEntity -> {
                    if(!peopleEntity.getPassword().equals(password)) {
                        return Mono.error(new BadRequest400("password does not match"));

                    }
                    else if(!ValidationUtils.isValidLocalDateFormat(peopleBoundary.getBirthdate()))
                    {
                        return Mono.error(new BadRequest400("birthdate  not at format"));
                    }
                    else if(peopleBoundary.getPassword().length()<5)
                    {
                        return Mono.error(new BadRequest400("PASSWORD LENGTH <5"));

                    }
                    else {
                        peopleBoundary.setEmail(peopleEntity.getEmail());//cant change email

                        if(!ValidationUtils.hasTwoUppercaseLetters(peopleBoundary.getAddress().getCountry())){
                        peopleBoundary.getAddress().setCountry(peopleEntity.getCountry());}//if it does not match save the last-we wanted
                        return this.peopleCrud.save(peopleBoundary.toEntity());
                    }

                })
                .then().log();
    }


//get
    @Override
    public Flux<PeopleBoundary> getAll() {
        return this.peopleCrud.findAll().map(peopleEntity -> {
            peopleEntity.setPassword("******");
            return new PeopleBoundary(peopleEntity);
        }).log();
    }

    @Override
    public Flux<PeopleBoundary> getByLastName( String value) {
        return this.peopleCrud.findByLast(value).map(peopleEntity -> {
            peopleEntity.setPassword("******");
            return new PeopleBoundary(peopleEntity);
        }).log();
    }

    @Override
    public Flux<PeopleBoundary> getPeopleByCountry(String country) {
        return this.peopleCrud.findByCountry(country)
                .map(peopleEntity -> { peopleEntity.setPassword("****") ;
                    return new PeopleBoundary(peopleEntity);})
                .log();
    }


    @Override
    public Mono<PeopleBoundary> getByEmail(String email, String password) {
        return this.peopleCrud.findById(email)
                .flatMap(peopleEntity -> {
                    if (peopleEntity.getPassword().equals(password)) {
                        // Mask the password before returning
                        peopleEntity.setPassword("***");
                        return Mono.just(new PeopleBoundary(peopleEntity));
                    } else {
                        return Mono.error(new BadRequest400("ERROR "));
                    }
                }).log();

    }
    public Flux<PeopleBoundary> getPeopleByAgeRange(int minimumAge, int maximumAge) {
        LocalDate today = LocalDate.now();
        LocalDate minDate = today.minusYears(maximumAge);
        LocalDate maxDate = today.minusYears(minimumAge);

        return peopleCrud.findByBirthdateBetween(minDate, maxDate)
                .map(peopleEntity -> {
                    PeopleBoundary peopleBoundary = new PeopleBoundary(peopleEntity);
                    peopleBoundary.setPassword("***********"); // Mask the password
                    return peopleBoundary;
                });
    }
@Override
    public Flux<PeopleBoundary> getPeopleByMaximumAge(int maximumAge) {
        return getPeopleByAgeRange(0, maximumAge);
    }

    @Override
    public Flux<PeopleBoundary> getByEmailOnly(String value) {
        if (!ValidationUtils.isEmailFormat(value)) {
            return Flux.error(new BadRequest400("ERROR "));
        }
        return this.peopleCrud.findById(value)
                .map(peopleEntity ->
                {peopleEntity.setPassword("*****");return new PeopleBoundary((peopleEntity));})
                .flux()
                .log();


    }
    @Override
    public Flux<PeopleBoundary> getPeopleByMinimumAge(int minimumAge) {
        if (minimumAge >= 150) {
            return Flux.error(() -> new BadRequest400("There is no documented evidence of people living over 150 years."));
        }
        LocalDate today = LocalDate.now();
        LocalDate earliestPossibleDate = today.minusYears(150);

        return peopleCrud.findByBirthdateBetween(earliestPossibleDate, today.minusYears(minimumAge))
                .map(peopleEntity -> {
                    PeopleBoundary peopleBoundary = new PeopleBoundary(peopleEntity);
                    peopleBoundary.setPassword(""); // Mask the password
                    return peopleBoundary;
                }).log();
    }



}

