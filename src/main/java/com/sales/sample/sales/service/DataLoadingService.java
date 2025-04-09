package com.sales.sample.sales.service;

import java.io.IOException;

public interface DataLoadingService {
	void loadData() throws IOException;

	void scheduledDataRefresh();
}
