package com.sales.sample.sales.product.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sales.sample.entities.ProductEntity;

@Repository
public interface SalesProductRepository extends JpaRepository<ProductEntity, String> {

}
