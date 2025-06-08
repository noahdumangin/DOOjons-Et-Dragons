package personnages;
import items.*;
import entite.Entite;

import java.util.ArrayList;
import outils.Des;
import systeme.actions.sorts.Sort;
import systeme.actions.*;
import systeme.*;
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
    private Classes m_classes;
    private Race m_race;


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
        this.m_weapon=null;
        this.m_armor=null;
        this.m_classes=classes;
        this.m_race=race;


    }
    public void afficherStats() {
        System.out.println("Nom : " + m_nom);
        System.out.println("PV : " + m_hp +"/"+m_max_hp);
        System.out.println("Force : " + m_strength);
        System.out.println("Dexterité : " + m_dext);
        System.out.println("Vitesse : " + m_speed);
        System.out.println("Initiative : " + m_init);
    }

    @Override
    public ArrayList<Action> getActionDeBase()
    {
        ArrayList<Action> actions = new ArrayList<>();
        actions.add(new ActionAttaquer());
        actions.add(new ActionSeDeplacer());
        if(m_inventory!=null)
        {
            actions.add((new ActionEquiper()));
        }
        if(!m_classes.getSorts().isEmpty())
        {
            actions.add(new ActionSorts());
        }
        return actions;

    }

    @Override
    public int attaquer(Entite cible) {
        int distance = Math.abs(cible.getX() - this.getX()) + Math.abs(cible.getY() - this.getY());
        int portee = this.getAtk_reach();

        if (distance > portee)
        {
            return -1; // hors portée
        }

        Des d20 = new Des(1, 20);
        int buffAttaque = d20.genererRandom();
        int caractAtt = this.getCaractAtt(portee);
        int totalAttaque = buffAttaque + caractAtt;

        if (totalAttaque >= cible.getCA()) {
            return this.getDmg();
        }
        else
        {
            return 0;
        }
    }


    public ArrayList<Sort> getSorts()
    {
        return m_classes.getSorts();
    }

    @Override
    public TypeEntite getType() {
        return TypeEntite.PERSONNAGE;
    }

    public void ajouterAction()
    {

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
    @Override
    public int getSpeed()
    {
        return m_speed;
    }
    @Override
    public int getInit()
    {
        return m_init;
    }

    @Override
    public String getDescription() {
        return "("+this.m_race.getNom() + " " + this.m_classes.getNom()+ " "+this.afficherHP()+")";
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
            Item item_afficher = m_inventory.get(i);
            System.out.println(i+1+"."+item_afficher.toString()+"("+item_afficher.getType()+")");
        }
    }

    @Override
    public int getCA() {
        // CA de base = 1
        int baseCA = 1;

        // Si une armure est équipée, on ajoute son bonus
        if (m_armor != null) {
            return baseCA + m_armor.getCA();
        }

        return baseCA;
    }
    @Override
    public int getAtk_reach()
    {
        int base_atk_reach=1;

        if (m_weapon!=null)
        {
            return base_atk_reach+ m_weapon.getAtk_reach();
        }
        return base_atk_reach;

    }

    public ArrayList<Weapon> listeArmes()
    {
        ArrayList<Weapon> malisteArmes = new ArrayList<>();
        if(m_weapon!=null)
        {
            malisteArmes.add(m_weapon);
        }
        for(int i=0;i<getInventory().size();i++)
        {
            Item item = getInventory().get(i);
            if(item.getType()== Item.TypeItem.ARME)
            {
                Weapon nouvelleArme = (Weapon) item;
                malisteArmes.add(nouvelleArme);
            }
        }
        return malisteArmes;
    }
    @Override
    public int getDmg() {
        if(m_weapon==null)
        {
            return 0;
        }
        else
        {
            return m_weapon.getDmg();
        }
    }

    @Override
    public int getCaractAtt(int portee) {
        if(m_weapon==null)
        {
            return 0;
        }
        if (portee>1)
        {
            return m_dext;
        }
        else
        {
            return m_strength;
        }
    }


    public int getMaxHp() {
        return m_max_hp;
    }

    public void addHP(int Soin){
        m_hp+=Soin;
    }
    public String getNom()
    {
        String nom = m_nom;
        return nom;
    }
    /*public boolean peutLancerSort(Sort sort) {
        if (classe == Classes.MAGICIEN) return true;
        if (classe == Classes.CLERC && sort instanceof Guerison) return true;
        return false;
    }*/

    @Override
    public int getX()
    {
        return m_x;
    }

    @Override
    public int getY()
    {
        return m_y;
    }


    @Override
    public void setPosition(int x, int y)
    {
        this.m_x=x;
        this.m_y=y;
    }

    @Override
    public String afficherHP()
    {
        return ("PV "+m_nom+" : "+m_hp+"/"+m_max_hp);
    }

    @Override
    public boolean estMort()
    {
        return m_hp <= 0;
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
        return m_nom;
    }

    public boolean equiper(Item item) //méthode équiper partagé entre Weapon et Armor (accessoirement un crime de guerre pour LeQuentrec
    {
        if(item.getType()== Item.TypeItem.ARME)
        {
            Weapon nouvelleArme = (Weapon) item;
            if(m_weapon!=null)
            {
                m_inventory.add(m_weapon);
            }
            m_weapon=nouvelleArme;
            m_inventory.remove(nouvelleArme);
            System.out.println(nouvelleArme+" équipé avec succès");
            return true;
        }
        if(item.getType()== Item.TypeItem.ARMURE)
        {
            Armor nouvelleArmure = (Armor) item;
            if(m_armor!=null)
            {
                m_inventory.add(m_armor);
            }

            m_armor=nouvelleArmure;
            m_inventory.remove(nouvelleArmure);
            System.out.println(nouvelleArmure+" équipé avec succès");
            return true;
        }
        System.out.println("Le type de l'item n'est pas identifié");
        return false;
    }

    public void addItemInventory(Item item)
    {
        m_inventory.add(item);
    }

    /*public void ameliorerArme(Weapon weapon)
    {
        m_weapon.
    }*/




}





