package com.myRetail.retail.datastore.api;

import com.myRetail.retail.datastore.model.ProductMetaDataDO;

public interface ProductMetaDataRepository {
	
	public boolean add(ProductMetaDataDO entity);
	
	public boolean update(Long productId,ProductMetaDataDO entity);
	
	public ProductMetaDataDO get(Long productId)throws Exception ;

}
