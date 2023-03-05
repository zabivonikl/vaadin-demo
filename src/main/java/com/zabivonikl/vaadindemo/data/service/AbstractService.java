package com.zabivonikl.vaadindemo.data.service;

import com.vaadin.flow.data.provider.AbstractBackEndDataProvider;
import com.vaadin.flow.data.provider.Query;
import com.zabivonikl.vaadindemo.data.entity.AbstractEntity;
import com.zabivonikl.vaadindemo.data.service.repositories.AbstractRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.UUID;
import java.util.stream.Stream;

public abstract class AbstractService<T extends AbstractEntity> extends AbstractBackEndDataProvider<T, String> {
    private final AbstractRepository<T> repository;

    protected AbstractService(AbstractRepository<T> repository) {
        this.repository = repository;
    }

    public T findById(UUID id) {
        return repository.findById(id).orElse(null);
    }

    public boolean contains(T entity) {
        return entity != null && entity.getId() != null && findById(entity.getId()) != null;
    }

    public T update(T entity) {
        return repository.save(entity);
    }

    public void delete(T entity) {
        repository.delete(entity);
    }

    public Page<T> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Page<T> findAll(String filter, Pageable pageable) {
        return repository.find(filter, pageable);
    }

    @Override
    protected Stream<T> fetchFromBackEnd(Query<T, String> query) {
        var page = PageRequest.of(query.getPage(), query.getPageSize());
        if (query.getFilter().isPresent())
            return findAll(query.getFilter().get(), page).stream();

        return findAll(page).stream();
    }

    @Override
    protected int sizeInBackEnd(Query<T, String> query) {
        return (int) fetchFromBackEnd(query).count();
    }
}
