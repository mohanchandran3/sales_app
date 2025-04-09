package com.sales.sample.sales.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor

public class ProductRevenueResponse {
    private String productName;
    private BigDecimal revenue;
}
