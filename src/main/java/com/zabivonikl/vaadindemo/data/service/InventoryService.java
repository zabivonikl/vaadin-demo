package com.zabivonikl.vaadindemo.data.service;

import com.zabivonikl.vaadindemo.data.entity.InventoryItem;
import com.zabivonikl.vaadindemo.data.service.repositories.AbstractRepository;
import org.springframework.stereotype.Service;

@Service
public class InventoryService extends AbstractService<InventoryItem> {
    protected InventoryService(AbstractRepository<InventoryItem> repository) {
        super(repository);
    }
}
