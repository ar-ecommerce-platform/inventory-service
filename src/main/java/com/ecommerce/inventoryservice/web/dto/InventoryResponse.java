package com.ecommerce.inventoryservice.web.dto;

import com.ecommerce.inventoryservice.entity.InventoryItem;

/** Stock view for a product. */
public record InventoryResponse(Long productId, int quantityAvailable) {

  public static InventoryResponse from(InventoryItem item) {
    return new InventoryResponse(item.getProductId(), item.getQuantityAvailable());
  }
}
