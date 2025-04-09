package com.sales.sample.sales.customer.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sales.sample.entities.CustomerEntity;

@Repository
public interface SalesCustomerRepository extends JpaRepository<CustomerEntity, String> {

}
