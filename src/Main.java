/*import items.Armor;
import items.Item;
import items.Weapon;
import monstres.Monstre;
import personnages.*;
import donjon.*;
import monstres.*;
import outils.Des;

import java.util.ArrayList;

public class Main {
    public static void main(String args[])
    {
        System.out.println("Bienvenue dans DOOnjon et Dragons");

        Race Humain = new Race("Humain", 2,2,2,2,2);
        Race Elfe = new Race("Elfe", 0,0,6,0,0);
        Race Nain = new Race("Nain", 0,6,0,0,0);
        Race Halfelins = new Race("Halfelins", 0,0,0,4,2);

        Weapon Baton = new Weapon("Baton",false, new Des(1,6),1);
        Weapon Fronde = new Weapon("Fronde",false,new Des(1,4),6);
        Armor CoteMaille = new Armor("Cote de Maille", true,11);
        ArrayList<Item> inventaireMagicien = new ArrayList<>();
        inventaireMagicien.add(Baton);
        inventaireMagicien.add(Fronde);
        Classes Magicien = new Classes(12, inventaireMagicien);
        //ici on instanciera toutes les classes en fonction de leurs attributs et de leurs items





        Personnage Lucas = new Personnage("Lucas le magnifique",Humain,Magicien);

        Lucas.afficherStats();

        Monstre dragon = new Monstre(1, "Dragon", 5, new Des(4,20), 20,6, 8,10,4,6);

        System.out.println(Lucas.getWeapon());
        Lucas.equiperArme(Baton);
        System.out.println(Lucas.getWeapon().getAtk_reach());
        Lucas.equiperArmure(CoteMaille);
        System.out.println(Lucas.getCA());




        Donjon donjon = new Donjon(18, 18);
        donjon.getCase(1,0).setItem(CoteMaille);
        donjon.getCase(0, 0).setPersonnage(Lucas);
        donjon.getCase(2,0).setMonstre(dragon);
        donjon.getCase(3,7).estObstacle=true;
        donjon.getCase(4,7).estObstacle=true;
        donjon.afficher();

        donjon.deplacerPersonnage(Lucas,"A1","B1");
        Lucas.attaquer(donjon, "C1");
        dragon.attaquer(donjon,"B1");
        donjon.afficher();
    }
}








*/
/*
Second rendu :
Ce qui a été fait :
-refonte complète du système de personnages, de classes, de races, d'items
-Création du donjon, ainsi que des méthodes permettant de set, déplacer les différentes entités, ainsi que leurs attaques

Ce qu'il reste à faire :
-Système de tour par tour
-Affichage de l'ordre des tours
 */
import items.Armor;
import items.Item;
import items.Weapon;
import monstres.Monstre;
import personnages.*;
import donjon.*;
import outils.Des;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

public class Main {
    private static ArrayList<EntiteCombat> ordreTour = new ArrayList<>();
    private static int tourActuel = 0;
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Bienvenue dans DOOnjon et Dragons\n");

        // Initialisation des races
        Race Humain = new Race("Humain", 2,2,2,2,2);
        Race Elfe = new Race("Elfe", 0,0,6,0,0);
        Race Nain = new Race("Nain", 0,6,0,0,0);
        Race Halfelins = new Race("Halfelins", 0,0,0,4,2);

        // Création des armes et armures
        Weapon Baton = new Weapon("Baton",false, new Des(1,6),1);
        Weapon Fronde = new Weapon("Fronde",false,new Des(1,4),6);
        Armor CoteMaille = new Armor("Cote de Maille", true,11);
        ArrayList<Item> inventaireMagicien = new ArrayList<>();
        inventaireMagicien.add(Baton);
        inventaireMagicien.add(Fronde);

        // Création de l'inventaire
        ArrayList<Item> inventaireGuerrier = new ArrayList<>();
        inventaireGuerrier.add(Baton);
        inventaireGuerrier.add(Fronde);

        // Création des classes
        Classes Magicien = new Classes(12, inventaireMagicien);

        // Création des personnages
        Personnage joueur = new Personnage("Conan", Humain, Magicien);

