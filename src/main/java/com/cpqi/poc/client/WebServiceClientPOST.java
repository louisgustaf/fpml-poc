package com.cpqi.poc.client;

import java.io.File;

import org.apache.camel.CamelContext;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.log4j.Logger;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

/**
 * 
 * The class WebServiceClientInsert is a client that sends a POST request
 * containing a XML to the web service.
 * 
 */
public class WebServiceClientPOST {

	private static final Logger log = Logger
			.getLogger(WebServiceClientPOST.class);

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) {
		try {

			log.debug("This method will create a PUT request to save a Fpml to the Database.");
			log.debug("Check main camel application for detailed logs.");

			// Using the jersey-client api to create a client
			Client client = Client.create();

			// Set the web service path to test
			WebResource webResource = client
					.resource("http://localhost:1024/poc/trade");

			// Create a camel context to read a XML file into a String.
			CamelContext context = new DefaultCamelContext();

			File testXml = new File(args[0]);
		    String file = context.getTypeConverter().convertTo(String.class,
			    testXml);

			// Setting the response, defining that it is a POST request
			// It could also be defined as a PUT request, if defined in the web
			// service
			ClientResponse response = webResource.type("text/plain").post(
					ClientResponse.class, file);

		} catch (Exception e) {
			log.debug("Unexpected exception: ", e);
		}
	}
}
