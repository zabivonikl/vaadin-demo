package com.zabivonikl.vaadindemo.data.service.dataproviders;

import com.vaadin.flow.data.provider.AbstractBackEndDataProvider;
import com.vaadin.flow.data.provider.Query;
import com.zabivonikl.vaadindemo.data.entity.AbstractEntity;
import com.zabivonikl.vaadindemo.data.service.AbstractService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public abstract class AbstractDataProvider<T extends AbstractEntity> extends AbstractBackEndDataProvider<T, String> {
    protected final List<T> database;

    public AbstractDataProvider(AbstractService<T> service) {
        this.database = new ArrayList<>(service.findAll(null));
    }

    @Override
    protected Stream<T> fetchFromBackEnd(Query<T, String> query) {
        var stream = database.stream();
        if (query.getFilter().isPresent())
            stream = stream.filter(e -> e.matches(query.getFilter().get()));

        return stream.skip(query.getOffset()).limit(query.getLimit());
    }

    @Override
    protected int sizeInBackEnd(Query<T, String> query) {
        return (int) fetchFromBackEnd(query).count();
    }
}
