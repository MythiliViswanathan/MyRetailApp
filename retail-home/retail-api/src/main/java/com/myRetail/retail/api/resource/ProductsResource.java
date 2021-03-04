package com.myRetail.retail.api.resource;

import com.myRetail.retail.api.request.ProductRequest;
import com.myRetail.retail.api.response.ProductResponse;

/**
 * @author mythili
 *
 */
public interface ProductsResource {

	/**
	 * Create a product-info with the given info
	 * 
	 * @param ProductRequest
	 * @return
	 */
	public void create(ProductRequest productRequest) throws Exception;

	/**
	 * Update product-info for the given product id
	 * 
	 * @param productId
	 * @param productRequest
	 */
	public void update(Long productId, ProductRequest productRequest) throws Exception;

	/**
	 * Get product info for the given productId
	 * 
	 * @param productId
	 * @return
	 */
	public ProductResponse get(Long productId) throws Exception;

}
