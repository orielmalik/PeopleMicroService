package org.example.messagehibernate.interfaces;

import org.example.messagehibernate.Boundries.PeopleBoundary;

import java.util.List;

public interface myService {

    public List<PeopleBoundary> getAll(String type,String value);
    public PeopleBoundary create(PeopleBoundary peopleBoundary);

    public PeopleBoundary update(PeopleBoundary peopleBoundary);
    public void delete(String id);


}
