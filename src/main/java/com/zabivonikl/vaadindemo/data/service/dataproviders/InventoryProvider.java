package com.zabivonikl.vaadindemo.data.service.dataproviders;

import com.zabivonikl.vaadindemo.data.entity.InventoryItem;
import com.zabivonikl.vaadindemo.data.service.AbstractService;

public class InventoryProvider extends AbstractDataProvider<InventoryItem> {
    public InventoryProvider(AbstractService<InventoryItem> service) {
        super(service);
    }
}
