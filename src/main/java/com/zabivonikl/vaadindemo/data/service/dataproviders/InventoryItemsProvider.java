package com.zabivonikl.vaadindemo.data.service.dataproviders;

import com.vaadin.flow.data.provider.Query;
import com.zabivonikl.vaadindemo.data.entity.InventoryItem;
import com.zabivonikl.vaadindemo.data.service.AbstractService;

import java.util.stream.Stream;

public class InventoryItemsProvider extends AbstractDataProvider<InventoryItem> {
    public InventoryItemsProvider(AbstractService<InventoryItem> service) {
        super(service);
    }

    @Override
    protected Stream<InventoryItem> fetchFromBackEnd(Query<InventoryItem, String> query) {
        return database.stream().filter(item -> item.getTitle().contains(query.getFilter().get()) ||
                item.getVendor().contains(query.getFilter().get()));
    }
}
