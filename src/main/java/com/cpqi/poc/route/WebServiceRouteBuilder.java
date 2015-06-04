package com.cpqi.poc.route;

import org.apache.camel.CamelExecutionException;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;

/**
 * 
 * The class WebServiceRouteBuilder instantiate a web service rest.
 * 
 */
public class WebServiceRouteBuilder extends RouteBuilder {

    @Override
    public void configure() throws Exception {
	onException(CamelExecutionException.class).log(LoggingLevel.DEBUG,
		"Exception: ${body} ");

	// Route used to instantiate the web service.
	from("jetty:http://localhost:1024?matchOnUriPrefix=true")
		.to("cxfbean:tradeService").convertBodyTo(String.class)
		.choice().when(body().isEqualTo("findAll"))
		.to("direct:retrieve.from.db").when(body().isEqualTo("count"))
		.to("direct:count").when(body().isEqualTo("findById"))
		.to("direct:findById").otherwise().to("direct:save.received")
		.end();
    }
}
