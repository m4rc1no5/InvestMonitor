package pl.marceen.investmonitor.api;

import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

/**
 * @author Marcin Zaremba
 */
@Stateless
@Path("hello-world")
public class HelloWorldResource {

    @GET
    public Response get() {
        return Response.ok("Hello World!").build();
    }
}
