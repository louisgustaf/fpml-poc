package com.cpqi.poc.util;

import org.apache.camel.Body;
import org.apache.camel.Exchange;
import org.bson.types.ObjectId;

import com.mongodb.BasicDBObject;

/**
 * 
 * The class ScapteMongoID is used to remove the tag id from DB.
 * 
 */
public class ScapeMongoID {

    /**
     * Method used to remove the id.
     * 
     * @param exchange
     * @param body
     */
    public void toMap(Exchange exchange, @Body BasicDBObject body) {
	ObjectId id = body.getObjectId("_id");
	body.put("_id", id.toString());
	exchange.getOut().setBody(body.clone());
    }
}
