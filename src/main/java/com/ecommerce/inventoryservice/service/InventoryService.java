package com.ecommerce.inventoryservice.service;

import com.ecommerce.inventoryservice.entity.InventoryItem;
import com.ecommerce.inventoryservice.repository.InventoryItemRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** Stock queries and reservations. */
@Service
public class InventoryService {

  private final InventoryItemRepository repository;

  public InventoryService(InventoryItemRepository repository) {
    this.repository = repository;
  }

  @Transactional(readOnly = true)
  public InventoryItem get(Long productId) {
    return repository
        .findById(productId)
        .orElseThrow(() -> new InventoryItemNotFoundException(productId));
  }

  /**
   * Reserves {@code quantity} units of a product.
   *
   * @throws InventoryItemNotFoundException if the product has no stock record
   * @throws InsufficientStockException if not enough units are available
   */
  @Transactional
  public InventoryItem reserve(Long productId, int quantity) {
    InventoryItem item = get(productId);
    item.reserve(quantity);
    return repository.save(item);
  }
}
