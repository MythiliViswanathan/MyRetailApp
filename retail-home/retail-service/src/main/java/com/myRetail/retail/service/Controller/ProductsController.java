package com.myRetail.retail.service.Controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.myRetail.retail.api.request.ProductRequest;
import com.myRetail.retail.api.resource.ProductsResource;
import com.myRetail.retail.api.response.ProductResponse;
import com.myRetail.retail.service.biz.api.ProductsManager;
import javax.validation.Valid;

/**
 * @author mythili
 *
 */

@RestController
@RequestMapping("/myRetail/products")
@ConditionalOnProperty(value = "metadata.api.enabled", havingValue = "true", matchIfMissing = true)
public class ProductsController implements ProductsResource {

	@Autowired
	private ProductsManager productManager;

	@RequestMapping(method = RequestMethod.POST, produces = "application/json")
	public void create(@RequestBody(required = true) @Valid ProductRequest productRequest) throws Exception {
		productManager.create(productRequest);
	}

	@RequestMapping(path = "/{productId}", method = RequestMethod.PUT, produces = "application/json")
	public void update(@PathVariable(name = "productId") Long productId,
			@RequestBody(required = true) @Valid ProductRequest productRequest) throws Exception {
		productManager.update(productId, productRequest);
	}

	@RequestMapping(path = "/{productId}", method = RequestMethod.GET, produces = "application/json")
	public ProductResponse get(@PathVariable(name = "productId") Long productId) throws Exception {
		return productManager.get(productId);
	}

}
