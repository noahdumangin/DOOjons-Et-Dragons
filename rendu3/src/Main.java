import items.Armor;
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

        Personnage Lucas = new Personnage("Lucas le magnifique",Humain,Magicien);

        Lucas.afficherStats();

        Monstre dragon = new Monstre(1, "Dragon", 5, new Des(4,20), 20,6, 8,10,4,6);

        System.out.println(Lucas.getWeapon());
        Lucas.equiperArme(Baton);
        System.out.println(Lucas.getWeapon());



        Donjon donjon = new Donjon(18, 18);
        donjon.getCase(1,0).setItem(CoteMaille);
        donjon.getCase(0, 0).setPersonnage(Lucas);
        donjon.getCase(2,0).setMonstre(dragon);
        donjon.getCase(3,7).estObstacle=true;
        donjon.getCase(4,7).estObstacle=true;
        donjon.afficher();

        donjon.deplacerPersonnage(Lucas,"A1","B1");
        Lucas.attaquer(donjon, "C1");
        donjon.afficher();

    }
}









/*
Second rendu :
Ce qui a été fait :
-refonte complète du système de personnages, de classes, de races, d'items
-Création du donjon, ainsi que des méthodes permettant de set, déplacer les différentes entités

Ce qu'il reste à faire :
-Système de tour par tour
-Affichage de l'ordre des tours
 */