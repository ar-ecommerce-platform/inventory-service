package com.ecommerce.inventoryservice.web;

import com.ecommerce.inventoryservice.service.InsufficientStockException;
import com.ecommerce.inventoryservice.service.InventoryItemNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/** Translates domain errors into {@link ApiError} responses. */
@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(InventoryItemNotFoundException.class)
  public ResponseEntity<ApiError> handleNotFound(InventoryItemNotFoundException ex) {
    return build(HttpStatus.NOT_FOUND, "INVENTORY_NOT_FOUND", ex.getMessage());
  }

  @ExceptionHandler(InsufficientStockException.class)
  public ResponseEntity<ApiError> handleInsufficient(InsufficientStockException ex) {
    return build(HttpStatus.CONFLICT, "INSUFFICIENT_STOCK", ex.getMessage());
  }

  private static ResponseEntity<ApiError> build(HttpStatus status, String code, String message) {
    return ResponseEntity.status(status).body(ApiError.of(status.value(), code, message));
  }
}
