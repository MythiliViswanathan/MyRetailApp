package com.myRetail.retail.service.util;

import java.util.Properties;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.myRetail.retail.api.request.ProductMetaDataRequest;
import com.myRetail.retail.api.response.ProductMetaDataResponse;
import com.myRetail.retail.service.util.HttpHelper;

@Component
public class ProductInfoUtil {

	private static final Logger LOGGER = LoggerFactory.getLogger(ProductInfoUtil.class);
	private String productMetaDataGetUrl;
	private String productMetaDataCreateUrl;
	private String productMetaDataUpdateUrl;
	private Properties properties;

	@Autowired
	private HttpHelper httphelper;

	@PostConstruct
	public void init() {
		try {
			properties = new Properties();
			properties.load(this.getClass().getClassLoader().getResourceAsStream("productConfig.properties"));
			productMetaDataGetUrl = getValue("productMetaDataGetUrl");
			productMetaDataCreateUrl = getValue("productMetaDataCreateUrl");
			productMetaDataUpdateUrl = getValue("productMetaDataUpdateUrl");
		} catch (Exception ex) {
			LOGGER.error("Unable to intialize ProductInfoUtil " + ex.getMessage());
		}
	}

	public String getProductName(Long productId) throws Exception {

		LOGGER.info("Fetching product name from ProductInfoService");
		String name = "";
		try {
			String url = String.format(productMetaDataGetUrl, productId);
			String result = httphelper.Get(url);
			Gson g = new Gson();
			ProductMetaDataResponse response = g.fromJson(result, ProductMetaDataResponse.class);
			name = response.getName();
		} catch (Exception ex) {
			LOGGER.error("Unable to fetch product name " + ex.getMessage());
		}
		return name;

	}

	public void addProductName(Long productId, String productName) throws Exception {
		LOGGER.info("Adding product name for product {}", productId);
		try {
			ProductMetaDataRequest request = new ProductMetaDataRequest();
			request.setId(productId);
			request.setName(productName);
			Gson g = new Gson();
			String requestString = g.toJson(request);
			httphelper.Post(productMetaDataCreateUrl, requestString);

		} catch (Exception ex) {
			LOGGER.error("Unable to add product name " + ex.getMessage());
		}

	}

	public void updateProductName(Long productId, String productName) throws Exception {
		LOGGER.info("updating product name {} for id {}", productName, productId);
		try {
			ProductMetaDataRequest request = new ProductMetaDataRequest();
			String url = String.format(productMetaDataUpdateUrl, productId);
			request.setId(productId);
			request.setName(productName);
			Gson g = new Gson();
			String requestString = g.toJson(request);
			httphelper.Put(url, requestString);

		} catch (Exception ex) {
			LOGGER.error("Unable to update product name " + ex.getMessage());
		}

	}

	private String getValue(String key) {
		return properties.getProperty(key);
	}

}
