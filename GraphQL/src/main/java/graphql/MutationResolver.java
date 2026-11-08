
package graphql;

import entities.Module;
import entities.UniteEnseignement;

public class MutationResolver extends QueryResolver {

    public boolean addModule(String matricule, String libelle,
                             int coef, int volumeHoraire,
                             int codeUE, String type) {

        UniteEnseignement ue = ueBusiness.getUEByCode(codeUE);

        if (ue == null || moduleBusiness.getModuleByMatricule(matricule) != null)
            return false;

        Module module = new Module(
                matricule,
                libelle,
                coef,
                volumeHoraire,
                Module.TypeModule.valueOf(type),
                ue
        );

        return moduleBusiness.addModule(module);
    }

    public Module updateModule(String matricule, String libelle,
                               int coef, int volumeHoraire,
                               int codeUE, String type) {

        UniteEnseignement ue = ueBusiness.getUEByCode(codeUE);

        if (ue == null)
            return null;

        Module module = new Module(
                matricule,
                libelle,
                coef,
                volumeHoraire,
                Module.TypeModule.valueOf(type),
                ue
        );

        if (moduleBusiness.updateModule(matricule, module))
            return module;

        return null;
    }

    public String deleteModule(String matricule) {
        if (moduleBusiness.deleteModule(matricule))
            return "deleted successfully";

        return "not found";
    }

    public boolean addUE(int code, String domaine,
                         String responsable, int credits,
                         int semestre) {

        if (ueBusiness.getUEByCode(code) != null)
            return false;

        UniteEnseignement ue = new UniteEnseignement(
                code, domaine, responsable, credits, semestre
        );

        return ueBusiness.addUniteEnseignement(ue);
    }

    public UniteEnseignement updateUE(int code, String domaine,
                                      String responsable, int credits,
                                      int semestre) {

        UniteEnseignement ue = new UniteEnseignement(
                code, domaine, responsable, credits, semestre
        );

        if (ueBusiness.updateUniteEnseignement(code, ue))
            return ue;

        return null;
    }

    public String deleteUE(int code) {
        if (ueBusiness.deleteUniteEnseignement(code))
            return "deleted successfully";

        return "not found";
    }
}
