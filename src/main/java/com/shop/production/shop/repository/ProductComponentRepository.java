package com.shop.production.shop.repository;

import com.shop.production.shop.entity.ProductComponent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface ProductComponentRepository extends JpaRepository<ProductComponent, UUID> {
}
