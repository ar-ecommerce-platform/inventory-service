package com.ecommerce.inventoryservice.repository;

import com.ecommerce.inventoryservice.entity.InventoryItem;
import org.springframework.data.jpa.repository.JpaRepository;

/** Data access for {@link InventoryItem}. */
public interface InventoryItemRepository extends JpaRepository<InventoryItem, Long> {}
