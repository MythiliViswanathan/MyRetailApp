package com.myRetail.retail.api.resource;

import com.myRetail.retail.api.request.ProductMetaDataRequest;
import com.myRetail.retail.api.response.ProductMetaDataResponse;

/**
 * @author mythili
 *
 */
public interface ProductMetaDataResource {

	/**
	 * Get product info for the given productId
	 * 
	 * @param productId
	 * @return
	 */
	public ProductMetaDataResponse get(Long productId) throws Exception;

	/**
	 * Create a product metadata with the given info
	 * 
	 * @param ProductMetaDataRequest
	 * @return
	 */
	public void create(ProductMetaDataRequest request) throws Exception;

	/**
	 * Update product metadata for the given product id
	 * 
	 * @param productId
	 * @param productMetaDataRequest
	 */
	public void update(Long productId, ProductMetaDataRequest request) throws Exception;

}
