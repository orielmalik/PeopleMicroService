package org.example.pro.boundries;

import org.example.pro.entities.PeopleEntity;
import org.example.pro.tools.Address;
import org.example.pro.tools.Name;
import org.example.pro.tools.ValidationUtils;

import java.time.LocalDate;
import java.util.Date;

public class PeopleBoundary {
    private String email;
    private Address address;
    private  String[] roles;
    private String birthdate;
    private  String password;

    private Name name;

    public PeopleBoundary()
    {
    }




    public PeopleBoundary(PeopleEntity entity) {
        this.setBirthdate(ValidationUtils.toBirthdateFormat(entity.getBirthdate()));
        this.setEmail(entity.getEmail());
        this.setPassword(entity.getPassword());
        this.setName(new Name(entity.getFirst(),entity.getLast()));
        this.setAddress(new Address(entity.getCountry(),entity.getCity()
                ,entity.getZip()));

        this.setRoles(entity.getRoles());
    }




    public PeopleEntity toEntity()
    {
        PeopleEntity peopleEntity=new PeopleEntity();
        peopleEntity.setEmail(getEmail());
        peopleEntity.setCity(getAddress().getCity());
        peopleEntity.setCountry(getAddress().getCountry());
        peopleEntity.setZip(getAddress().getZip());
        peopleEntity.setRoles(getRoles());
        LocalDate check=ValidationUtils.fromStringToLocalDatte(getBirthdate());
        peopleEntity.setBirthdate(ValidationUtils.fromStringToLocalDatte(getBirthdate()));
        peopleEntity.setFirst(getName().getFirst());
        peopleEntity.setLast(getName().getLast());
        peopleEntity.setPassword(getPassword());
        return peopleEntity;
    }
//optional
    public void ValidateAge(PeopleEntity peopleEntity)
    {LocalDate date=ValidationUtils.fromStringToLocalDatte(getBirthdate());
        peopleEntity.setAge((LocalDate.now().minusYears(date.getYear())).getYear());

    }

    public String getEmail() {
        return email;
    }

    public PeopleBoundary setEmail(String email) {
        this.email = email;
        return  this;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String[] getRoles() {
        return roles;
    }

    public void setRoles(String[] roles) {
        this.roles = roles;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Name getName() {
        return name;
    }

    public void setName(Name name) {
        this.name = name;
    }
}
