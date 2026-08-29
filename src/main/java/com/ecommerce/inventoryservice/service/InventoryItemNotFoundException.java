package com.ecommerce.inventoryservice.service;

/** Raised when no stock record exists for a product id. */
public class InventoryItemNotFoundException extends RuntimeException {

  public InventoryItemNotFoundException(Long productId) {
    super("No inventory record for product " + productId);
  }
}
