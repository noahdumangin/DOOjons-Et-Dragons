package monstres;

import outils.Des;
import donjon.*;
import entite.Entite;
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



    @Override
    public int getAtk_reach() {
        return m_atk_reach;
    }

    public int getInit()
    {
        return m_init;
    }

    @Override
    public int getCA()
    {
        return m_armor_class;
    }
    public int getDext()
    {
        return m_dext;
    }
    public int getStrength()
    {
        return m_strength;
    }
    @Override
    public int getDmg()
    {
        return m_dmg.genererRandom();
    }

    @Override
    public int getCaractAtt(int portee) {
        if(m_atk_reach>1)
        {
            return m_dext;
        }
        else
        {
            return m_strength;
        }
    }

    public int getArmorClass() {
        return m_armor_class;
    }


    @Override
    public void setPosition(int x, int y)
    {
        this.m_x=x;
        this.m_y=y;
    }
    @Override
    public int getX()
    {
        return this.m_x;
    }
    @Override
    public int getY()
    {
        return this.m_y;
    }

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
    public String  afficherHP()
    {
        return ("PV "+m_specie+ " : " +m_hp+"/"+m_max_hp);
    }

    @Override
    public boolean estMort() {
        if(m_hp>0)
        {
            return false;
        }
        return true;
    }

    public void setHp(int new_hp)
    {
        this.m_hp=new_hp;
    }
    @Override
    public int changeHp(int new_hp)
    {
        return this.m_hp+=new_hp;
    }

    @Override
    public String toString()
    {
        return m_specie;
    }

}

//Limbes

/*public void attaquer(int x_cible, int y_cible, Donjon donjon) {
        Des attaque = new Des(1, 20);
        int buff_attaque = attaque.genererRandom();
        int distance = Math.abs(x_cible - m_x) + Math.abs(y_cible - m_y);

        Personnage cible = donjon.getCase(x_cible, y_cible).getPersonnage();

        if (cible == null) {
            System.out.println("Il n'y a pas de joueur sur cette case !");
            return;
        }

        if (distance > m_atk_reach) {
            System.out.println("La cible est trop éloignée !");
            return;
        }

        int caract_attaque;
        if(m_atk_reach >1)
        {
            caract_attaque=m_dext;
        }
        else
        {
            caract_attaque=m_strength;
        }

        if (m_atk_reach > 1)
        {
            m_strength = 0;
        }
        else
        {
            m_dext = 0;
        }

        int total_attaque = caract_attaque + buff_attaque;

        if (total_attaque >= cible.getCA()) {
            int degats = m_dmg.genererRandom();
            System.out.println(m_specie + " attaque " + cible.getNom() + " et inflige " + degats + " dégâts !");
            cible.changeHp(-degats, donjon);
            cible.afficherHP();
        }
        else
        {
            System.out.println("L'attaque a échoué ! La classe d'armure de " + cible.getNom() + " était trop élevée");
        }
    }*/