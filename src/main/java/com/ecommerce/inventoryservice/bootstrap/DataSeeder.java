package com.ecommerce.inventoryservice.bootstrap;

import com.ecommerce.inventoryservice.entity.InventoryItem;
import com.ecommerce.inventoryservice.repository.InventoryItemRepository;
import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/** Seeds stock levels matching product-service's demo catalog (ids 1..5). */
@Component
public class DataSeeder implements CommandLineRunner {

  private final InventoryItemRepository repository;

  public DataSeeder(InventoryItemRepository repository) {
    this.repository = repository;
  }

  @Override
  public void run(String... args) {
    if (repository.count() > 0) {
      return;
    }
    repository.saveAll(
        List.of(
            new InventoryItem(1L, 50),
            new InventoryItem(2L, 30),
            new InventoryItem(3L, 200),
            new InventoryItem(4L, 40),
            // Deliberately low so the "out of stock" path is easy to demo.
            new InventoryItem(5L, 3)));
  }
}
