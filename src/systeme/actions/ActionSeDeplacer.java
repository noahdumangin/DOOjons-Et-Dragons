package systeme.actions;

import entite.Entite;
import systeme.GestionnaireDonjon;
import systeme.actions.Action;
import systeme.*;

import java.util.Scanner;

public class ActionSeDeplacer implements Action {

    @Override
    public boolean executer(Entite entite, GestionnaireDonjon gestionnaire) {
        Scanner scanner = new Scanner(System.in);
        int x = Entree.lireInt("Coordonnée X de destination : ");
        int y = Entree.lireInt("Coordonnée Y de destination : ");

        boolean succes = gestionnaire.deplacerEntite(entite, x, y);
        if(succes)
        {
            return true;
        }
        return false;
    }

    @Override
    public String getNom() {
        return "Se déplacer";
    }
}