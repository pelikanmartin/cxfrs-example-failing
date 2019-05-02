package eu.mpelikan.camel.springboot.api;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

@Produces(value={"application/xml"})
@Consumes(value="application/xml")
@Path("/")
public interface ObjectApi {
    @POST
    @Path("/{path: .*}")
    public Object proxyRequestPOST(String request);
}