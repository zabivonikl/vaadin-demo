package com.zabivonikl.vaadindemo.data.service.dataproviders;

import com.zabivonikl.vaadindemo.data.entity.InventoryItem;
import com.zabivonikl.vaadindemo.data.service.AbstractService;

public class InventoryItemsProvider extends AbstractDataProvider<InventoryItem> {
    public InventoryItemsProvider(AbstractService<InventoryItem> service) {
        super(service);
    }
}