//Limbes
/*public void seDeplacer(int dest_x, int dest_y, Donjon donjon)
    {
        int distance= Math.abs(dest_x-m_x) + Math.abs(dest_y-m_y);
        if(distance <= m_speed*999)//remplacé *999 par /3 plus tard, c'est juste pour simplifier les déplacements
        {
            if (donjon.getCase(dest_x,dest_y).isLibre())
            {
                //System.out.println(m_x +"-"+ m_y);
                donjon.getCase(m_x,m_y).setPersonnage(null);
                this.m_x=dest_x;
                this.m_y=dest_y;
                donjon.getCase(dest_x,dest_y).setPersonnage(this);
            }
            System.out.println("La case est occupée");
        }
        else
        {
            System.out.println("distance trop élevée");
        }

    }

    public void attaquer(int x_cible, int y_cible, Donjon donjon) {
        Des attaque = new Des(1, 20);
        int buff_attaque = attaque.genererRandom();
        int distance = Math.abs(x_cible - m_x) + Math.abs(y_cible - m_y);

        Monstre cible = donjon.getCase(x_cible, y_cible).getMonstre();

        if (cible == null) {
            System.out.println("Il n'y a pas de monstre sur cette case !");
            return;
        }

        if (distance > m_weapon.getAtk_reach()) {
            System.out.println("La cible est trop éloignée !");
            return;
        }

        int caract_attaque;
        if(m_weapon.getAtk_reach() >1)
        {
            caract_attaque=m_dext;
        }
        else
        {
            caract_attaque=m_strength;
        }

        int total_attaque = caract_attaque + buff_attaque;

        if (total_attaque >= cible.getCA()) {
            int degats = m_weapon.getDmg().genererRandom();
            System.out.println(m_nom + " attaque " + cible.getSpecie() + " et inflige " + degats + " dégâts !");
            cible.changeHp(-degats);
            cible.afficherHP();
        }
        else
        {
            System.out.println("L'attaque a échoué ! La classe d'armure de " + cible.getSpecie() + " était trop élevée");
        }
    }

    public void recupItem(Donjon donjon)
    {
        if(donjon.getCase(m_x,m_y).getItem()!=null)
        {
            m_inventory.add(donjon.getCase(m_x,m_y).getItem());
            donjon.getCase(m_x,m_y).setItem(null);
        }
    }*/



/*public void poser(int x, int y)
        {
            m_x=x;
            m_y=y;
        }*/