package com.cpqi.poc.route;

import static org.apache.camel.model.dataformat.XmlJsonDataFormat.ENCODING;
import static org.apache.camel.model.dataformat.XmlJsonDataFormat.NAMESPACE_LENIENT;
import static org.apache.camel.model.dataformat.XmlJsonDataFormat.REMOVE_NAMESPACE_PREFIXES;
import static org.apache.camel.model.dataformat.XmlJsonDataFormat.ROOT_NAME;
import static org.apache.camel.model.dataformat.XmlJsonDataFormat.SKIP_NAMESPACES;

import java.util.HashMap;
import java.util.Map;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;

import com.cpqi.poc.util.ScapeMongoID;

/**
 * 
 * The class ExportRouteBuilder is used to retrieve values from DB and export to
 * XML.
 * 
 */
public class ExportRouteBuilder extends RouteBuilder {

    @Override
    public void configure() throws Exception {
	// 1 - trigger the process
	from("timer:timer.dev?period=5000&repeatCount=1").autoStartup("true")
		.to("direct:retrieve.from.db");

	Map<String, String> xmlJsonOptions = new HashMap<String, String>();
	xmlJsonOptions.put(ENCODING, "UTF-8");
	xmlJsonOptions.put(NAMESPACE_LENIENT, "true");
	xmlJsonOptions.put(ROOT_NAME, "trade");
	xmlJsonOptions.put(SKIP_NAMESPACES, "true");
	xmlJsonOptions.put(REMOVE_NAMESPACE_PREFIXES, "true");

	// 2 - Find from DB in JSON and convert to XML
	from("direct:retrieve.from.db").to("direct:findAll").split().body()
		.bean(ScapeMongoID.class).convertBodyTo(String.class)
		.unmarshal().xmljson(xmlJsonOptions)
		.to("activemq:queue:findall")
		.log(LoggingLevel.DEBUG, "XML: ${body} ");

	// 3 - Received from MQ save files to audit the process.
	from("activemq:queue:findall").to("file:data/jsontoxml");

    }
}
