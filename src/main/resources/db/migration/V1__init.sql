-- inventory-service schema. Matches com.ecommerce.inventoryservice.entity.InventoryItem.
CREATE TABLE inventory_items (
    product_id         BIGINT NOT NULL PRIMARY KEY,
    quantity_available INTEGER NOT NULL,
    version            BIGINT NOT NULL DEFAULT 0
);
