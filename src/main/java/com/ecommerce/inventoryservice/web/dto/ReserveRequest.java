package com.ecommerce.inventoryservice.web.dto;

import jakarta.validation.constraints.Positive;

/** Request body for a stock reservation. */
public record ReserveRequest(@Positive int quantity) {}
