package com.zabivonikl.vaadindemo.data.service.dataproviders;

import com.vaadin.flow.data.provider.AbstractBackEndDataProvider;
import com.vaadin.flow.data.provider.Query;
import com.zabivonikl.vaadindemo.data.entity.AbstractEntity;
import com.zabivonikl.vaadindemo.data.service.AbstractService;
import org.springframework.data.domain.PageRequest;

import java.util.stream.Stream;

public abstract class AbstractDataProvider<T extends AbstractEntity> extends AbstractBackEndDataProvider<T, String> {
    private final AbstractService<T> service;

    public AbstractDataProvider(AbstractService<T> service) {
        this.service = service;
    }

    @Override
    protected Stream<T> fetchFromBackEnd(Query<T, String> query) {
        var page = PageRequest.of(query.getPage(), query.getPageSize());
        if (query.getFilter().isPresent())
            return service.findAll(query.getFilter().get(), page).stream();

        return service.findAll(page).stream();
    }

    @Override
    protected int sizeInBackEnd(Query<T, String> query) {
        return (int) fetchFromBackEnd(query).count();
    }
}
