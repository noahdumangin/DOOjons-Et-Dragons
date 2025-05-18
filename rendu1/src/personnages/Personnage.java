package personnages;
import items.*;

import java.util.ArrayList;
import outils.Des;
import donjon.*;
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
    public void seDeplacer()
    {

    }
}