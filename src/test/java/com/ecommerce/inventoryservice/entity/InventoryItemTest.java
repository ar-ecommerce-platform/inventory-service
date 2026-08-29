package com.ecommerce.inventoryservice.entity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.ecommerce.inventoryservice.service.InsufficientStockException;
import org.junit.jupiter.api.Test;

class InventoryItemTest {

  @Test
  void reserve_decrementsAvailableStock() {
    InventoryItem item = new InventoryItem(1L, 10);

    item.reserve(4);

    assertThat(item.getQuantityAvailable()).isEqualTo(6);
  }

  @Test
  void reserve_rejectsMoreThanAvailable() {
    InventoryItem item = new InventoryItem(1L, 2);

    assertThatThrownBy(() -> item.reserve(5)).isInstanceOf(InsufficientStockException.class);
    assertThat(item.getQuantityAvailable()).isEqualTo(2);
  }
}
