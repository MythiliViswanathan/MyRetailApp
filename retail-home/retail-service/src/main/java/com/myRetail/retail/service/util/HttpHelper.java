package com.myRetail.retail.service.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class HttpHelper<T> {

	private final CloseableHttpClient httpClient = HttpClients.createDefault();

	private static final Logger LOGGER = LoggerFactory.getLogger(HttpHelper.class);

	public String Get(String url) throws Exception {
		BufferedReader bReader = null;
		CloseableHttpResponse response = null;
		StringBuilder result = new StringBuilder();
		try {
			HttpGet getRequest = new HttpGet(new URI(url));
			getRequest.addHeader("accept", "application/json");
			response = httpClient.execute(getRequest);

			if (response.getStatusLine().getStatusCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + response.getStatusLine().getStatusCode());
			}
			bReader = new BufferedReader(new InputStreamReader((response.getEntity().getContent())));
			String line;
			while ((line = bReader.readLine()) != null) {
				result.append(line);
			}
		}

		finally {
			response.close();
		}
		return result.toString();
	}

	public String Post(String url, String requestBody) throws Exception {
		BufferedReader bReader = null;
		CloseableHttpResponse response = null;
		StringBuilder result = new StringBuilder();
		try {
			HttpPost postRequest = new HttpPost(new URI(url));
			StringEntity input = new StringEntity(requestBody);
			input.setContentType("application/json");
			postRequest.setEntity(input);
			response = httpClient.execute(postRequest);

			if (response.getStatusLine().getStatusCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + response.getStatusLine().getStatusCode());
			}
			bReader = new BufferedReader(new InputStreamReader((response.getEntity().getContent())));
			String line;
			while ((line = bReader.readLine()) != null) {
				result.append(line);
			}
		}

		finally {
			response.close();
		}
		return result.toString();
	}

	public String Put(String url, String requestBody) throws Exception {
		BufferedReader bReader = null;
		CloseableHttpResponse response = null;
		StringBuilder result = new StringBuilder();
		try {
			HttpPut postRequest = new HttpPut(new URI(url));
			StringEntity input = new StringEntity(requestBody);
			input.setContentType("application/json");
			postRequest.setEntity(input);
			response = httpClient.execute(postRequest);

			if (response.getStatusLine().getStatusCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + response.getStatusLine().getStatusCode());
			}
			bReader = new BufferedReader(new InputStreamReader((response.getEntity().getContent())));
			String line;
			while ((line = bReader.readLine()) != null) {
				result.append(line);
			}
		}

		finally {
			response.close();
		}
		return result.toString();
	}

}
