package com.myRetsil.retail.service.controller;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.myRetail.retail.api.request.ProductMetaDataRequest;
import com.myRetail.retail.api.response.ProductMetaDataResponse;
import com.myRetail.retail.service.Controller.ProductMetaDataController;
import com.myRetail.retail.service.biz.api.ProductsMetaDataManager;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;

import static org.fest.assertions.api.Assertions.assertThat;

import static org.mockito.MockitoAnnotations.initMocks;

/**
 * @author mythili
 *
 */
public class ProductMetaDataControllerTest {

	@Mock
	ProductsMetaDataManager manager;

	@InjectMocks
	ProductMetaDataController controller;

	ProductMetaDataResponse response = new ProductMetaDataResponse();

	ProductMetaDataRequest request = new ProductMetaDataRequest();

	Long productId = 1234L;

	@BeforeMethod
	void init() {
		initMocks(this);
		response.setName("Pen");
		response.setId(productId);
	}

	@Test
	public void createTest() throws Exception {
		doNothing().when(manager).create(request);
		controller.create(request);
		verify(manager).create(request);
	}

	@Test(expectedExceptions = Exception.class, expectedExceptionsMessageRegExp = "(?s).*Unable to create the details of the given product.*")
	public void createFailureTest() throws Exception {
		doThrow(new Exception("Unable to create the details of the given product")).when(manager).create(request);
		controller.create(request);
		verify(manager).create(request);
	}

	@Test
	public void getTest() throws Exception {
		when(manager.get(productId)).thenReturn(response);
		ProductMetaDataResponse actualResponse = controller.get(productId);
		assertThat(response.getId() == actualResponse.getId());
		assertThat(response.getName() == actualResponse.getName());
		verify(manager).get(productId);
	}

	@Test(expectedExceptions = Exception.class, expectedExceptionsMessageRegExp = "(?s).*metadata with the given product id doesnt exist")
	public void getErrorTest() throws Exception {
		when(manager.get(productId)).thenThrow(new Exception("metadata with the given product id doesnt exist"));
		controller.get(productId);
	}

	@Test
	public void updateTest() throws Exception {
		doNothing().when(manager).update(productId, request);
		controller.update(productId, request);
		verify(manager).update(productId, request);
	}

	@Test(expectedExceptions = Exception.class, expectedExceptionsMessageRegExp = "(?s).*Unable to update the details of the given product")
	public void updateFailureTest() throws Exception {
		doThrow(new Exception("Unable to update the details of the given product")).when(manager).update(productId,
				request);
		controller.update(productId, request);
		verify(manager).update(productId, request);
	}

}
