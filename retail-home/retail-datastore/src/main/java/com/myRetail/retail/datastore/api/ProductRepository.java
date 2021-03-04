package com.myRetail.retail.datastore.api;

import com.myRetail.retail.datastore.model.ProductPriceDO;

public interface ProductRepository {
	
	public boolean add(ProductPriceDO entity);
	
	public boolean update(Long productId,ProductPriceDO entity);
	
	public ProductPriceDO get(Long productId)throws Exception ;

}
