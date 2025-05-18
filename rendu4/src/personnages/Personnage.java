package personnages;
import items.*;

import java.util.ArrayList;
import outils.Des;
import donjon.*;
import monstres.Monstre;
public class Personnage {
    private String m_nom;

    private int m_hp;
    private int m_max_hp;
    private int m_strength;
    private int m_dext;
    private int m_speed;
    private int m_init;

    private ArrayList<Item> m_inventory;

    private Armor m_armor;

    private Weapon m_weapon;


    public Personnage(String nom, Race race, Classes classes)
    {
        Des Buffer = new Des(4,4);
        ArrayList<Integer> BuffDes = Buffer.genererRandom();
        this.m_nom=nom;
        this.m_hp=race.getHp()+classes.getHp();
        this.m_strength=race.getStrength()+BuffDes.get(0);
        this.m_dext=race.getDext()+BuffDes.get(1);
        this.m_speed=race.getSpeed()+BuffDes.get(2);
        this.m_init=race.getInit()+BuffDes.get(3);
        this.m_inventory=classes.getInventory();
        this.m_max_hp=m_hp;


    }
    public void afficherStats() {
        System.out.println("Nom : " + m_nom);
        System.out.println("PV : " + m_hp +"/"+m_max_hp);
        System.out.println("Force : " + m_strength);
        System.out.println("Dexterité : " + m_dext);
        System.out.println("Vitesse : " + m_speed);
        System.out.println("Initiative : " + m_init);
    }

    public String getNom()
    {
        return m_nom;
    }

    public void equiperArme(Weapon weapon)
    {
        if (m_inventory.contains(weapon))
        {
            m_weapon=weapon;
            m_inventory.remove(weapon);
        }

    }
    public void equiperArmure(Armor armor)
    {
        if (m_inventory.contains(armor))
        {
            m_armor=armor;
            m_inventory.remove(armor);
        }

    }
    public void desequiperArme(Weapon weapon)
    {
        if(m_weapon==weapon)
        {
            m_inventory.add(weapon);
            m_weapon=null;
        }
    }
    public void desequiperArmmure(Armor armor)
    {
        if(m_armor==armor)
        {
            m_inventory.add(armor);
            m_armor=null;
        }
    }
    public int getHp()
    {
        return m_hp;
    }
    public int getStrength()
    {
        return m_strength;
    }
    public int getDext()
    {
        return m_dext;
    }
    public int getSpeed()
    {
        return m_speed;
    }
    public int getInit()
    {
        return m_init;
    }
    public Weapon getWeapon() {
        return m_weapon;
    }
    public Armor getArmor()
    {
        return m_armor;
    }


    public ArrayList<Item> getInventory()
    {
        return m_inventory;
    }

    public int getCA() {
        // CA de base = 10
        int baseCA = 10;

        // Si une armure est équipée, on ajoute son bonus
        if (m_armor != null) {
            return baseCA + m_armor.getCA();
        }

        return baseCA;
    }


    public int getMaxHp() {
        return m_max_hp;
    }

    // Dans Personnage.java
    public boolean seDeplacer(Donjon donjon, String caseArrivee) {
        // Trouver la case actuelle du personnage
        int[] coordActuelles = trouverPositionDansDonjon(donjon);
        if (coordActuelles == null) return false;

        return donjon.deplacerPersonnage(this, coordActuelles[0] + "," + coordActuelles[1], caseArrivee);
    }

    public int[] trouverPositionDansDonjon(Donjon donjon) {
        for (int x = 0; x < donjon.getTailleX(); x++) {
            for (int y = 0; y < donjon.getTailleY(); y++) {
                Case c = donjon.getCase(x, y);
                if (c != null && c.getPersonnage() == this) {
                    return new int[]{x, y};
                }
            }
        }
        return null;
    }

    public void recupererItem(Donjon donjon) {
        int[] position = trouverPositionDansDonjon(donjon);
        if (position == null) return;

        Case caseActuelle = donjon.getCase(position[0], position[1]);
        Item item = caseActuelle.getItem();

        if (item != null) {
            m_inventory.add(item);
            caseActuelle.setItem(null);
            System.out.println(m_nom + " a récupéré " + item.getNom());
        }
    }

    // Dans Personnage.java
    public boolean attaquer(Donjon donjon, String caseCible) {
        if (m_weapon == null) {
            System.out.println("Aucune arme équipée !");
            return false;
        }

        int[] coordAttaquant = trouverPositionDansDonjon(donjon);
        int[] coordCible = donjon.convertirCaseEnCoordonnees(caseCible);

        if (coordAttaquant == null || coordCible == null) return false;

        // Vérifier la portée
        int distance = Math.abs(coordCible[0] - coordAttaquant[0]) +
                Math.abs(coordCible[1] - coordAttaquant[1]);

        if (distance > m_weapon.getAtk_reach()) {
            System.out.println("Cible hors de portée !");
            return false;
        }

        Case caseCibleObj = donjon.getCase(coordCible[0], coordCible[1]);
        Monstre monstreCible = caseCibleObj.getMonstre();

        if (monstreCible == null) {
            System.out.println("Aucun monstre à attaquer sur cette case !");
            return false;
        }

        // Jet d'attaque
        Des deAttaque = new Des(1, 20);
        int jetAttaque = deAttaque.genererRandom().get(0);
        int bonus = m_weapon.getAtk_reach() > 1 ? m_dext : m_strength;
        int totalAttaque = jetAttaque + bonus;

        System.out.println(m_nom + " attaque " + monstreCible.getSpecie() +
                " (Jet: " + jetAttaque + " + Bonus: " + bonus + " = " + totalAttaque +
                " vs CA: " + monstreCible.getArmorClass() + ")");

        if (totalAttaque > monstreCible.getArmorClass()) {
            // Touche !
            int degats = m_weapon.retournerDmg();
            monstreCible.prendreDegats(degats);
            System.out.println(m_nom + " inflige " + degats + " dégâts à " + monstreCible.getSpecie());

            if (monstreCible.estMort()) {
                System.out.println(monstreCible.getSpecie() + " est mort !");
                caseCibleObj.setMonstre(null);
            }
            return true;
        } else {
            System.out.println("Attaque ratée !");
            return false;
        }
    }
    public void prendreDegats(int degats) {
        this.m_hp -= degats;
        if (this.m_hp < 0) {
            this.m_hp = 0;
        }
        System.out.println(m_nom + " a maintenant " + m_hp + "/" + m_max_hp + " PV");
    }

    public boolean estMort() {
        return m_hp <= 0;
    }


}