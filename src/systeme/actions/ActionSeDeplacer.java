package systeme.actions;

import entite.Entite;
import systeme.GestionnaireDonjon;
import systeme.actions.Action;

import java.util.Scanner;

public class ActionSeDeplacer implements Action {

    @Override
    public boolean executer(Entite entite, GestionnaireDonjon gestionnaire) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Coordonnée X de destination : ");
        int x = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Coordonnée Y de destination : ");
        int y = scanner.nextInt();
        scanner.nextLine();

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
