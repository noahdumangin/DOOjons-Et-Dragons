import personnages.*;
import personnages.classes.*;
import items.Armors.Armor;
import items.Armors.Heavy.*;
import items.Weapons.*;
import items.Weapons.SimpleRange.*;
public class Main {
    public static void main(String args[])
    {
        System.out.println("Bienvenue dans DOOnjon et Dragons");
        Personnage Leo = new Clerc("Leo","Nain");
        Leo.afficherStats();
        Personnage Abel = new Guerrier("Abel","Humain");
        Abel.afficherStats();
        Personnage Lucas = new Mage("Lucas","Halfelin");
        Lucas.afficherStats();
        Armor hrn = new Harness();
        System.out.println(hrn.getName());
        System.out.println(hrn.getCA());

        Weapon wep = new LightCrossbow();
        System.out.println(wep.getName());
        System.out.println(wep.getDice());
        System.out.println(wep.getRange());
    }
}
/*Premier rendu et mise en commun
Ce qui a été fait :
Création de personnages
Création d'équipements

Ce qu'il reste à faire :
Créations de stats random à partir de la classe Des
Ajout des items de base de chaque classes lors de la création d'un personnage
Création de monstres
Création du système de combats
Création du donjon/gestion des cases
 */