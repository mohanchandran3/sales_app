package com.sales.sample.sales.service.impl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sales.sample.entities.OrderEntity;
import com.sales.sample.sales.dto.CategoryRevenueResponse;
import com.sales.sample.sales.dto.ProductRevenueResponse;
import com.sales.sample.sales.dto.RegionRevenueResponse;
import com.sales.sample.sales.dto.RevenueResponse;
import com.sales.sample.sales.order.dao.SalesOrderRepository;
import com.sales.sample.sales.service.SalesAnalysisService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class SalesAnalysisServiceImpl implements SalesAnalysisService {

	@Autowired
	private SalesOrderRepository orderRepository;

	@Override
	public RevenueResponse calculateRevenue(LocalDate startDate, LocalDate endDate) {
		List<OrderEntity> orders = orderRepository.findByDateRange(startDate, endDate);

		BigDecimal totalRevenue = orders.stream().flatMap(order -> order.getItems().stream()).map(item -> {
			BigDecimal itemPrice = item.getProduct().getUnitPrice();
			BigDecimal discountedPrice = itemPrice.subtract(itemPrice.multiply(item.getDiscount()));
			return discountedPrice.multiply(BigDecimal.valueOf(item.getQuantity()));
		}).reduce(BigDecimal.ZERO, BigDecimal::add);

		return new RevenueResponse(startDate, endDate, totalRevenue);
	}

	@Override
	public List<ProductRevenueResponse> calculateRevenueByProduct(LocalDate startDate, LocalDate endDate) {
		List<OrderEntity> orders = orderRepository.findByDateRange(startDate, endDate);

		return orders.stream().flatMap(order -> order.getItems().stream()).collect(Collectors
				.groupingBy(item -> item.getProduct().getName(), Collectors.reducing(BigDecimal.ZERO, item -> {
					BigDecimal itemPrice = item.getProduct().getUnitPrice();
					BigDecimal discountedPrice = itemPrice.subtract(itemPrice.multiply(item.getDiscount()));
					return discountedPrice.multiply(BigDecimal.valueOf(item.getQuantity()));
				}, BigDecimal::add))).entrySet().stream()
				.map(entry -> new ProductRevenueResponse(entry.getKey(), entry.getValue()))
				.collect(Collectors.toList());
	}

	@Override
	public List<CategoryRevenueResponse> calculateRevenueByCategory(LocalDate startDate, LocalDate endDate) {
		List<OrderEntity> orders = orderRepository.findByDateRange(startDate, endDate);

		return orders.stream().flatMap(order -> order.getItems().stream()).collect(Collectors
				.groupingBy(item -> item.getProduct().getCategory(), Collectors.reducing(BigDecimal.ZERO, item -> {
					BigDecimal itemPrice = item.getProduct().getUnitPrice();
					BigDecimal discountedPrice = itemPrice.subtract(itemPrice.multiply(item.getDiscount()));
					return discountedPrice.multiply(BigDecimal.valueOf(item.getQuantity()));
				}, BigDecimal::add))).entrySet().stream()
				.map(entry -> new CategoryRevenueResponse(entry.getKey(), entry.getValue()))
				.collect(Collectors.toList());
	}

	@Override
	public List<RegionRevenueResponse> calculateRevenueByRegion(LocalDate startDate, LocalDate endDate) {
		List<OrderEntity> orders = orderRepository.findByDateRange(startDate, endDate);

		return orders.stream().collect(Collectors.groupingBy(OrderEntity::getRegion,
				Collectors.reducing(BigDecimal.ZERO, order -> order.getItems().stream().map(item -> {
					BigDecimal itemPrice = item.getProduct().getUnitPrice();
					BigDecimal discountedPrice = itemPrice.subtract(itemPrice.multiply(item.getDiscount()));
					return discountedPrice.multiply(BigDecimal.valueOf(item.getQuantity()));
				}).reduce(BigDecimal.ZERO, BigDecimal::add), BigDecimal::add))).entrySet().stream()
				.map(entry -> new RegionRevenueResponse(entry.getKey(), entry.getValue())).collect(Collectors.toList());
	}
}
