package com.zabivonikl.vaadindemo.data.service;

import com.zabivonikl.vaadindemo.data.entity.AbstractEntity;

import java.util.List;
import java.util.Optional;

import com.zabivonikl.vaadindemo.data.service.repositories.AbstractRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public abstract class AbstractService<T extends AbstractEntity> {
    private final AbstractRepository<T> repository;

    protected AbstractService(AbstractRepository<T> repository) {
        this.repository = repository;
    }

    public T add(T entity) {
        return repository.save(entity);
    }

    public Optional<T> get(Long id) {
        return repository.findById(id);
    }

    public T update(T entity) {
        return repository.save(entity);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public Page<T> list(Pageable pageable) {
        return repository.findAll(pageable);
    }

    /*
    public Page<T> list(Pageable pageable, Specification<T> filter) {
        return repository.findAll(filter, pageable);
    }
    */

    public List<T> findAll(String stringFilter) {
        if (stringFilter == null || stringFilter.isEmpty()) {
            return repository.findAll();
        } else {
            return repository.search(stringFilter);
        }
    }

    public int count() {
        return (int) repository.count();
    }

}
