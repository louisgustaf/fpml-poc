package com.cpqi.poc.client;

import org.apache.log4j.Logger;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

/**
 * 
 * The class WebServiceClientGet is a client that sends a Get request to
 * retrieve content from the Database.
 * 
 */
public class WebServiceClientGET {

	private static final Logger log = Logger
			.getLogger(WebServiceClientGET.class);

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {

		try {

			log.debug("This method will create a GET request to return what is in the Database.");
			log.debug("Check main camel application for detailed logs.");

			// Using the jersey-client api to create a client
			Client client = Client.create();

			String id = "";

			// Defining with path of the web service to check
			WebResource webResource = client
					.resource("http://localhost:1024/poc/trade" + id);

			// set to the response and send the request
			ClientResponse response = webResource.accept("text/plain").get(
					ClientResponse.class);

		} catch (Exception e) {
			log.debug("Unexpected exception: ", e);
		}

	}
}
