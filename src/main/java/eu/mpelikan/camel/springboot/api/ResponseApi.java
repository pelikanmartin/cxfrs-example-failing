package eu.mpelikan.camel.springboot.api;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Produces(value={"application/xml","application/json", "application/atom+xml", "application/pdf"})
@Consumes(value="application/xml")
@Path("/")
public interface ResponseApi {
    @POST
    @Path("/{path: .*}")
    public Response proxyRequestPOST(String request);

    @GET
    @Path("/{path: .*}")
    public Response proxyRequestGET(String request);
}