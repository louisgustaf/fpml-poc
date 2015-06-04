package com.cpqi.poc.route;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;

/**
 * 
 * This class is DAO like to be used with MongoDB.
 * 
 */
public class MongoService extends RouteBuilder {

    @Override
    public void configure() throws Exception {

	// Save on database
	from("direct:save").to(
		"mongodb:myDb?database=poc&collection=trades&operation=insert")
		.log(LoggingLevel.DEBUG, "Save: ${body}");

	// Retrieve all rows from database
	from("direct:findAll")
		.to("mongodb:myDb?database=poc&collection=trades&operation=findAll")
		.log(LoggingLevel.DEBUG, "Find All: ${body} ");

	// Retrieve one row by query
	from("direct:findOneByQuery")
		.to("mongodb:myDb?database=poc&collection=trades&operation=findOneByQuery")
		.log(LoggingLevel.DEBUG, "Find One By Query: ${body} ");

	// Retrieve one row by id
	from("direct:findById")
		.to("mongodb:myDb?database=poc&collection=trades&operation=findById")
		.log(LoggingLevel.DEBUG, "Find By Id: ${body} ");

	// Count rows from database
	from("direct:count").to(
		"mongodb:myDb?database=poc&collection=trades&operation=count")
		.log(LoggingLevel.DEBUG, "Count: ${body} ");
    }
}
