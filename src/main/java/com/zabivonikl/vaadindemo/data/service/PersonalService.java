package com.zabivonikl.vaadindemo.data.service;

import com.zabivonikl.vaadindemo.data.entity.Person;
import com.zabivonikl.vaadindemo.data.service.repositories.PersonalRepository;
import org.springframework.stereotype.Service;

@Service
public class PersonalService extends AbstractService<Person> {
    public PersonalService(PersonalRepository repository) {
        super(repository);
    }
}
