package com.zabivonikl.vaadindemo.data.service.repositories;

import com.zabivonikl.vaadindemo.data.entity.AbstractEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

@NoRepositoryBean
public interface AbstractRepository<T extends AbstractEntity> extends
        JpaRepository<T, Long>,
        JpaSpecificationExecutor<T> {
    List<T> search(String searchTerm);
}
