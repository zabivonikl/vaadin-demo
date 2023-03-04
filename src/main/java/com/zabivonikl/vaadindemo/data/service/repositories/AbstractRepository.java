package com.zabivonikl.vaadindemo.data.service.repositories;

import com.zabivonikl.vaadindemo.data.entity.AbstractEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

@NoRepositoryBean
public interface AbstractRepository<T extends AbstractEntity> extends
        JpaRepository<T, UUID>,
        JpaSpecificationExecutor<T> {
    Page<T> find(@Param("searchTerm") String filter, Pageable pageable);
}
