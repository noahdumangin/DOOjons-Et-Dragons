package personnages;
import donjon.Donjon;
import items.*;
import entite.Entite;
import java.util.ArrayList;

import outils.Des;

public class Personnage implements Entite {
    private String m_nom;

    private int m_x;
    private int m_y;

    private boolean estMort;

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
        ArrayList<Integer> BuffDes = Buffer.genererListeRandom();
        this.m_nom=nom;
        this.m_hp=race.getHp()+classes.getHp();
        this.m_strength=race.getStrength()+BuffDes.get(0);
        this.m_dext=race.getDext()+BuffDes.get(1);
        this.m_speed=race.getSpeed()+BuffDes.get(2);
        this.m_init=race.getInit()+BuffDes.get(3);
        this.m_inventory=classes.getInventory();
        this.m_max_hp=m_hp;
        this.estMort=false;
        this.m_x=-1;
        this.m_y=-1;


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

    public void afficherInventaire()
    {
        for(int i=0;i<m_inventory.size();i++)
        {
            System.out.println(m_inventory.get(i).getNom());
        }
    }

    public int getCA() {
        // CA de base = 1
        int baseCA = 1;

        // Si une armure est équipée, on ajoute son bonus
        if (m_armor != null) {
            return baseCA + m_armor.getCA();
        }

        return baseCA;
    }


    public int getMaxHp() {
        return m_max_hp;
    }

    /*public void poser(int x, int y)
    {
        m_x=x;
        m_y=y;
    }*/
    public void setPosition(int x, int y)
    {
        this.m_x=x;
        this.m_y=y;
    }
    public void seDeplacer(int dest_x, int dest_y, Donjon donjon)
    {
        int distance= Math.abs(dest_x-m_x) + Math.abs(dest_y-m_y);
        if(distance <= m_speed*999)
        {
            if (donjon.getCase(dest_x,dest_y).isLibre())
            {
                //System.out.println(m_x +"-"+ m_y);
                donjon.getCase(m_x,m_y).setPersonnage(null);
                this.m_x=dest_x;
                this.m_y=dest_y;
                donjon.getCase(dest_x,dest_y).setPersonnage(this);
            }
        }
        else
        {
            System.out.println("distance trop élevée");
        }

    }

    public void attaquer(int x_cible, int y_cible, Donjon donjon)
    {

        Des attaque = new Des(1,20);
        if (this.m_weapon!=null)
        {

            int distance= Math.abs(x_cible-m_x) + Math.abs(y_cible-m_y);
            if(distance<=m_weapon.getAtk_reach())
            {
                if(donjon.getCase(x_cible,y_cible).getMonstre()!=null)
                {
                    //System.out.println(m_nom +" attaque "+donjon.getCase(x_cible,y_cible).getMonstre().getSpecie());
                    int buff_attaque=attaque.genererRandom();

                    if(m_weapon.getAtk_reach()>1)
                    {
                        if(m_dext+buff_attaque>=donjon.getCase(x_cible,y_cible).getMonstre().getCA())
                        {
                            int degats_infliges = m_weapon.getDmg().genererRandom();
                            System.out.println(this.m_nom+" attaque "+donjon.getCase(x_cible,y_cible).getMonstre().getSpecie()+" avec " +m_weapon.getNom() + " et inflige "+degats_infliges +" dégats !");
                            donjon.getCase(x_cible,y_cible).getMonstre().changeHp(-degats_infliges,donjon);
                        }
                        else
                        {
                            System.out.println("L'attaque à échoué ! La classe d'armure de "+donjon.getCase(x_cible,y_cible).getMonstre().getSpecie()+" est trop grande !");
                        }
                    }

                    if(m_weapon.getAtk_reach()==1)
                    {
                        if(m_strength+buff_attaque>=donjon.getCase(x_cible,y_cible).getMonstre().getCA())
                        {
                            System.out.println(this.m_nom+" attaque "+donjon.getCase(x_cible,y_cible).getMonstre().getSpecie()+" avec " +m_weapon.getNom() + " et inflige " +m_weapon.getDmg().genererRandom()+" dégats !");
                            donjon.getCase(x_cible,y_cible).getMonstre().afficherHP();
                        }
                        else
                        {
                            System.out.println("L'attaque à échoué ! La classe d'armure de "+donjon.getCase(x_cible,y_cible).getMonstre().getSpecie()+" est trop grande !");
                        }
                    }
                }
                else
                {
                    System.out.println("Il n'y a pas de monstres sur cette case");
                }
            }
            else
            {
                System.out.println("La cible est trop éloignée");
            }
        }
        else
        {
            System.out.println("Pas d'arme équipée");
        }
    }
    public void recupItem(Donjon donjon)
    {
        if(donjon.getCase(m_x,m_y).getItem()!=null)
        {
            m_inventory.add(donjon.getCase(m_x,m_y).getItem());
            donjon.getCase(m_x,m_y).setItem(null);
        }
    }

    public void afficherHP()
    {
        System.out.println("PV "+m_nom+" : "+m_hp+"/"+m_max_hp);
    }

    public void setHp(int new_hp)
    {
        this.m_hp=new_hp;
    }

    public void changeHp(int new_hp, Donjon donjon)
    {
        this.m_hp+=new_hp;
        if(m_hp<=0)
        {
            meurt(donjon);
        }
    }

    public void meurt(Donjon donjon)
    {
        System.out.println(m_nom +" est mort :(");
        donjon.getCase(m_x,m_y).setPersonnage(null);
    }

    public int getX(){
        return m_x;
    }

    public int getY(){
        return m_y;
    }


}