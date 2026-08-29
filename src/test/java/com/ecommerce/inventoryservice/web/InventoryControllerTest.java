package com.ecommerce.inventoryservice.web;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.ecommerce.inventoryservice.entity.InventoryItem;
import com.ecommerce.inventoryservice.service.InsufficientStockException;
import com.ecommerce.inventoryservice.service.InventoryItemNotFoundException;
import com.ecommerce.inventoryservice.service.InventoryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(InventoryController.class)
class InventoryControllerTest {

  @Autowired private MockMvc mvc;

  @MockitoBean private InventoryService service;

  @Test
  void get_returnsStock() throws Exception {
    when(service.get(1L)).thenReturn(new InventoryItem(1L, 42));

    mvc.perform(get("/inventory/1"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.productId").value(1))
        .andExpect(jsonPath("$.quantityAvailable").value(42));
  }

  @Test
  void reserve_success_returnsRemainingStock() throws Exception {
    when(service.reserve(eq(1L), anyInt())).thenReturn(new InventoryItem(1L, 8));

    mvc.perform(
            post("/inventory/1/reserve")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"quantity\":2}"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.quantityAvailable").value(8));
  }

  @Test
  void reserve_insufficient_returns409() throws Exception {
    when(service.reserve(eq(5L), anyInt())).thenThrow(new InsufficientStockException(5L, 999, 3));

    mvc.perform(
            post("/inventory/5/reserve")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"quantity\":999}"))
        .andExpect(status().isConflict())
        .andExpect(jsonPath("$.code").value("INSUFFICIENT_STOCK"));
  }

  @Test
  void get_unknownProduct_returns404() throws Exception {
    when(service.get(77L)).thenThrow(new InventoryItemNotFoundException(77L));

    mvc.perform(get("/inventory/77"))
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$.code").value("INVENTORY_NOT_FOUND"));
  }
}