        // Création des monstres
        Monstre gobelin = new Monstre(1, "Gobelin", 1, new Des(1, 6), 7, 2, 3, 12, 2, 6);
        Monstre orc = new Monstre(2, "Orc", 1, new Des(1, 8), 15, 4, 1, 13, 1, 5);

        // Création du donjon
        Donjon donjon = new Donjon(10, 10);
        donjon.getCase(0, 0).setPersonnage(joueur);
        donjon.getCase(3, 3).setMonstre(gobelin);
        donjon.getCase(7, 7).setMonstre(orc);
        donjon.getCase(2, 2).setItem(new Weapon("Arc", false, new Des(1, 6), 5));

        // Initialisation de l'ordre des tours
        initialiserOrdreTour(joueur, gobelin, orc);

        // Boucle principale du jeu
        boucleDeJeu(donjon, joueur);
    }

    // Classe interne pour gérer l'ordre des tours
    static class EntiteCombat {
        Object entite;
        int initiative;

        public EntiteCombat(Object entite, int initiative) {
            this.entite = entite;
            this.initiative = initiative;
        }
    }

    private static void initialiserOrdreTour(Personnage joueur, Monstre... monstres) {
        // Ajout du joueur
        ordreTour.add(new EntiteCombat(joueur, joueur.getInit()));

        // Ajout des monstres
        for (Monstre m : monstres) {
            ordreTour.add(new EntiteCombat(m, m.getInit()));
        }

        // Tri par initiative (décroissant)
        Collections.sort(ordreTour, Comparator.comparingInt(e -> -e.initiative));

        System.out.println("\nOrdre d'initiative:");
        for (EntiteCombat ec : ordreTour) {
            if (ec.entite instanceof Personnage) {
                System.out.println(((Personnage) ec.entite).getNom() + " (Init: " + ec.initiative + ")");
            } else {
                System.out.println(((Monstre) ec.entite).getSpecie() + " (Init: " + ec.initiative + ")");
            }
        }
        System.out.println();
    }

    private static void boucleDeJeu(Donjon donjon, Personnage joueur) {
        boolean jeuEnCours = true;

        while (jeuEnCours) {
            EntiteCombat entiteActuelle = ordreTour.get(tourActuel % ordreTour.size());

            // Affichage du plateau
            donjon.afficher();

            if (entiteActuelle.entite instanceof Personnage) {
                // Tour du joueur
                tourJoueur(donjon, (Personnage) entiteActuelle.entite);
            } else {
                // Tour du monstre
                tourMonstre(donjon, (Monstre) entiteActuelle.entite, joueur);
            }

            // Vérification des conditions de victoire/défaite
            if (joueur.estMort()) {
                System.out.println("Défaite! " + joueur.getNom() + " a été vaincu!");
                jeuEnCours = false;
            } else if (tousMonstresMorts(donjon)) {
                System.out.println("Victoire! Tous les monstres ont été vaincus!");
                joueur.prendreDegats(-joueur.getMaxHp()); // Soigne complètement
                jeuEnCours = false;
            }

            tourActuel++;
        }
    }

    private static void tourJoueur(Donjon donjon, Personnage joueur) {
        System.out.println("\n--- Tour de " + joueur.getNom() + " ---");
        System.out.println("PV: " + joueur.getHp() + "/" + joueur.getMaxHp());
        System.out.println("Actions disponibles: 1. Se déplacer 2. Attaquer 3. Ramasser objet 4. S'équiper");

        int actionsRestantes = 3;
        while (actionsRestantes > 0) {
            System.out.print("\nAction " + (4 - actionsRestantes) + "/3 - Choix: ");
            int choix = scanner.nextInt();

            switch (choix) {
                case 1: // Déplacement
                    System.out.print("Case de destination (ex: B3): ");
                    String destination = scanner.next();
                    if (joueur.seDeplacer(donjon, destination)) {
                        System.out.println(joueur.getNom() + " se déplace vers " + destination);
                    }
                    break;

                case 2: // Attaque
                    System.out.print("Case à attaquer (ex: C4): ");
                    String cible = scanner.next();
                    joueur.attaquer(donjon, cible);
                    break;

                case 3: // Ramasser objet
                    joueur.recupererItem(donjon);
                    break;

                case 4: // Équipement
                    gererEquipement(joueur);
                    break;

                default:
                    System.out.println("Action invalide!");
                    continue;
            }

            actionsRestantes--;
        }
    }

    private static void tourMonstre(Donjon donjon, Monstre monstre, Personnage joueur) {
        System.out.println("\n--- Tour du " + monstre.getSpecie() + " ---");

        // IA simple: se déplacer vers le joueur et attaquer
        try {
            // Trouver la position du monstre et du joueur
            int[] posMonstre = trouverPosition(donjon, monstre);
            int[] posJoueur = trouverPosition(donjon, joueur);

            if (posMonstre != null && posJoueur != null) {
                // Calculer la distance
                int distance = Math.abs(posJoueur[0] - posMonstre[0]) +
                        Math.abs(posJoueur[1] - posMonstre[1]);

                // Si à portée d'attaque
                if (distance <= monstre.getAtkReach()) {
                    String caseJoueur = convertirCoordonneesEnCase(posJoueur[0], posJoueur[1]);
                    monstre.attaquer(donjon, caseJoueur);
                } else {
                    // Se rapprocher du joueur
                    String direction = calculerDirection(posMonstre, posJoueur);
                    monstre.seDeplacer(donjon, direction);
                    System.out.println(monstre.getSpecie() + " se déplace vers " + direction);
                }
            }
        } catch (Exception e) {
            System.out.println(monstre.getSpecie() + " passe son tour");
        }
    }

    // Méthodes utilitaires
    private static int[] trouverPosition(Donjon donjon, Object entite) {
        for (int x = 0; x < donjon.getTailleX(); x++) {
            for (int y = 0; y < donjon.getTailleY(); y++) {
                Case c = donjon.getCase(x, y);
                if (c != null) {
                    if (entite instanceof Personnage && c.getPersonnage() == entite) {
                        return new int[]{x, y};
                    } else if (entite instanceof Monstre && c.getMonstre() == entite) {
                        return new int[]{x, y};
                    }
                }
            }
        }
        return null;
    }

    private static String convertirCoordonneesEnCase(int x, int y) {
        return "" + (char)('A' + x) + (y + 1);
    }

    private static String calculerDirection(int[] posActuelle, int[] posCible) {
        int dx = posCible[0] - posActuelle[0];
        int dy = posCible[1] - posActuelle[1];

        // Priorité au déplacement horizontal
        if (Math.abs(dx) > Math.abs(dy)) {
            return convertirCoordonneesEnCase(
                    posActuelle[0] + (dx > 0 ? 1 : -1),
                    posActuelle[1]);
        } else {
            return convertirCoordonneesEnCase(
                    posActuelle[0],
                    posActuelle[1] + (dy > 0 ? 1 : -1));
        }
    }

    private static void gererEquipement(Personnage joueur) {
        System.out.println("1. Équiper une arme 2. Équiper une armure 3. Déséquiper");
        int choix = scanner.nextInt();

        if (choix == 1) {
            ArrayList<Weapon> armes = new ArrayList<>();
            for (Item item : joueur.getInventory()) {
                if (item instanceof Weapon) {
                    armes.add((Weapon) item);
                }
            }

            if (armes.isEmpty()) {
                System.out.println("Aucune arme disponible!");
                return;
            }

            System.out.println("Armes disponibles:");
            for (int i = 0; i < armes.size(); i++) {
                System.out.println((i + 1) + ". " + armes.get(i).getNom());
            }

            int choixArme = scanner.nextInt() - 1;
            if (choixArme >= 0 && choixArme < armes.size()) {
                joueur.equiperArme(armes.get(choixArme));
                System.out.println(joueur.getNom() + " équipe " + armes.get(choixArme).getNom());
            }
        } else if (choix == 2) {
            // Même logique pour les armures
        }
    }

    private static boolean tousMonstresMorts(Donjon donjon) {
        for (int x = 0; x < donjon.getTailleX(); x++) {
            for (int y = 0; y < donjon.getTailleY(); y++) {
                Case c = donjon.getCase(x, y);
                if (c != null && c.getMonstre() != null) {
                    return false;
                }
            }
        }
        return true;
    }
}