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
    private static int tourActuel = 0;
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Bienvenue dans\n");
        System.out.println(

                "▓█████▄  ▒█████   ▒█████   ███▄    █  ▄▄▄██▀▀▀▒█████   ███▄    █   ██████         \n" +
                        "▒██▀ ██▌▒██▒  ██▒▒██▒  ██▒ ██ ▀█   █    ▒██  ▒██▒  ██▒ ██ ▀█   █ ▒██    ▒         \n" +
                        "░██   █▌▒██░  ██▒▒██░  ██▒▓██  ▀█ ██▒   ░██  ▒██░  ██▒▓██  ▀█ ██▒░ ▓██▄           \n" +
                        "░▓█▄   ▌▒██   ██░▒██   ██░▓██▒  ▐▌██▒▓██▄██▓ ▒██   ██░▓██▒  ▐▌██▒  ▒   ██▒        \n" +
                        "░▒████▓ ░ ████▓▒░░ ████▓▒░▒██░   ▓██░ ▓███▒  ░ ████▓▒░▒██░   ▓██░▒██████▒▒        \n" +
                        " ▒▒▓  ▒ ░ ▒░▒░▒░ ░ ▒░▒░▒░ ░ ▒░   ▒ ▒  ▒▓▒▒░  ░ ▒░▒░▒░ ░ ▒░   ▒ ▒ ▒ ▒▓▒ ▒ ░        \n" +
                        " ░ ▒  ▒   ░ ▒ ▒░   ░ ▒ ▒░ ░ ░░   ░ ▒░ ▒ ░▒░    ░ ▒ ▒░ ░ ░░   ░ ▒░░ ░▒  ░ ░        \n" +
                        " ░ ░  ░ ░ ░ ░ ▒  ░ ░ ░ ▒     ░   ░ ░  ░ ░ ░  ░ ░ ░ ▒     ░   ░ ░ ░  ░  ░          \n" +
                        "   ░        ░ ░      ░ ░           ░  ░   ░      ░ ░           ░       ░          \n" +
                        " ░                                                                                \n" +
                        "▓█████▄▄▄█████▓   ▓█████▄  ██▀███   ▄▄▄        ▄████  ▒█████   ███▄    █   ██████ \n" +
                        "▓█   ▀▓  ██▒ ▓▒   ▒██▀ ██▌▓██ ▒ ██▒▒████▄     ██▒ ▀█▒▒██▒  ██▒ ██ ▀█   █ ▒██    ▒ \n" +
                        "▒███  ▒ ▓██░ ▒░   ░██   █▌▓██ ░▄█ ▒▒██  ▀█▄  ▒██░▄▄▄░▒██░  ██▒▓██  ▀█ ██▒░ ▓██▄   \n" +
                        "▒▓█  ▄░ ▓██▓ ░    ░▓█▄   ▌▒██▀▀█▄  ░██▄▄▄▄██ ░▓█  ██▓▒██   ██░▓██▒  ▐▌██▒  ▒   ██▒\n" +
                        "░▒████▒ ▒██▒ ░    ░▒████▓ ░██▓ ▒██▒ ▓█   ▓██▒░▒▓███▀▒░ ████▓▒░▒██░   ▓██░▒██████▒▒\n" +
                        "░░ ▒░ ░ ▒ ░░       ▒▒▓  ▒ ░ ▒▓ ░▒▓░ ▒▒   ▓▒█░ ░▒   ▒ ░ ▒░▒░▒░ ░ ▒░   ▒ ▒ ▒ ▒▓▒ ▒ ░\n" +
                        " ░ ░  ░   ░        ░ ▒  ▒   ░▒ ░ ▒░  ▒   ▒▒ ░  ░   ░   ░ ▒ ▒░ ░ ░░   ░ ▒░░ ░▒  ░ ░\n" +
                        "   ░    ░          ░ ░  ░   ░░   ░   ░   ▒   ░ ░   ░ ░ ░ ░ ▒     ░   ░ ░ ░  ░  ░  \n" +
                        "   ░  ░              ░       ░           ░  ░      ░     ░ ░           ░       ░  \n" +
                        "                   ░                                                              \n" +
                        "\n");

        // Initialisation des races :
        Race Humain = new Race("Humain", 2, 2, 2, 2, 2);
        Race Elfe = new Race("Elfe", 0, 0, 6, 0, 0);
        Race Nain = new Race("Nain", 0, 6, 0, 0, 0);
        Race Halfelins = new Race("Halfelins", 0, 0, 0, 4, 2);

        //Création des armures légères :
        Armor ArmureEcailles = new Armor("Armures d'écailles", false, 9);
        Armor DemiPlate = new Armor("Demi-Plate", false, 10);

        //Création des armures lourdes :
        Armor CoteMaille = new Armor("Cote de maille", true, 11);
        Armor Harnois = new Armor("Harnois", true, 12);

        // Création des armes courantes au corps à corps :
        Weapon Baton = new Weapon("Baton", false, new Des(1, 6), 1);
        Weapon MasseArme = new Weapon("Masse d'arme", false, new Des(1, 6), 1);

        //Création des armes de guerre au corps-à-corps :
        Weapon EpeeLongue = new Weapon("Epée Longue", true, new Des(1, 8), 1);
        Weapon Rapiere = new Weapon("Rapière", true, new Des(1, 8), 1);
        Weapon EpeeDeuxMains = new Weapon("Epée à deux mains", true, new Des(2, 6), 1);

        //Création des armes à distance :
        Weapon Fronde = new Weapon("Fronde", false, new Des(1, 4), 6);
        Weapon ArbaleteLegere = new Weapon("Arbalète légère", false, new Des(1, 8), 16);
        Weapon ArcCourt = new Weapon("Arc Court", false, new Des(1, 6), 16);


        ArrayList<Item> inventaireMagicien = new ArrayList<>();
        ArrayList<Item> inventaireClerc = new ArrayList<>();
        ArrayList<Item> inventaireGuerrier = new ArrayList<>();
        ArrayList<Item> inventaireRoublard = new ArrayList<>();

        // Création inventaire Magicien
        inventaireMagicien.add(Baton);
        inventaireMagicien.add(Fronde);

        // Création inventaire Clerc
        inventaireClerc.add(MasseArme);
        inventaireClerc.add(ArmureEcailles);
        inventaireClerc.add(ArbaleteLegere);

        //Création inventaire Guerrier
        inventaireGuerrier.add(CoteMaille);
        inventaireGuerrier.add(EpeeLongue);
        inventaireGuerrier.add(ArbaleteLegere);

        //Création inventaire Roublard
        inventaireRoublard.add(Rapiere);
        inventaireRoublard.add(ArcCourt);

        // Création des classes
        Classes Magicien = new Classes(12, inventaireMagicien);
        Classes Clerc = new Classes(16, inventaireClerc);
        Classes Guerrier = new Classes(20, inventaireGuerrier);
        Classes Roublard = new Classes(16, inventaireRoublard);


        // Création des personnages
        String temp_nom="Michel";
        Race temp_race=Humain;
        Classes temp_classe=Guerrier;
        int temp_id_race;
        int temp_id_classe;
        int temp_HP;
        int temp_STRENGTH;
        int temp_DEXT;
        int temp_SPEED;
        int temp_INIT;
        System.out.println("Combien de personnages voulez vous créer ?\n");
        int nb_persos = scanner.nextInt();
        scanner.nextLine();
        for (int i=0; i<nb_persos; i++)
        {
            System.out.println("Rentrez le nom du personnage\n");
            temp_nom=scanner.nextLine();

            System.out.println("Rentrez la race du personnage\n1.Humain\n2.Elfe\n3.Nain\n4.Halfelins\n5.Créer nouvelle race");
            temp_id_race=scanner.nextInt();
            switch(temp_id_race)
            {
                case 1:
                    temp_race=Humain;
                    break;
                case 2:
                    temp_race=Elfe;
                    break;
                case 3:
                    temp_race=Nain;
                    break;
                case 4:
                    temp_race=Halfelins;
                case 5:
                    System.out.println("Rentrez le nom de la nouvelle race\n");
                    break;
                default:
                    System.out.println("Choix incorrect");
                    break;

            }
            System.out.println("Rentrez la classe du personnage\n1.Magicien\n2.Clerc\n3.Guerrier\n4.Roublard\n5.Créer nouvelle classe");
            temp_id_classe=scanner.nextInt();
            scanner.nextLine();
            switch (temp_id_classe)
            {
                case 1:
                    temp_classe=Magicien;
                    break;
                case 2:
                    temp_classe=Clerc;
                     break;
                case 3:
                    temp_classe=Guerrier;
                    break;
                case 4:
                    temp_classe=Roublard;
                    break;
                case 5:
                    System.out.println("Rentrez le nom de la nouvelle classe");
                    break;
                default:
                    System.out.println("Choix incorrect");
                    break;
            }

            Personnage Personnage = new Personnage(temp_nom,temp_race,temp_classe);

        }

        

        System.out.println("Rentrez");

        Personnage joueur = new Personnage("Conan", Humain, Magicien);

        // Création des monstres
        Monstre gobelin = new Monstre(1, "Gobelin", 1, new Des(1, 6), 7, 2, 3, 12, 2, 6);
        Monstre orc = new Monstre(2, "Orc", 1, new Des(1, 8), 15, 4, 1, 13, 1, 5);

        // Création du donjon
        Donjon donjon = new Donjon(10, 10);
        donjon.getCase(0, 0).setPersonnage(joueur);
        donjon.getCase(3, 3).setMonstre(gobelin);
        donjon.getCase(7, 7).setMonstre(orc);
        donjon.getCase(2, 2).setItem(EpeeDeuxMains);
        donjon.afficher();


    }
}