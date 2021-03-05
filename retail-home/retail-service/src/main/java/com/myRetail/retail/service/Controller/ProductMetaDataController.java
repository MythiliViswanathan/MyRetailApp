package com.myRetail.retail.service.Controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.myRetail.retail.api.request.ProductMetaDataRequest;
import com.myRetail.retail.api.resource.ProductMetaDataResource;
import com.myRetail.retail.api.response.ProductMetaDataResponse;
import com.myRetail.retail.service.biz.api.ProductsMetaDataManager;

/**
 * @author mythili
 *
 */
@RestController
@RequestMapping("/myRetail/productMetaData")
@ConditionalOnProperty(value = "metadata.api.enabled", havingValue = "true", matchIfMissing = true)
public class ProductMetaDataController implements ProductMetaDataResource {

	@Autowired
	private ProductsMetaDataManager productsInfoManager;

	@RequestMapping(method = RequestMethod.POST, produces = "application/json")
	public void create(@RequestBody(required = true) @Valid ProductMetaDataRequest productInfoRequest)
			throws Exception {
		productsInfoManager.create(productInfoRequest);
	}

	@RequestMapping(path = "/{productId}", method = RequestMethod.PUT, produces = "application/json")
	public void update(@PathVariable(name = "productId") Long productId,
			@RequestBody(required = true) @Valid ProductMetaDataRequest productRequest) throws Exception {
		productsInfoManager.update(productId, productRequest);
	}

	@RequestMapping(path = "/{productId}", method = RequestMethod.GET, produces = "application/json")
	public ProductMetaDataResponse get(@PathVariable(name = "productId") Long productId) throws Exception {
		return productsInfoManager.get(productId);
	}

}
