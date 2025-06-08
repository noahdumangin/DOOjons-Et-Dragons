package monstres;

import outils.Des;
import entite.Entite;
import systeme.actions.Action;
import systeme.actions.ActionAttaquer;
import systeme.actions.ActionSeDeplacer;

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

    public Monstre(Monstre other) {
        this.m_num = other.m_num;
        this.m_specie = other.m_specie;
        this.m_atk_reach = other.m_atk_reach;
        this.m_dmg = new Des(other.m_dmg.getNbDes(), other.m_dmg.getTypeDes()); // bien créer un nouvel objet Des
        this.m_hp = other.m_hp;
        this.m_strength = other.m_strength;
        this.m_dext = other.m_dext;
        this.m_armor_class = other.m_armor_class;
        this.m_init = other.m_init;
        this.m_speed = other.m_speed;
        this.m_max_hp = other.m_max_hp;
    }


    @Override
    public ArrayList<Action> getActionDeBase() {
        ArrayList<Action> actions = new ArrayList<>();
        actions.add(new ActionAttaquer());
        actions.add(new ActionSeDeplacer());
        return actions;
    }

    @Override
    public int attaquer(Entite cible) {
        int distance = Math.abs(cible.getX() - this.getX()) + Math.abs(cible.getY() - this.getY());
        int portee = this.getAtk_reach();

        if (distance > portee) {
            return -1; // hors portée
        }

        Des d20 = new Des(1, 20);
        int buffAttaque = d20.genererRandom();
        int caractAtt = this.getCaractAtt(portee);
        int totalAttaque = buffAttaque + caractAtt;

        if (totalAttaque >= cible.getCA())
        {
            return this.getDmg();
        }
        else
        {
            return 0;
        }
    }

    @Override
    public TypeEntite getType() {
        return TypeEntite.MONSTRE;
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
    @Override
    public int getInit()
    {
        return m_init;
    }

    @Override
    public String getDescription() {
        return "("+afficherHP()+")";
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

    @Override
    public String  afficherHP()
    {
        return ("PV "+m_specie+ " : " +m_hp+"/"+m_max_hp);
    }

    @Override
    public boolean estMort() {
        return m_hp<=0;
    }

    public void setHp(int new_hp)
    {
        this.m_hp=new_hp;
    }
    @Override
    public boolean changeHp(int adding_hp)
    {
        if (adding_hp > 0)
        {
            if (m_hp == m_max_hp)
            {
                return false;
            }


            m_hp += adding_hp;
            if (m_hp > m_max_hp)
            {
                m_hp = m_max_hp;
            }
            return true;
        }

        if (adding_hp < 0) {
            m_hp += adding_hp;
            if (m_hp < 0)
            {
                m_hp = 0;
            }
            return true;
        }

        return false;
    }

    @Override
    public String toString()
    {
        return m_specie;
    }

}

//Limbes
/*public void seDeplacer(int dest_x, int dest_y, Donjon donjon)
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

}*/
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