package com.sales.sample.sales.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sales.sample.entities.CustomerEntity;
import com.sales.sample.entities.OrderEntity;
import com.sales.sample.entities.OrderItemEntity;
import com.sales.sample.entities.ProductEntity;
import com.sales.sample.sales.customer.dao.SalesCustomerRepository;
import com.sales.sample.sales.order.dao.SalesOrderRepository;
import com.sales.sample.sales.product.dao.SalesProductRepository;
import com.sales.sample.sales.service.DataLoadingService;

import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class DataLoadingServiceImpl implements DataLoadingService {
	@Autowired
	private SalesCustomerRepository customerRepository;
	@Autowired
	private SalesProductRepository productRepository;
	@Autowired
	private SalesOrderRepository orderRepository;

	@Value("${app.data.file-path}")
	private Resource dataFile;

	@Override
	@Transactional
	public void loadData() throws IOException {
		log.info("Starting data loading process");

		try (InputStreamReader reader = new InputStreamReader(dataFile.getInputStream())) {

			CSVFormat format = CSVFormat.Builder.create().setHeader().setSkipHeaderRecord(true)
					.setIgnoreHeaderCase(true).setTrim(true).build();

			try (CSVParser csvParser = CSVParser.parse(reader, format)) {

				Map<String, CustomerEntity> customerCache = new HashMap<>();
				Map<String, ProductEntity> productCache = new HashMap<>();

				for (CSVRecord record : csvParser) {
					String customerId = record.get("Customer ID");
					String productId = record.get("Product ID");
					String orderId = record.get("Order ID");

					CustomerEntity customer = customerCache.computeIfAbsent(customerId, id -> {
						CustomerEntity c = new CustomerEntity();
						c.setId(id);
						c.setName(record.get("Customer Name"));
						c.setEmail(record.get("Customer Email"));
						c.setAddress(record.get("Customer Address"));
						return customerRepository.save(c);
					});

					ProductEntity product = productCache.computeIfAbsent(productId, id -> {
						ProductEntity p = new ProductEntity();
						p.setId(id);
						p.setName(record.get("Product Name"));
						p.setCategory(record.get("Category"));
						p.setUnitPrice(new BigDecimal(record.get("Unit Price")));
						return productRepository.save(p);
					});

					if (!orderRepository.existsById(orderId)) {
						OrderEntity order = new OrderEntity();
						order.setId(orderId);
						order.setCustomer(customer);
						order.setRegion(record.get("Region"));
						order.setDateOfSale(
								LocalDate.parse(record.get("Date of Sale"), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
						order.setPaymentMethod(record.get("Payment Method"));
						order.setShippingCost(new BigDecimal(record.get("Shipping Cost")));

						OrderItemEntity item = new OrderItemEntity();
						item.setOrder(order);
						item.setProduct(product);
						item.setQuantity(Integer.parseInt(record.get("Quantity Sold")));
						item.setDiscount(new BigDecimal(record.get("Discount")));

						order.setItems(List.of(item));
						orderRepository.save(order);
					}
				}

				log.info("Data loading completed successfully");
			}
		} catch (Exception e) {
			log.error("Error during data loading", e);
			throw e;
		}
	}

	@Override
	@Scheduled(cron = "${app.data.refresh-cron}")
	public void scheduledDataRefresh() {
		try {
			log.info("Starting scheduled data refresh");
			loadData();
		} catch (IOException e) {
			log.error("Failed to refresh data", e);
		}
	}
}