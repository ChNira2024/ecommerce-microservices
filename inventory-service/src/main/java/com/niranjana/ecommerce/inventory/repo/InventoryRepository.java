package com.niranjana.ecommerce.inventory.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.niranjana.ecommerce.inventory.entity.InventoryProduct;

@Repository
public interface InventoryRepository extends JpaRepository<InventoryProduct, Long> {
}