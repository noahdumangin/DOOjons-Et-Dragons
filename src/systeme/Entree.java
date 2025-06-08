package systeme;

import java.util.Scanner;

public class Entree {

    public Scanner scanner = new Scanner(System.in);
    Affichage affichage = new Affichage();
    public int lireInt(String message) {
        int valeur;
        while (true) {
            affichage.afficher(message);
            try {
                valeur = Integer.parseInt(scanner.nextLine());
                break;
            } catch (NumberFormatException e) {
                System.out.println("Erreur : Veuillez entrer un entier valide.");
            }
        }
        return valeur;
    }

    public void fermer() {
        scanner.close();
    }
}

