# SalesApp - Backend Assessment

A Spring Boot application for processing and analyzing sales data.

## Prerequisites

- Java 17
- Spring Boot 3.4+
- Supabase with PostgreSQL

## Setup

1. Clone the repository
2. Configure database connection in `application-dev.yml`
3. Place your sales data CSV file in `src/main/resources` (default filename: `sales_data.csv`)
4. Run the application:
   ```bash
   gradlew.bat bootRun

 ## API endpoints

 1. POST /api/data/refresh
 2. GET /api/analysis/revenue?startDate=2023-01-01&endDate=2023-12-31
 3. GET /api/analysis/revenue/products?startDate=2023-01-01&endDate=2023-12-31
 4. GET /api/analysis/revenue/categories?startDate=2023-01-01&endDate=2023-12-31
 5. GET /api/analysis/revenue/regions?startDate=2023-01-01&endDate=2023-12-31
