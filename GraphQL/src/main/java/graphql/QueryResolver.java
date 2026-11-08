
package graphql;

import business.ModuleBusiness;
import business.UniteEnseignementBusiness;
import entities.Module;
import entities.UniteEnseignement;

import java.util.List;

public class QueryResolver {

    protected static final UniteEnseignementBusiness ueBusiness =
            new UniteEnseignementBusiness();

    protected static final ModuleBusiness moduleBusiness =
            new ModuleBusiness();

    public List<Module> getAllModules() {
        return moduleBusiness.getAllModules();
    }

    public List<Module> getModulesByType(String type) {
        return moduleBusiness.getModulesByType(
                Module.TypeModule.valueOf(type)
        );
    }

    public Module getModuleByMatricule(String matricule) {
        return moduleBusiness.getModuleByMatricule(matricule);
    }

    public UniteEnseignement getUEByCode(int code) {
        return ueBusiness.getUEByCode(code);
    }

    public List<UniteEnseignement> getUEByDomaine(String domaine) {
        return ueBusiness.getUEByDomaine(domaine);
    }

    public List<UniteEnseignement> getUEBySemestre(int semestre) {
        return ueBusiness.getUEBySemestre(semestre);
    }
}
