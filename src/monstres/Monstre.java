package monstres;

import entite.Entite;
import outils.Des;
import donjon.*;
import personnages.*;

import java.util.ArrayList;

public class Monstre implements Entite {

    private int m_x;
    private int m_y;

    private int m_num;
    private String m_specie;
    private int m_atk_reach;
    private Des m_dmg;
    private int m_hp;
    private int m_strength;
    private int m_dext;
    private int m_armor_class;
    private int m_init;
    private int m_speed;
    private int m_max_hp;


    public Monstre(int num, String specie, int atk_reach, Des dmg, int hp, int strength, int dext, int armor_class, int init, int speed)
    {
        this.m_armor_class=armor_class;
        this.m_atk_reach=atk_reach;
        this.m_dext=dext;
        this.m_num=num;
        this.m_specie=specie;
        this.m_dmg=dmg;
        this.m_strength=strength;
        this.m_init=init;
        this.m_hp=hp;
        this.m_speed=speed;
        this.m_max_hp=hp;
    }

    public String getSpecie()
    {
        return m_specie;
    }
    public int getSpeed()
    {
        return m_speed;
    }
    public int getInit()
    {
        return m_init;
    }
    public int getAtkReach()
    {
        return m_atk_reach;
    }
    public int getCA()
    {
        return m_armor_class;
    }
    public int getArmorClass() {
        return m_armor_class;
    }
    public boolean estMort() {
        return m_hp <= 0;
    }

    public void setPosition(int x, int y)
    {
        this.m_x=x;
        this.m_y=y;
    }
    @Override
    public void seDeplacer(int dest_x, int dest_y, Donjon donjon)
    {
        int distance= Math.abs(dest_x-m_x) + Math.abs(dest_y-m_y);
        System.out.println(distance);
        if(distance <= m_speed/3)
        {
            if (donjon.getCase(dest_x,dest_y).isLibre())
            {
                System.out.println(m_x +"-"+ m_y);
                donjon.getCase(m_x,m_y).setMonstre(null);
                this.m_x=dest_x;
                this.m_y=dest_y;
                donjon.getCase(dest_x,dest_y).setMonstre(this);
            }
        }
        else
        {
            System.out.println("distance trop élevée");
        }

    }

    @Override
    public void attaquer()
    {

    }

    public void prendreDegats(int degat)
    {
        this.m_hp -= degat;
    }


}

