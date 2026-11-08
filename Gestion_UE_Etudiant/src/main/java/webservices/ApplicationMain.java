
package webservices;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import java.util.HashSet;
import java.util.Set;

@ApplicationPath("rest")
public class ApplicationMain extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> classes = new HashSet<>();

        classes.add(HelloRessources.class);
        classes.add(UniteEnseignementResource.class);
        classes.add(ModuleResource.class);
        classes.add(org.glassfish.jersey.jackson.JacksonFeature.class);

        return classes;
    }
}
