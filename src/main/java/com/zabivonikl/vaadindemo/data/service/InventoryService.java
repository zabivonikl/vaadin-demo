package com.zabivonikl.vaadindemo.data.service;

import com.zabivonikl.vaadindemo.data.entity.InventoryItem;
import com.zabivonikl.vaadindemo.data.service.repositories.InventoryRepository;
import org.springframework.stereotype.Service;

@Service
public class InventoryService extends AbstractService<InventoryItem> {
    protected InventoryService(InventoryRepository repository) {
        super(repository);
    }
}
