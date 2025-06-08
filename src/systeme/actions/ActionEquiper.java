package systeme.actions;

import entite.Entite;
import personnages.Personnage;
import systeme.Affichage;
import systeme.Entree;
import systeme.GestionnaireDonjon;
import items.*;

import java.util.ArrayList;

public class ActionEquiper implements Action{

    private Affichage affichage = new Affichage();
    private Entree entree = new Entree();

    @Override
    public boolean executer(Entite entite, GestionnaireDonjon gestionnaireDonjon)
    {
        Personnage personnage = (Personnage) entite;
        ArrayList<Item> inventaire_personnage = personnage.getInventory();
        affichage.afficher("Inventaire :");

        if(inventaire_personnage.isEmpty())
        {
            affichage.afficher("L'inventaire est vide");
            return false;
        }

        personnage.afficherInventaire();
        affichage.afficher("\nSélectionner l'id de l'item que vous voulez equiper");
        int choix = entree.scanner.nextInt();

        if(choix<=inventaire_personnage.size() && choix > 0)
        {
            Item item_selectionne = inventaire_personnage.get(choix-1);
            item_selectionne.toString();
            personnage.equiper(item_selectionne);
            return true;
        }

        affichage.afficher("Veuillez sélectionnez un id valable");
        return false;
    }

    @Override
    public String getNom()
    {
        return "S'équiper";
    }
}
