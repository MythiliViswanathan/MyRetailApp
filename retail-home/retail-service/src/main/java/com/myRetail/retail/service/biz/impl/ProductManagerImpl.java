package com.myRetail.retail.service.biz.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myRetail.retail.api.request.ProductRequest;
import com.myRetail.retail.api.response.PriceResponse;
import com.myRetail.retail.api.response.ProductResponse;
import com.myRetail.retail.datastore.api.ProductRepository;
import com.myRetail.retail.datastore.model.ProductPriceDO;
import com.myRetail.retail.service.biz.api.ProductsManager;
import com.myRetail.retail.service.util.ProductInfoUtil;

/**
 * @author mythili
 *
 */
@Service
public class ProductManagerImpl implements ProductsManager {

	private static final Logger LOGGER = LoggerFactory.getLogger(ProductManagerImpl.class);

	@Autowired
	ProductRepository repo;

	@Autowired
	ProductInfoUtil productInfoUtil;

	public void create(ProductRequest productRequest) throws Exception {
		LOGGER.info("Create product request with details : ", productRequest);
		try {

			if (repo.get(productRequest.getId()) != null) {
				throw new Exception("Entry already exist for the given product id");
			}

			ProductPriceDO entity = convertRequestToEntity(productRequest);

			// create entry in the repo
			if (!repo.add(entity)) {
				throw new Exception("Unable to create the details of the given product");
			}

			// Add the product name to service
			productInfoUtil.addProductName(productRequest.getId(), productRequest.getName());

		} catch (Exception ex) {
			LOGGER.error("Unable to create prdouct with details", ex.toString());
			throw ex;
		}
	}

	public void update(Long productId, ProductRequest productRequest) throws Exception {
		LOGGER.info("Update product request with details : ", productRequest);
		try {

			if (repo.get(productId) == null) {
				throw new Exception("Info for the given product id doesnt exist");
			}

			ProductPriceDO entity = convertRequestToEntity(productRequest);

			// update entry in the repo
			if (!repo.update(productId, entity)) {
				throw new Exception("Unable to update the details of the given product");
			}

			// Update the product name to service
			productInfoUtil.updateProductName(productId, productRequest.getName());

		} catch (Exception ex) {
			LOGGER.error("Unable to update the details of the given product:", ex.toString());
			throw ex;
		}

	}

	public ProductResponse get(Long productId) throws Exception {
		LOGGER.info("Get product request with the id:", productId);
		try {

			// Fetch the product details from repo
			ProductPriceDO entity = repo.get(productId);

			if (entity == null) {
				throw new Exception("product-info for the given product id doesnt exist");
			}
			ProductResponse response = convertEntityToReponse(entity);

			// Fetch the product name from service
			String productName = productInfoUtil.getProductName(productId);
			response.setName(productName);
			return response;
		} catch (Exception ex) {
			LOGGER.error("Unable to fetch the details of the given product:", ex.toString());
			throw ex;

		}

	}

	private ProductResponse convertEntityToReponse(ProductPriceDO entity) {
		ProductResponse response = new ProductResponse();
		PriceResponse price = new PriceResponse();
		price.setCode(entity.getCode());
		price.setValue(entity.getValue());
		response.setId(entity.getProductId());
		response.setCurrent_price(price);
		return response;
	}

	private ProductPriceDO convertRequestToEntity(ProductRequest request) {
		ProductPriceDO entity = new ProductPriceDO();
		entity.setProductId(request.getId());
		entity.setValue(request.getCurrent_price().getValue());
		entity.setCode(request.getCurrent_price().getCode());
		return entity;
	}

}
