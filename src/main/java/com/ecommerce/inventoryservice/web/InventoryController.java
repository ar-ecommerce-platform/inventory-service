package com.ecommerce.inventoryservice.web;

import com.ecommerce.inventoryservice.service.InventoryService;
import com.ecommerce.inventoryservice.web.dto.InventoryResponse;
import com.ecommerce.inventoryservice.web.dto.ReserveRequest;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** Stock lookup and reservation endpoints. */
@RestController
@RequestMapping("/inventory")
public class InventoryController {

  private final InventoryService service;

  public InventoryController(InventoryService service) {
    this.service = service;
  }

  @GetMapping("/{productId}")
  public InventoryResponse get(@PathVariable Long productId) {
    return InventoryResponse.from(service.get(productId));
  }

  /** Called by order-service only; the gateway does not expose it, so it is not in the docs. */
  @Hidden
  @PostMapping("/{productId}/reserve")
  public InventoryResponse reserve(
      @PathVariable Long productId, @Valid @RequestBody ReserveRequest request) {
    return InventoryResponse.from(service.reserve(productId, request.quantity()));
  }
}
