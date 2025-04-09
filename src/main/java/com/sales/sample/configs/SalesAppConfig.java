package com.sales.sample.configs;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "salesapp")
public class SalesAppConfig {
	private CsvConfig csv;

	public CsvConfig getCsv() {
		return csv;
	}

	public void setCsv(CsvConfig csv) {
		this.csv = csv;
	}

	public static class CsvConfig {
		private String filePath;

		public String getFilePath() {
			return filePath;
		}

		public void setFilePath(String filePath) {
			this.filePath = filePath;
		}
	}
}