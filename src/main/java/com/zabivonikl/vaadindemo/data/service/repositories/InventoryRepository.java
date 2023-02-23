package com.zabivonikl.vaadindemo.data.service.repositories;

import com.zabivonikl.vaadindemo.data.entity.InventoryItem;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface InventoryRepository extends AbstractRepository<InventoryItem> {
    @Query(
            "select item from InventoryItem item " +
                    "where lower(item.title) like lower(concat('%', :searchTerm, '%')) " +
                    "or lower(item.vendor) like lower(concat('%', :searchTerm, '%'))"
    )
    List<InventoryItem> search(@Param("searchTerm") String searchTerm);
}
