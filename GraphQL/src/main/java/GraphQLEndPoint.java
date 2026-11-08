
package webservices;

import graphql.ExecutionInput;
import graphql.GraphQL;
import graphql.MutationResolver;
import graphql.QueryResolver;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import graphql.schema.idl.TypeRuntimeWiring;

import entities.Module;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Map;

@Path("/graphql")
public class GraphQLEndPoint {

    private static final QueryResolver queries = new QueryResolver();
    private static final MutationResolver mutations = new MutationResolver();
    private static final GraphQL graphQL = createGraphQL();

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @SuppressWarnings("unchecked")
    public Response execute(Map<String, Object> request) {

        if (request == null || !(request.get("query") instanceof String)) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(Collections.singletonMap(
                            "error", "A GraphQL query is required"
                    ))
                    .build();
        }

        ExecutionInput.Builder input = ExecutionInput.newExecutionInput()
                .query((String) request.get("query"));

        if (request.get("variables") instanceof Map) {
            input.variables((Map<String, Object>) request.get("variables"));
        }

        if (request.get("operationName") instanceof String) {
            input.operationName((String) request.get("operationName"));
        }

        return Response.ok(
                graphQL.execute(input.build()).toSpecification()
        ).build();
    }

    private static GraphQL createGraphQL() {

        String sdl;

        try (InputStream in = GraphQLEndPoint.class
                .getClassLoader()
                .getResourceAsStream("schema.graphql")) {

            if (in == null)
                throw new IllegalStateException("schema.graphql not found");

            sdl = new String(in.readAllBytes(), StandardCharsets.UTF_8);

        } catch (Exception e) {
            throw new IllegalStateException("Cannot load GraphQL schema", e);
        }

        TypeDefinitionRegistry registry =
                new SchemaParser().parse(sdl);

        RuntimeWiring wiring = RuntimeWiring.newRuntimeWiring()

                .type(TypeRuntimeWiring.newTypeWiring("Module")
                        .dataFetcher("type", env -> {
                            Module module = env.getSource();
                            return module.getType() == null
                                    ? null
                                    : module.getType().name();
                        }))

                .type(TypeRuntimeWiring.newTypeWiring("Query")
                        .dataFetcher("getAllModules",
                                env -> queries.getAllModules())

                        .dataFetcher("getModulesByType",
                                env -> queries.getModulesByType(
                                        env.getArgument("type")
                                ))

                        .dataFetcher("getModuleByMatricule",
                                env -> queries.getModuleByMatricule(
                                        env.getArgument("matricule")
                                ))

                        .dataFetcher("getUEByCode",
                                env -> queries.getUEByCode(
                                        env.getArgument("code")
                                ))

                        .dataFetcher("getUEByDomaine",
                                env -> queries.getUEByDomaine(
                                        env.getArgument("domaine")
                                ))

                        .dataFetcher("getUEBySemestre",
                                env -> queries.getUEBySemestre(
                                        env.getArgument("semestre")
                                ))
                )

                .type(TypeRuntimeWiring.newTypeWiring("Mutation")
                        .dataFetcher("addModule", env ->
                                mutations.addModule(
                                        env.getArgument("matricule"),
                                        env.getArgument("libelle"),
                                        env.getArgument("coef"),
                                        env.getArgument("volumeHoraire"),
                                        env.getArgument("codeUE"),
                                        env.getArgument("type")
                                ))

                        .dataFetcher("updateModule", env ->
                                mutations.updateModule(
                                        env.getArgument("matricule"),
                                        env.getArgument("libelle"),
                                        env.getArgument("coef"),
                                        env.getArgument("volumeHoraire"),
                                        env.getArgument("codeUE"),
                                        env.getArgument("type")
                                ))

                        .dataFetcher("deleteModule", env ->
                                mutations.deleteModule(
                                        env.getArgument("matricule")
                                ))

                        .dataFetcher("addUE", env ->
                                mutations.addUE(
                                        env.getArgument("code"),
                                        env.getArgument("domaine"),
                                        env.getArgument("responsable"),
                                        env.getArgument("credits"),
                                        env.getArgument("semestre")
                                ))

                        .dataFetcher("updateUE", env ->
                                mutations.updateUE(
                                        env.getArgument("code"),
                                        env.getArgument("domaine"),
                                        env.getArgument("responsable"),
                                        env.getArgument("credits"),
                                        env.getArgument("semestre")
                                ))

                        .dataFetcher("deleteUE", env ->
                                mutations.deleteUE(
                                        env.getArgument("code")
                                ))
                )

                .build();

        GraphQLSchema schema = new SchemaGenerator()
                .makeExecutableSchema(registry, wiring);

        return GraphQL.newGraphQL(schema).build();
    }
}
