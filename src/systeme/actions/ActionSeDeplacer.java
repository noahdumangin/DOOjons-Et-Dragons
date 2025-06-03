package systeme.actions;

import entite.Entite;
import systeme.GestionnaireDonjon;
import systeme.actions.Action;

import java.util.Scanner;

public class ActionSeDeplacer implements Action {

    @Override
    public void executer(Entite entite, GestionnaireDonjon gestionnaire) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Coordonnée X de destination : ");
        int x = scanner.nextInt();
        System.out.print("Coordonnée Y de destination : ");
        int y = scanner.nextInt();

        gestionnaire.deplacerEntite(entite, x, y);
    }

    @Override
    public String getNom() {
        return "Se déplacer";
    }
}
