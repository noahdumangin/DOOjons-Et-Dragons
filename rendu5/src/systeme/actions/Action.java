package systeme.actions;

import entite.Entite;
import systeme.GestionnaireDonjon;

public interface Action {
    void executer (Entite entite, GestionnaireDonjon gestionnaireDonjon);
    String getNom();

}
