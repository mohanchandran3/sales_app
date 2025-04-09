package com.sales.sample.sales.service;

import java.time.LocalDate;
import java.util.List;

import com.sales.sample.sales.dto.CategoryRevenueResponse;
import com.sales.sample.sales.dto.ProductRevenueResponse;
import com.sales.sample.sales.dto.RegionRevenueResponse;
import com.sales.sample.sales.dto.RevenueResponse;

public interface SalesAnalysisService {
	RevenueResponse calculateRevenue(LocalDate startDate, LocalDate endDate);

	List<ProductRevenueResponse> calculateRevenueByProduct(LocalDate startDate, LocalDate endDate);

	List<CategoryRevenueResponse> calculateRevenueByCategory(LocalDate startDate, LocalDate endDate);

	List<RegionRevenueResponse> calculateRevenueByRegion(LocalDate startDate, LocalDate endDate);
}
