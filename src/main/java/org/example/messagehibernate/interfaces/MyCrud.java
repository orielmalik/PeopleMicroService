package org.example.messagehibernate.interfaces;

import org.example.messagehibernate.Boundries.PeopleBoundary;
import org.example.messagehibernate.Entity.PeopleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface MyCrud extends JpaRepository<PeopleEntity,String> {


       List<PeopleEntity> findAllByBirthBetween(Date start, Date end);


    List<PeopleEntity> findAllByEmail(String value);
    PeopleEntity findByEmail(String value);
}
