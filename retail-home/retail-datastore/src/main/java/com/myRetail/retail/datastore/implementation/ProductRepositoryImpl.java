package com.myRetail.retail.datastore.implementation;

import com.mongodb.MongoClient;

import java.util.Iterator;

import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.myRetail.retail.api.model.CurrencyCode;
import com.myRetail.retail.datastore.api.ProductRepository;
import com.myRetail.retail.datastore.model.ProductPriceDO;

import org.springframework.stereotype.Repository;

/**
 * @author mythili
 *
 */
@Repository
public class ProductRepositoryImpl implements ProductRepository {

	private static final Logger LOGGER = LoggerFactory.getLogger(ProductMetaDataRepositoryImpl.class);
	private static final String PRODUCTID = "productId";
	private static final String VALUE = "value";
	private static final String CODE = "code";

	public boolean add(ProductPriceDO entity) {
		try {
			MongoCollection<Document> collection = getProductCollection();
			Document document = new Document(PRODUCTID, entity.getProductId());
			document.append(VALUE, entity.getValue());
			document.append(CODE, entity.getCode().toString());

			collection.insertOne(document);
			return true;

		} catch (Exception ex) {
			// Unable to add a new document
			LOGGER.error("Unable to add the details of the given product:", ex.toString());
			return false;
		}

	}

	public boolean update(Long productId, ProductPriceDO entity) {
		try {

			// create query document with the needed product id
			Document findDocument = new Document(PRODUCTID, productId);

			MongoCollection<Document> collection = getProductCollection();

			Document updateDocument = new Document(PRODUCTID, productId);
			updateDocument.append(VALUE, entity.getValue());
			updateDocument.append(CODE, entity.getCode().toString());

			collection.insertOne(updateDocument);

			collection.updateOne(findDocument, updateDocument);
			return true;

		} catch (Exception ex) {
			LOGGER.error("Unable to update the details of the given product:", ex.toString());
			return false;
		}

	}

	public ProductPriceDO get(Long productId) throws Exception {
		try {
			MongoCollection<Document> collection = getProductCollection();

			// create query document with the needed product id
			Document findDocument = new Document(PRODUCTID, productId);

			ProductPriceDO entity = null; // return null if data is not found in the DB
			FindIterable<Document> iterDoc = collection.find(findDocument);

			Iterator it = iterDoc.iterator();

			if (it.hasNext()) {
				Document document = (Document) it.next();
				entity = createEntityFromDoc(document);
			}
			return entity;

		} catch (Exception ex) {
			LOGGER.error("Unable to add the details of the given product:", ex.toString());
			throw new Exception("Exception occured while fetch the product info from DB");
		}

	}

	private MongoCollection<Document> getProductCollection() {
		MongoClient mongoClient = new MongoClient("localhost", 27017);
		MongoDatabase database = mongoClient.getDatabase("productPriceDB");
		return database.getCollection("ProductPrice");
	}

	private ProductPriceDO createEntityFromDoc(Document document) {
		ProductPriceDO entity = new ProductPriceDO();
		entity.setProductId((Long) document.get(PRODUCTID));
		entity.setCode(CurrencyCode.valueOf(document.get(CODE).toString()));
		entity.setValue(Double.parseDouble(document.get(VALUE).toString()));
		return entity;

	}

}
