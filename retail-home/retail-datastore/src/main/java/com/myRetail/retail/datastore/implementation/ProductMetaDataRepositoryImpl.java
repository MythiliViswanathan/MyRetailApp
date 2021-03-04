package com.myRetail.retail.datastore.implementation;

import java.util.Iterator;

import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.myRetail.retail.datastore.api.ProductMetaDataRepository;
import com.myRetail.retail.datastore.model.ProductMetaDataDO;

/**
 * @author mythili
 *
 */
@Repository
public class ProductMetaDataRepositoryImpl implements ProductMetaDataRepository {

	private static final Logger LOGGER = LoggerFactory.getLogger(ProductMetaDataRepositoryImpl.class);
	private static final String PRODUCTID = "productId";
	private static final String NAME = "NAME";

	public boolean add(ProductMetaDataDO entity) {
		try {
			MongoCollection<Document> collection = getProductCollection();
			Document document = new Document(PRODUCTID, entity.getProductId());
			document.append(NAME, entity.getName());

			collection.insertOne(document);
			return true;

		} catch (Exception ex) {
			// Unable to add a new document
			LOGGER.error("Unable to add the details of the given product:", ex.toString());
			return false;
		}

	}

	public boolean update(Long productId, ProductMetaDataDO entity) {
		try {

			// create query document with the needed product id
			Document findDocument = new Document("productId", productId);

			MongoCollection<Document> collection = getProductCollection();

			Document updateDocument = new Document(PRODUCTID, productId);
			updateDocument.append(NAME, entity.getName());

			collection.insertOne(updateDocument);

			collection.updateOne(findDocument, updateDocument);
			return true;

		} catch (Exception ex) {
			// Unable to add a new document
			LOGGER.error("Unable to update the details of the given product:", ex.toString());
			return false;
		}

	}

	public ProductMetaDataDO get(Long productId) throws Exception {
		try {
			MongoCollection<Document> collection = getProductCollection();

			// create query document with the needed product id
			Document findDocument = new Document("productId", productId);

			ProductMetaDataDO entity = null; // return null if data is not found in the DB
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
		MongoDatabase database = mongoClient.getDatabase("productMetaDataDB");
		return database.getCollection("ProductPrice");
	}

	private ProductMetaDataDO createEntityFromDoc(Document document) {
		ProductMetaDataDO entity = new ProductMetaDataDO();
		entity.setProductId((Long) document.get(PRODUCTID));
		entity.setName(document.get(NAME).toString());
		return entity;

	}

}
