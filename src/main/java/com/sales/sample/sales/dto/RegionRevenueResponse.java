package com.sales.sample.sales.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class RegionRevenueResponse {
    private String region;
    private BigDecimal revenue;
}
