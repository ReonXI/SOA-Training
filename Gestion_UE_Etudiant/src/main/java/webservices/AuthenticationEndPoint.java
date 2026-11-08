
package webservices;

import filtres.JwtSupport;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("authentication")
public class AuthenticationEndPoint {

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_PLAIN)
    public Response authenticateUser(
            @FormParam("username") String username,
            @FormParam("password") String password) {

        if (!"Mariem".equals(username) || !"mariem".equals(password))
            return Response.status(Response.Status.FORBIDDEN).build();

        return Response.ok(JwtSupport.issueToken(username)).build();
    }
}
