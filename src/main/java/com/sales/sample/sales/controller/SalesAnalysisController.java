package com.sales.sample.sales.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sales.sample.sales.dto.CategoryRevenueResponse;
import com.sales.sample.sales.dto.ProductRevenueResponse;
import com.sales.sample.sales.dto.RegionRevenueResponse;
import com.sales.sample.sales.dto.RevenueResponse;
import com.sales.sample.sales.service.SalesAnalysisService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/analysis")
@RequiredArgsConstructor
@Tag(name = "Sales Analysis", description = "Endpoints for sales data analysis")
public class SalesAnalysisController {
	@Autowired
	private SalesAnalysisService salesAnalysisService;

	@GetMapping("/revenue")
	@Operation(summary = "Calculate total revenue", description = "Calculates total revenue for a given date range")
	@ApiResponse(responseCode = "200", description = "Revenue calculation successful")
	public ResponseEntity<RevenueResponse> getTotalRevenue(
			@Parameter(description = "Start date (yyyy-MM-dd)", example = "2023-01-01") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
			@Parameter(description = "End date (yyyy-MM-dd)", example = "2023-12-31") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

		return ResponseEntity.ok(salesAnalysisService.calculateRevenue(startDate, endDate));
	}

	@GetMapping("/revenue/products")
	@Operation(summary = "Calculate revenue by product", description = "Calculates revenue grouped by product for a given date range")
	@ApiResponse(responseCode = "200", description = "Revenue by product calculation successful")
	public ResponseEntity<List<ProductRevenueResponse>> getRevenueByProduct(
			@Parameter(description = "Start date (yyyy-MM-dd)", example = "2023-01-01") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
			@Parameter(description = "End date (yyyy-MM-dd)", example = "2023-12-31") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

		return ResponseEntity.ok(salesAnalysisService.calculateRevenueByProduct(startDate, endDate));
	}

	@GetMapping("/revenue/categories")
	@Operation(summary = "Calculate revenue by category", description = "Calculates revenue grouped by category for a given date range")
	@ApiResponse(responseCode = "200", description = "Revenue by category calculation successful")
	public ResponseEntity<List<CategoryRevenueResponse>> getRevenueByCategory(
			@Parameter(description = "Start date (yyyy-MM-dd)", example = "2023-01-01") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
			@Parameter(description = "End date (yyyy-MM-dd)", example = "2023-12-31") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

		return ResponseEntity.ok(salesAnalysisService.calculateRevenueByCategory(startDate, endDate));
	}

	@GetMapping("/revenue/regions")
	@Operation(summary = "Calculate revenue by region", description = "Calculates revenue grouped by region for a given date range")
	@ApiResponse(responseCode = "200", description = "Revenue by region calculation successful")
	public ResponseEntity<List<RegionRevenueResponse>> getRevenueByRegion(
			@Parameter(description = "Start date (yyyy-MM-dd)", example = "2023-01-01") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
			@Parameter(description = "End date (yyyy-MM-dd)", example = "2023-12-31") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

		return ResponseEntity.ok(salesAnalysisService.calculateRevenueByRegion(startDate, endDate));
	}
}