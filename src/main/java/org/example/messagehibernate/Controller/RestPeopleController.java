package org.example.messagehibernate.Controller;

import org.example.messagehibernate.Boundries.PeopleBoundary;
import org.example.messagehibernate.Exceptions.BadRequest400;
import org.example.messagehibernate.Tools.ValidationUtils;
import org.example.messagehibernate.interfaces.myService;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/people")
public class RestPeopleController {

    private  myService myService;
    public  RestPeopleController(myService mys)
    {
        this.myService=mys;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,produces =MediaType.APPLICATION_JSON_VALUE )
    public PeopleBoundary post(@RequestBody PeopleBoundary peopleBoundary)
    {
        if( ! ValidationUtils.isEmailFormat(peopleBoundary.getEmail()))
        {
            throw new  BadRequest400("email");
        }
        return  this.myService.create(peopleBoundary);

    }
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE,produces =MediaType.APPLICATION_JSON_VALUE )
    public PeopleBoundary update(@RequestBody PeopleBoundary peopleBoundary)
    {
        if( ! ValidationUtils.isEmailFormat(peopleBoundary.getEmail()))
        {
            throw new  BadRequest400("email");
        }
        return  this.myService.update(peopleBoundary);


    }


    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<PeopleBoundary>getAll(@RequestParam(required = false,defaultValue = " ") String type,@RequestParam(required = false,defaultValue = " ") String value)
    {
        return this.myService.getAll(type,value);
    }

    @DeleteMapping ()
    public void getAll(@RequestParam(required = false) String id)
    {
         this.myService.delete(id);
    }





}
