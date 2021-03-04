package com.myRetail.retail.service.biz.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myRetail.retail.api.request.ProductMetaDataRequest;
import com.myRetail.retail.api.response.ProductMetaDataResponse;
import com.myRetail.retail.datastore.api.ProductMetaDataRepository;
import com.myRetail.retail.datastore.model.ProductMetaDataDO;
import com.myRetail.retail.service.biz.api.ProductsMetaDataManager;

/**
 * @author mythili
 *
 */
@Service
public class ProductMetaDataManagerImpl implements ProductsMetaDataManager {

	private static final Logger LOGGER = LoggerFactory.getLogger(ProductMetaDataManagerImpl.class);

	@Autowired
	ProductMetaDataRepository repo;

	public ProductMetaDataResponse get(Long productId) throws Exception {
		LOGGER.info("Fetch product request with id : ", productId);
		try {
			// Fetch the product details from repo
			ProductMetaDataDO entity = repo.get(productId);
			
			if(entity==null) {
				throw new Exception("metadata with the given product id doesnt exist");
			}
			return convertEntityToReponse(entity);
		} catch (Exception ex) {
			LOGGER.error("Unable to fetch the details of the given product:", ex.toString());
			throw ex;

		}
	}

	public void create(ProductMetaDataRequest productRequest) throws Exception {
		LOGGER.info("Create product request with details : ", productRequest);
		try {
			if(repo.get(productRequest.getId())!=null) {
				throw new Exception("metadata alreadyexist for the given product id");
			}

			ProductMetaDataDO entity = convertRequestToEntity(productRequest);

			// create entry in the repo
			if (!repo.add(entity)) {
				throw new Exception("Unable to create the details of the given product");
			}

		} catch (Exception ex) {
			LOGGER.error("Unable to create prdouct with details", ex.toString());
			throw ex;
		}

	}

	public void update(Long productId, ProductMetaDataRequest productRequest) throws Exception {
		LOGGER.info("Update product request with details : ", productRequest);
		try {
			
			if(repo.get(productId)==null) {
				throw new Exception("metadata with the given product id doesnt exist");
			}

			ProductMetaDataDO entity = convertRequestToEntity(productRequest);

			// update entry in the repo
			if (!repo.update(productId, entity)) {
				throw new Exception("Unable to update the details of the given product");
			}

		} catch (Exception ex) {
			LOGGER.error("Unable to update the details of the given product:", ex.toString());
			throw ex;
		}

	}

	private ProductMetaDataResponse convertEntityToReponse(ProductMetaDataDO entity) {
		ProductMetaDataResponse response = new ProductMetaDataResponse();
		response.setId(entity.getProductId());
		response.setName(entity.getName());
		return response;
	}

	private ProductMetaDataDO convertRequestToEntity(ProductMetaDataRequest request) {
		ProductMetaDataDO entity = new ProductMetaDataDO();
		entity.setProductId(request.getId());
		entity.setName(request.getName());
		return entity;
	}

}
