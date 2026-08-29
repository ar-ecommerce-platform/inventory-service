package com.ecommerce.inventoryservice.service;

/** Raised when a reservation asks for more units than are available. */
public class InsufficientStockException extends RuntimeException {

  public InsufficientStockException(Long productId, int requested, int available) {
    super(
        "Insufficient stock for product "
            + productId
            + ": requested "
            + requested
            + ", available "
            + available);
  }
}
