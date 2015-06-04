package com.cpqi.poc.rs;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

/**
 * 
 * The interface TradeService expose the webservice.
 * 
 */
@Path("/poc")
public interface TradeService {

    @GET
    @Produces("text/plain")
    @Path("trade/{id}")
    public String findById(@PathParam("id") String id);

    @GET
    @Produces("text/plain")
    @Path("trade/findall")
    public String findAll();

    @POST
    @Produces("text/plain")
    @Path("trade")
    public String save(String data);

    @DELETE
    @Produces("text/plain")
    @Path("trade")
    public String delete(@PathParam("id") String id);

    @GET
    @Produces("text/plain")
    @Path("trade/count")
    public String count();
}
