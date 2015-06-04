package com.cpqi.poc.route;

import static org.apache.camel.model.dataformat.XmlJsonDataFormat.ENCODING;
import static org.apache.camel.model.dataformat.XmlJsonDataFormat.NAMESPACE_LENIENT;
import static org.apache.camel.model.dataformat.XmlJsonDataFormat.REMOVE_NAMESPACE_PREFIXES;
import static org.apache.camel.model.dataformat.XmlJsonDataFormat.ROOT_NAME;
import static org.apache.camel.model.dataformat.XmlJsonDataFormat.SKIP_NAMESPACES;

import java.util.HashMap;
import java.util.Map;

import nu.xom.ParsingException;

import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.Processor;
import org.apache.camel.ValidationException;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.spring.Main;

import biz.c24.io.api.ParserException;

import com.cpqi.poc.util.FpmlValidator;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;

/**
 * Class Responsable for the creation of availables routes in Camel. We can
 * think of a route as a service/endpoint that our code can invoke to do
 * something for us.
 */
public class ImportRouteBuilder extends RouteBuilder {

    public static void main(String... args) throws Exception {
	Main.main(args);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void configure() {
	// Used to handle exception
	onException(ValidationException.class, ParsingException.class,
		ParserException.class,
		biz.c24.io.api.data.ValidationException.class)
		.useOriginalMessage().handled(true).to("direct:save.exception");

	/* ############## */
	/* Receive Routes */
	/* ############## */

	// Route to retrieve from MQ
	from("activemq:queue:inbox").to("direct:save.received");

	// Route to retrieve from folder received
	from("file:data/received?charset=utf-8")
	// Validate the XML
		.to("direct:validate");

	// Route to retrieve valid XMLs
	from("file:data/valid?charset=utf-8")
	// Convert XML to Json
		.to("direct:convert.to.json")
		// Save Json file
		.to("direct:save.json");

	// Route to retrieve valid Json files
	from("file:data/json?noop=true")
	// Convert Json to DBobject
		.to("direct:convert.to.dbobject")
		// Save on database
		.to("direct:save");

	/* ########## */
	/* Validation */
	/* ########## */

	// Validate XML using XSD
	from("direct:validate")
		.log(LoggingLevel.DEBUG, "XML to be validated: ${body} ")
		.convertBodyTo(String.class).choice()
		.when(body().contains("5-3")).to("direct:validate.new")
		.when(body().contains("4-1")).to("direct:validate.old").end();

	// Validate using FpML version 5.3
	from("direct:validate.new").doTry()
		.to("validator:org/fpml/fpml-main-5-3.xsd")
		.to("direct:save.valid").doCatch(ValidationException.class)
		.to("direct:save.invalid").end();

	// Validate using C24 validation
	from("direct:validate.old")
		.doTry()
		.bean(FpmlValidator.class, "validate")
		.to("direct:save.valid")
		.doCatch(biz.c24.io.api.data.ValidationException.class,
			ParserException.class).to("direct:save.invalid").end();

	/* ######## */
	/* Adapters */
	/* ######## */

	Map<String, String> xmlJsonOptions = new HashMap<String, String>();
	xmlJsonOptions.put(ENCODING, "UTF-8");
	xmlJsonOptions.put(NAMESPACE_LENIENT, "true");
	xmlJsonOptions.put(ROOT_NAME, "trade");
	xmlJsonOptions.put(SKIP_NAMESPACES, "true");
	xmlJsonOptions.put(REMOVE_NAMESPACE_PREFIXES, "true");

	// Route to convert files from XML to Json
	from("direct:convert.to.json").convertBodyTo(String.class).marshal()
		.xmljson(xmlJsonOptions);

	// Convert Json to DBObject
	from("direct:convert.to.dbobject").convertBodyTo(String.class).process(
		new Processor() {

		    @Override
		    public void process(Exchange ex) throws Exception {
			DBObject dbObject = (DBObject) JSON.parse(ex.getIn()
				.getBody(String.class));
			ex.getOut().setBody(dbObject);
		    }
		});

	/* ################ */
	/* Save File Routes */
	/* ################ */

	// Route to save files to received folder
	from("direct:save.received").to("file:data/received");

	// Route to save files to valid folder
	from("direct:save.valid").to("file:data/valid");

	// Route to save files to invalid folder
	from("direct:save.invalid").to("file:data/invalid");

	// Route to save files to json folder
	from("direct:save.json").to("file:data/json");

	// Route to save files to exception folder
	from("direct:save.exception").to("file:data/exception");

	// Route to save files to completed folder
	from("direct:save.completed").to("file:data/completed");
    }
}
