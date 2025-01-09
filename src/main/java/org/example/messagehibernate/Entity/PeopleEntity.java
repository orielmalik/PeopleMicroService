package org.example.messagehibernate.Entity;


import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "things")
public class PeopleEntity {

    @Id
    private String id;
    private String First;
    private String Last;
    private  String email;

    //dd-mm-yyyy
    @Temporal(TemporalType.DATE)
    private Date birth;






    public  PeopleEntity(){}





    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    public String getFirst() {
        return First;
    }

    public void setFirst(String first) {
        First = first;
    }

    public String getLast() {
        return Last;
    }

    public void setLast(String last) {
        Last = last;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
