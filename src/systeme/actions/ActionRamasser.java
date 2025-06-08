package systeme.actions;

import entite.Entite;
import personnages.Personnage;
import systeme.GestionnaireDonjon;
import systeme.*;
import items.*;

public class ActionRamasser implements Action{

    Affichage affichage = new Affichage();


    @Override
    public boolean executer(Entite entite, GestionnaireDonjon gestionnaireDonjon)  //Ici on ne test pas si il n'y a pas d'item sur la case, car si ce n'est pas le cas, l'option n'est pas proposé de base
    {

        Personnage personnage = (Personnage) entite;
        personnage.addItemInventory(gestionnaireDonjon.getDonjon().getCase(entite.getX(), entite.getY()).getItem());
        Item item = gestionnaireDonjon.getDonjon().getCase(entite.getX(), entite.getY()).getItem();
        affichage.afficher("Vous avez ramassé "+item.toString());
        gestionnaireDonjon.getDonjon().getCase(entite.getX(), entite.getY()).setItem(null);

        return true;
    }

    @Override
    public String getNom() {
        return "Ramasser";
    }
}
