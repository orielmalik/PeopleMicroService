package org.example.messagehibernate.Service;


import org.example.messagehibernate.Boundries.PeopleBoundary;
import org.example.messagehibernate.Entity.PeopleEntity;
import org.example.messagehibernate.Exceptions.BadRequest400;
import org.example.messagehibernate.Tools.ValidationUtils;
import org.example.messagehibernate.interfaces.MyCrud;
import org.example.messagehibernate.interfaces.myService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class serviceImpl implements myService {

    public MyCrud myCrud;

    public serviceImpl(MyCrud myCrud)
    {
        this.myCrud=myCrud;
    }

    @Override
    public List<PeopleBoundary> getAll(String type, String value) {

        if(ValidationUtils.NullOrEmptystr(type)&&ValidationUtils.NullOrEmptystr(value)){
            return  this.myCrud.findAll().stream().map(peopleEntity -> {
                return new PeopleBoundary(peopleEntity);
            }).collect(Collectors.toList());
        }
        else if(ValidationUtils.NullOrEmptystr(value))
        {
            throw  new BadRequest400("err");
        }
        else {
            switch (type.toLowerCase())
            {
                case "email" ->
                {
                    return  this.myCrud.findAllByEmail(value).stream().map(peopleEntity -> {
                        return new PeopleBoundary(peopleEntity);
                    }).collect(Collectors.toList());
                }

                default -> throw  new BadRequest400("err");

            }
        }
    }

    @Override
    public PeopleBoundary create(PeopleBoundary peopleBounday) {
        if (!ValidationUtils.isEmailFormat(peopleBounday.getEmail())||
                !ValidationUtils.isValidDateFormat(peopleBounday.getBirth()))
        {
            throw new BadRequest400("not found");
        }
        boolean b=  this.myCrud.findById(peopleBounday.getId())
                .isEmpty()&&this.myCrud
                .findAllByEmail(peopleBounday.getEmail()).isEmpty();
        if(!b) {
            throw new BadRequest400("not found");
        }
        PeopleEntity target=peopleBounday.toEntity();
        ValidationUtils.stringToDateSecured(peopleBounday.getBirth(), target); // 100% true
        PeopleEntity targ=this.myCrud.save(target);

        return  new PeopleBoundary(targ);
    }

    @Override
    public PeopleBoundary update(PeopleBoundary peopleBounday) {
        if (!ValidationUtils.isEmailFormat(peopleBounday.getEmail())||
                !ValidationUtils.isValidDateFormat(peopleBounday.getBirth()))
        {
            throw new BadRequest400("not found");
        }
        boolean b=  !this.myCrud.findById(peopleBounday.getId())
                .isEmpty()&&(this.myCrud
                .findByEmail(peopleBounday.getEmail())==null||this.myCrud
                .findByEmail(peopleBounday.getEmail()).getId().equals(peopleBounday.getId()));
        if(!b) {
            throw new BadRequest400("not found");
        }
        PeopleEntity target=peopleBounday.toEntity();
        ValidationUtils.stringToDateSecured(peopleBounday.getBirth(), target); // 100% true
        PeopleEntity targ=this.myCrud.save(target);

        return  new PeopleBoundary(targ);
    }

    @Override
    public void delete(String id) {
        if(id.equals("*"))
        {
            this.myCrud.deleteAll();
            return;
        }
        this.myCrud.findById(id)
                .orElseThrow(() -> {throw new BadRequest400("sa");});
    }

}



