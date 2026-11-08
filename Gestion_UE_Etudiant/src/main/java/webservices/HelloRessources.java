
package webservices;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/hello")
public class HelloRessources {

    @GET
    @Path("/hi")
    @Produces(MediaType.TEXT_PLAIN)
    public Response sayHello() {
        return Response.ok("Hello World!").build();
    }

    @GET
    @Path("/{name}")
    @Produces(MediaType.TEXT_PLAIN)
    public Response sayHelloT(@PathParam("name") String name) {
        return Response.ok("Hello " + name + "!").build();
    }
}
