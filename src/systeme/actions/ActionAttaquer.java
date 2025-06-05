package systeme.actions;

import entite.Entite;
import systeme.GestionnaireDonjon;
import systeme.actions.Action;

import java.util.Scanner;

public class ActionAttaquer implements Action {

    @Override
    public void executer(Entite entite, GestionnaireDonjon gestionnaire) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Coordonnée X de la cible : ");
        int x = scanner.nextInt();
        System.out.print("Coordonnée Y de la cible : ");
        int y = scanner.nextInt();

        gestionnaire.attaquer(entite, x, y);
    }

    @Override
    public String getNom() {
        return "Attaquer";
    }
}
