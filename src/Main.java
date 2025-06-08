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
import systeme.*;
import systeme.actions.sorts.*;

import java.util.*;

public class Main {
    private static int tourActuel = 0;



    private static Random randomNumbers = new Random();

    public static void main(String[] args) {

        Affichage affichage = new Affichage();
        Entree entree = new Entree();

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
        ArrayList<Item> listeItems = new ArrayList<>();

        //Création des sorts pour les classes qui les intègrent
        //Permet d'ajouter simplement des sorts aux classes
        ArrayList<Sort> sortsMagicien = new ArrayList<>();
        sortsMagicien.add(new ArmeMagique());
        sortsMagicien.add(new BoogieWoogie());
        sortsMagicien.add(new Guerison());

        ArrayList<Sort> sortsClerc = new ArrayList<>();
        sortsClerc.add(new Guerison());

        ArrayList<Sort> sortsGuerrier = new ArrayList<>();
        ArrayList<Sort> sortsRoublard = new ArrayList<>();

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
        Classes Magicien = new Classes("Magicien",12, inventaireMagicien, sortsMagicien);
        Classes Clerc = new Classes("Clerc",16, inventaireClerc, sortsClerc);
        Classes Guerrier = new Classes("Guerrier",20, inventaireGuerrier, sortsGuerrier);
        Classes Roublard = new Classes("Roublard",16, inventaireRoublard, sortsRoublard);

        //int choixPartie = entree.lireInt("Comment voulez-vous jouer ?\n1.Nouveau Donjon\n2.Preset de Donjon");


        ArrayList<Monstre> bestiaire = new ArrayList<>();
        Monstre orc = new Monstre(0, "Orc", 1, new Des(1, 8), 15, 4, 1, 3, 1, 3);
        Monstre gobelin = new Monstre(0, "Gobelin", 1, new Des(1, 6), 7, 2, 3, 3, 3, 6);
        Monstre dragon = new Monstre(0,"Dragon", 4,new Des(2,10),20,0,6,7,3,3);
        Monstre demon = new Monstre(0,"Demon", 1,new Des(3,6),17,12,0,6,3,6);
        Monstre squelette = new Monstre(0,"Squelette", 1,new Des(2,4),7,8,0,1,1,3);
        Monstre golem = new Monstre(0,"Golem", 1,new Des(1,2),30,6,0,8,1,3);

        bestiaire.add(orc);
        bestiaire.add(gobelin);
        bestiaire.add(dragon);
        bestiaire.add(demon);
        bestiaire.add(squelette);
        bestiaire.add(golem);



        ArrayList<Personnage> listeJoueurs = new ArrayList<>();
        int nb_perso = entree.lireInt("Combien de personnages voulez vous créer ?");
        for (int i = 0; i < nb_perso; i++) {
            String nom = entree.lireString("Nom du personnage ?");
            // Sélection race
            int choixRace = entree.lireInt("1. Humain  2. Elfe  3. Nain  4. Halfelins");
            Race raceChoisie;
            switch (choixRace) {
                case 1:
                    raceChoisie = Humain;
                    break;
                case 2:
                    raceChoisie = Elfe;
                    break;
                case 3:
                    raceChoisie = Nain;
                    break;
                case 4:
                    raceChoisie = Halfelins;
                    break;
                default:
                    raceChoisie = Humain;
                    break;
            }


            // Sélection classe
            int choixClasse = entree.lireInt("1. Magicien  2. Clerc  3. Guerrier  4. Roublard");
            Classes classeChoisie;
            switch (choixClasse) {
                case 1:
                    classeChoisie = Magicien;
                    break;
                case 2:
                    classeChoisie = Clerc;
                    break;
                case 3:
                    classeChoisie = Guerrier;
                    break;
                case 4:
                    classeChoisie = Roublard;
                    break;
                default:
                    classeChoisie = Guerrier;
                    break;
            }


            // Création personnage et ajout dans la liste
            Personnage joueur = new Personnage(nom, raceChoisie, classeChoisie);
            listeJoueurs.add(joueur);
        }
        Partie partie = new Partie(listeJoueurs,bestiaire);
        partie.demarrer();




        // Création des personnages







        //Début partie non définini






        //Création des monstres
        //ArrayList<Monstre> ListeMonstre = new ArrayList<>();
        /*int temp_num;
        String temp_specie;
        int temp_atk_reach;
        int temp_nb_des;
        int temp_type_des;
        int temp_hp;
        int temp_strength;
        int temp_dext;
        int temp_CA;
        int temp_init;
        int temp_speed;
        System.out.println("Rentrez le nombre de monstres à créer");
        int nb_monstres = scanner.nextInt();
        scanner.nextLine();

        for (int i=0; i<nb_monstres; i++)
        {
            System.out.println("Rentrez le nom de l'espèce de monstre");
            temp_specie=scanner.nextLine();
            System.out.println("Rentrez sa portée");
            temp_atk_reach=scanner.nextInt();
            System.out.println("Rentrez le nombre de dés qu'il lancera pour attaquer");
            temp_nb_des=scanner.nextInt();
            System.out.println("Rentrez le type de dés");
            temp_type_des=scanner.nextInt();
            System.out.println("Rentrez ses pv");
            temp_hp= scanner.nextInt();
            System.out.println("Rentrez sa force");
            temp_strength= scanner.nextInt();
            System.out.println("Rentrez sa dextérité");
            temp_dext=scanner.nextInt();
            System.out.println("Rentrez sa vitesse");
            temp_speed= scanner.nextInt();
            System.out.println("Rentrez sa classe d'armure");
            temp_CA= scanner.nextInt();
            System.out.println("Rentrez son initiative");
            temp_init=scanner.nextInt();


            Monstre monstre = new Monstre(1, temp_specie, temp_atk_reach, new Des(temp_nb_des, temp_type_des), temp_hp, temp_strength, temp_dext, temp_CA, temp_init, temp_speed);
            //ListeMonstre.add(monstre);
        }

        Personnage joueur = new Personnage("Conan", Humain, Magicien);*/


        // Création du donjon

        /*
        for(int i=0;i<nb_persos;i++)
        {
            donjon.getCase(randomNumbers.nextInt(10), randomNumbers.nextInt(10)).setPersonnage(ListePerso.get(i));
        }
        for(int i=0;i<nb_monstres;i++)
        {
            donjon.getCase(randomNumbers.nextInt(10), randomNumbers.nextInt(10)).setMonstre(ListeMonstre.get(i));
        }*/

        // Création des monstres

        /*Monstre gobelin = new Monstre(1, "Gobelin", 1, new Des(1, 6), 7, 2, 3, 3, 0, 6);
        Monstre orc = new Monstre(2, "Orc", 1, new Des(1, 8), 15, 4, 1, 3, 1, 5);

        Personnage Lucas = new Personnage("Lucas",Halfelins,Magicien);

        Personnage Abel = new Personnage("Abel", Humain, Guerrier);

        Lucas.equiperArme(Baton);
        Lucas.afficherStats();
        Abel.afficherStats();
        Lucas.afficherInventaire();
        donjon.getCase (0,1).setItem(ArmureEcailles);
        //gestionnaireDonjon.ajouterEntite(Abel,2,4);
        gestionnaireDonjon.ajouterEntite(gobelin,1,0);
        gestionnaireDonjon.ajouterEntite(Lucas,0,0);
        partie.jouerPartie(gestionnaireDonjon);*/




        //tour.afficherOrdre();



        //tour.afficherOrdre();

        //donjon.afficher();


        //donjon.afficher();









        /*Lucas.seDeplacer(2,2,donjon);
        Lucas.afficherInventaire();
        Lucas.recupItem(donjon);
        Lucas.afficherInventaire();*/

        //Lucas.seDeplacer(6,8,donjon);
        //Lucas.seDeplacer(4,6,donjon);






        //Début du système de tour par tour


    }
}