package systeme.actions.sorts;
import systeme.GestionnaireDonjon;
import entite.Entite;
public interface Sort {

    public boolean executer(Entite entite, GestionnaireDonjon gestionnaireDonjon);

    public String Description();
}
