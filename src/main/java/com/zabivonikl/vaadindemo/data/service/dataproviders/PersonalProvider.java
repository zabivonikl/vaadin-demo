package com.zabivonikl.vaadindemo.data.service.dataproviders;

import com.zabivonikl.vaadindemo.data.entity.Person;
import com.zabivonikl.vaadindemo.data.service.AbstractService;
import org.springframework.stereotype.Service;

@Service
public class PersonalProvider extends AbstractDataProvider<Person> {
    public PersonalProvider(AbstractService<Person> service) {
        super(service);
    }
}
