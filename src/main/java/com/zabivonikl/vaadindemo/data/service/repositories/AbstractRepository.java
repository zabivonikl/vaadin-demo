package com.zabivonikl.vaadindemo.data.service.repositories;

import com.zabivonikl.vaadindemo.data.entity.AbstractEntity;
import com.zabivonikl.vaadindemo.data.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AbstractRepository<T extends AbstractEntity> extends
        JpaRepository<T, Long>,
        JpaSpecificationExecutor<Person> {
}
