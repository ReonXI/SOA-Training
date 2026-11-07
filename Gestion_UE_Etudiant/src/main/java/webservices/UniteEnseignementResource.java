package webservices;

import entities.UniteEnseignement;
import metiers.UniteEnseignementBusiness;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/UE")
public class UniteEnseignementResource {

    private static final UniteEnseignementBusiness business = new UniteEnseignementBusiness();

    @POST
    @Consumes(MediaType.APPLICATION_XML)
    public Response addUniteEnseignement(UniteEnseignement ue) {
        if (business.addUniteEnseignement(ue))
            return Response.status(Response.Status.CREATED).build();

        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllUE(@QueryParam("semestre") Integer semestre,
                             @QueryParam("code") Integer code) {

        if (code != null) {
            UniteEnseignement ue = business.getUEByCode(code);

            if (ue != null)
                return Response.ok(ue).build();

            return Response.status(Response.Status.NOT_FOUND).build();
        }

        if (semestre != null)
            return Response.ok(business.getUEBySemestre(semestre)).build();

        return Response.ok(business.getListeUE()).build();
    }

    @DELETE
    @Path("/{code}")
    public Response deleteUE(@PathParam("code") int code) {
        if (business.deleteUniteEnseignement(code))
            return Response.status(Response.Status.OK).build();

        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @PUT
    @Path("/{code}")
    @Consumes(MediaType.APPLICATION_XML)
    public Response updateUE(@PathParam("code") int code, UniteEnseignement ue) {
        if (business.updateUniteEnseignement(code, ue))
            return Response.status(Response.Status.OK).build();

        return Response.status(Response.Status.NOT_FOUND).build();
    }
}