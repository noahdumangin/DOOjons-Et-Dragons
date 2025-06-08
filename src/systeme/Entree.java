package systeme;

import java.util.Scanner;
import systeme.*;

public class Entree {

    public static Scanner scanner = new Scanner(System.in);
    private Affichage affichage = new Affichage();
    public static int lireInt(String message) {
        int valeur;
        while (true) {
            Affichage.afficher(message);
            try {
                valeur = Integer.parseInt(scanner.nextLine());
                break;
            } catch (NumberFormatException e) {
                Affichage.afficher("Erreur : Veuillez entrer un entier valide.");
            }
        }
        return valeur;
    }
    public String lireString(String message) {
        affichage.afficher (message);
        return scanner.nextLine();
    }

    public static void fermer() {
        scanner.close();
    }
}

