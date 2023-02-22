package com.zabivonikl.vaadindemo.data.service;

import com.zabivonikl.vaadindemo.data.entity.Person;

import com.zabivonikl.vaadindemo.data.service.repositories.PersonRepository;
import org.springframework.stereotype.Service;

@Service
public class PersonService extends AbstractService<Person> {
    public PersonService(PersonRepository repository) {
        super(repository);
    }
}
