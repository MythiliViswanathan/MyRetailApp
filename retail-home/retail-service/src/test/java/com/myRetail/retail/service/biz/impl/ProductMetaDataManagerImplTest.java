package com.myRetail.retail.service.biz.impl;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.ArgumentMatchers.any;

import com.myRetail.retail.api.request.ProductMetaDataRequest;
import com.myRetail.retail.api.response.ProductMetaDataResponse;
import com.myRetail.retail.datastore.api.ProductMetaDataRepository;
import com.myRetail.retail.datastore.model.ProductMetaDataDO;

import static org.fest.assertions.api.Assertions.assertThat;


import static org.mockito.MockitoAnnotations.initMocks;

public class ProductMetaDataManagerImplTest {
	
	@Mock
	ProductMetaDataRepository repo;
	
	@InjectMocks
	ProductMetaDataManagerImpl productMetaDataManager;
	
	ProductMetaDataDO entity=new ProductMetaDataDO();
	
	ProductMetaDataRequest request=new ProductMetaDataRequest();
	
	Long productId=1234L;
	
	@BeforeMethod
	void init() {
		initMocks(this);
		entity.setName("Pen");
		entity.setProductId(1234L);
	}
	
	@Test
	public void createTest() throws Exception {
		
		when(repo.add(any(ProductMetaDataDO.class))).thenReturn(true);
		productMetaDataManager.create(request);
		verify(repo).add(any(ProductMetaDataDO.class));
	}
	
	@Test(expectedExceptions = Exception.class,expectedExceptionsMessageRegExp = "(?s).*Unable to create the details of the given product.*")
	public void createFailureTest() throws Exception {
		
		when(repo.add(any(ProductMetaDataDO.class))).thenReturn(false);
		productMetaDataManager.create(request);
	}
	
	@Test
	public void getTest() throws Exception {
		
		when(repo.get(productId)).thenReturn(entity);
		ProductMetaDataResponse response =productMetaDataManager.get(productId);
        assertThat(response.getId()==entity.getProductId()).isTrue();
		verify(repo).get(productId);
	}
	
	@Test(expectedExceptions = Exception.class,expectedExceptionsMessageRegExp = "(?s).*metadata with the given product id doesnt exist")
	public void getErrorTest() throws Exception {
		when(repo.get(productId)).thenReturn(null);
		ProductMetaDataResponse response =productMetaDataManager.get(productId);
	}
	
	
	@Test(expectedExceptions = Exception.class,expectedExceptionsMessageRegExp = "(?s).*Unable to update the details of the given product")
	public void updateFailureTest() throws Exception {
		when(repo.get(productId)).thenReturn(entity);
		productMetaDataManager.update(productId,request);
	}
	
	

}
