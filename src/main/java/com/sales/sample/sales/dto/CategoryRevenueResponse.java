package com.sales.sample.sales.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class CategoryRevenueResponse {
    private String category;
    private BigDecimal revenue;
}