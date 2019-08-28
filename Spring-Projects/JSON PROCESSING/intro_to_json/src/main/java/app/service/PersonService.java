package app.service;

import app.domain.model.Person;

public interface PersonService {
    Person getById(long id);

    void save(Person person);
}
