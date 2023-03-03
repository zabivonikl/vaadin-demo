package com.zabivonikl.vaadindemo.data.service.dataproviders;

import com.zabivonikl.vaadindemo.data.entity.Person;
import com.zabivonikl.vaadindemo.data.service.AbstractService;

public class PersonsProvider extends AbstractDataProvider<Person> {
    public PersonsProvider(AbstractService<Person> service) {
        super(service);
    }
}
