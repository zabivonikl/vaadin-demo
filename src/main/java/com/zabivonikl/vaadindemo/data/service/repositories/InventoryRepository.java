package com.zabivonikl.vaadindemo.data.service.repositories;

import com.zabivonikl.vaadindemo.data.entity.InventoryItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface InventoryRepository extends AbstractRepository<InventoryItem> {
    @Override
    @Query(
            "select item from InventoryItem item " +
                    "where lower(item.title) like lower(concat('%', :searchTerm, '%')) " +
                    "or lower(item.vendor) like lower(concat('%', :searchTerm, '%'))"
    )
    Page<InventoryItem> find(@Param("searchTerm") String filter, Pageable pageable);
}
