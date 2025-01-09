package org.example.messagehibernate.Boundries;

import org.example.messagehibernate.Entity.PeopleEntity;
import org.example.messagehibernate.Tools.ValidationUtils;

import java.text.ParseException;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.Random;

public class PeopleBoundary {

    private  String id;
    private String birth;
    private  Name name;//Name(String first,String last)
    private String email;


    public PeopleBoundary(){}
    public PeopleBoundary(PeopleEntity peopleEntity){
        this.birth= ValidationUtils.toStringdateFormat(peopleEntity.getBirth());
        this.id=peopleEntity.getId();
        this.name=new Name(peopleEntity.getFirst(),peopleEntity.getLast());
        this.email=peopleEntity.getEmail();

    }
    // בנאי שמקבל תכונות מפורשות
    public PeopleBoundary(String id, String birth, Name name, String email) {
        this.id = id;
        this.birth = birth;
        this.name = name;
        this.email = email;
    }

    // מתודה שיוצרת אובייקט עם ערכים רנדומליים


    public  PeopleEntity toEntity() {
        PeopleEntity pe=new PeopleEntity();
        pe.setId(this.getId());

        pe.setEmail(this.getEmail());
        pe.setLast(this.getName().getLast());
        pe.setFirst(this.getName().getFirst());
        return pe;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Name getName() {
        return name;
    }

    public void setName(Name name) {
        this.name = name;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
