package webservices;

import entities.Module;
import entities.UniteEnseignement;
import metiers.ModuleBusiness;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collections;
import java.util.List;

@Path("/modules")
public class ModuleResource {

    private static final ModuleBusiness business = new ModuleBusiness();

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addModule(Module module) {
        if (business.addModule(module))
            return Response.status(Response.Status.CREATED)
                    .entity(Collections.singletonList(module))
                    .build();

        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllModules() {
        return Response.ok(business.getAllModules()).build();
    }

    @GET
    @Path("/UE")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getModulesByUE(@QueryParam("codeUE") int codeUE) {
        UniteEnseignement ue = new UniteEnseignement();
        ue.setCode(codeUE);

        List<Module> modules = business.getModulesByUE(ue);

        if (!modules.isEmpty())
            return Response.ok(modules).build();

        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @GET
    @Path("/{matricule}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getModuleByMatricule(@PathParam("matricule") String matricule) {
        Module module = business.getModuleByMatricule(matricule);

        if (module != null)
            return Response.ok(module).build();

        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @DELETE
    @Path("/{matricule}")
    public Response deleteModule(@PathParam("matricule") String matricule) {
        if (business.deleteModule(matricule))
            return Response.status(Response.Status.OK).build();

        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @PUT
    @Path("/{matricule}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateModule(@PathParam("matricule") String matricule, Module module) {
        if (business.updateModule(matricule, module))
            return Response.status(Response.Status.OK).build();

        return Response.status(Response.Status.NOT_FOUND).build();
    }
}