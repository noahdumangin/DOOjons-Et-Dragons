package systeme.actions;

import entite.Entite;
import systeme.GestionnaireDonjon;

public interface Action {
    boolean executer (Entite entite, GestionnaireDonjon gestionnaireDonjon);
    String getNom();

}