package com.ecommerce.inventoryservice.entity;

import com.ecommerce.inventoryservice.service.InsufficientStockException;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Version;

/** Stock level for one product, keyed by the product id owned by product-service. */
@Entity
@Table(name = "inventory_items")
public class InventoryItem {

  @Id private Long productId;

  @Column(nullable = false)
  private int quantityAvailable;

  @Version private long version;

  protected InventoryItem() {
    // for JPA
  }

  public InventoryItem(Long productId, int quantityAvailable) {
    this.productId = productId;
    this.quantityAvailable = quantityAvailable;
  }

  /**
   * Removes {@code quantity} units from stock.
   *
   * @throws InsufficientStockException if {@code quantity} exceeds availability
   */
  public void reserve(int quantity) {
    if (quantity > quantityAvailable) {
      throw new InsufficientStockException(productId, quantity, quantityAvailable);
    }
    quantityAvailable -= quantity;
  }

  public Long getProductId() {
    return productId;
  }

  public int getQuantityAvailable() {
    return quantityAvailable;
  }
}
