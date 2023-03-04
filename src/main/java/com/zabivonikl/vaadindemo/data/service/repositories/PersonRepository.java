package com.zabivonikl.vaadindemo.data.service.repositories;

import com.zabivonikl.vaadindemo.data.entity.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PersonRepository extends AbstractRepository<Person> {
    @Override
    @Query(
            "select p from Person p " +
                    "where lower(p.firstName) like lower(concat('%', :searchTerm, '%')) " +
                    "or lower(p.lastName) like lower(concat('%', :searchTerm, '%'))"
    )
    Page<Person> find(@Param("searchTerm") String filter, Pageable pageable);
}
