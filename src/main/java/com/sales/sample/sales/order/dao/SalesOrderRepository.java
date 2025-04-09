package com.sales.sample.sales.order.dao;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sales.sample.entities.OrderEntity;

@Repository
public interface SalesOrderRepository extends JpaRepository<OrderEntity, String> {

	@Query("SELECT o FROM OrderEntity o WHERE o.dateOfSale BETWEEN :startDate AND :endDate")
	List<OrderEntity> findByDateRange(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
}
