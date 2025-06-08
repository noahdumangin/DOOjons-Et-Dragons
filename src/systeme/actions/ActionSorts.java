package systeme.actions;

import entite.Entite;
import personnages.Personnage;
import systeme.Affichage;
import systeme.Entree;
import systeme.GestionnaireDonjon;
import systeme.actions.sorts.*;

import java.util.ArrayList;

public class ActionSorts implements Action {

    private Affichage affichage = new Affichage();
    private Entree entree = new Entree();

    @Override
    public boolean executer(Entite entite, GestionnaireDonjon gestionnaireDonjon) {


        Personnage perso = (Personnage) entite;
        ArrayList<Sort> listeSorts = perso.getSorts();

        if (listeSorts.isEmpty()) {
            affichage.afficher("Vous ne possédez aucun sort.");
            return false;
        }

        affichage.afficher("Sorts disponibles :");
        for (int i = 0; i < listeSorts.size(); i++) {
            affichage.afficher((i + 1) + ". " + listeSorts.get(i).Description());
        }

        int choix = Entree.lireInt("Sélectionnez un sort à lancer :");

        if (choix >= 1 && choix <= listeSorts.size()) {
            Sort sortChoisi = listeSorts.get(choix - 1);
            boolean reussi = sortChoisi.executer(entite, gestionnaireDonjon);
            if (reussi)
            {
                affichage.afficher("Le sort a été lancé avec succès !");
            }
            else
            {
                affichage.afficher("Le sort a échoué.");
                return false;
            }
            return true;
        }
        else
        {
            affichage.afficher("Choix invalide.");
            return false;
        }
    }

    @Override
    public String getNom() {
        return "Sorts";
    }
}
