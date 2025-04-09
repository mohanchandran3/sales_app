package com.sales.sample.sales.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sales.sample.sales.service.DataLoadingService;

@RestController
@RequestMapping("/api/data")
@RequiredArgsConstructor
@Tag(name = "Data Loading", description = "Endpoints for loading and refreshing sales data")
public class DataLoadingController {

	@Autowired
	private DataLoadingService dataLoadingService;

	@PostMapping("/refresh")
	@Operation(summary = "Trigger manual data refresh", description = "Loads data from CSV file into the database")
	@ApiResponse(responseCode = "200", description = "Data refresh initiated successfully")
	public ResponseEntity<String> refreshData() {
		try {
			dataLoadingService.loadData();
			return ResponseEntity.ok("Data refresh completed successfully");
		} catch (Exception e) {
			return ResponseEntity.internalServerError().body("Failed to refresh data: " + e.getMessage());
		}
	}
}
