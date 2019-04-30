package com.shop.production.shop.repository;

import com.shop.production.shop.entity.CustomerInfo;
import org.hibernate.metamodel.model.convert.spi.JpaAttributeConverter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CustomerInfoRepository extends JpaRepository<CustomerInfo, UUID> {
}
