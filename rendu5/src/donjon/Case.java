package donjon;
import entite.Entite;
import personnages.*;
import items.*;
import monstres.*;

public class Case {
    private int m_x;
    private int m_y;
    private Personnage personnage;
    private Item item;
    private Monstre monstre;
    private Boolean estObstacle=false;

    private Entite entite;

    public Case(int x, int y) {
        this.m_x = x;
        this.m_y = y;

    }

    // Getters et Setters
    public int getM_x()
    {
        return m_x;
    }
    public int getM_y()
    {
        return m_y;
    }
    public Personnage getPersonnage()
    {
        return personnage;
    }
    public void setPersonnage(Personnage p)
    {
        this.personnage = p;
        if(this.personnage !=null)
        {
            this.personnage.setPosition(m_x,m_y);
        }
    }

    public Monstre getMonstre()
    {
        return monstre;
    }
    public void setMonstre(Monstre m)
    {
        this.monstre=m;
        if(this.monstre!=null)
        {
            this.monstre.setPosition(m_x,m_y);
        }
    }

    public Entite getEntite()
    {
        return entite;
    }

    public Entite setEntite (Entite e)
    {
        return this.entite=e;
    }

    public Item getItem()
    {
        return item;
    }
    public void setItem(Item i)
    {
        this.item = i;
    }

    public boolean isLibre()
    {
        return getPersonnage()==null && getMonstre() == null && !estObstacle;

    }

    public boolean estObstacle()
    {
        return estObstacle;
    }

    @Override
    public String toString() {

        if (estObstacle)
        {
            return "[ ]";
        }
        if (item != null)
        {
            return " * ";
        }
        
        if (entite != null)
        {
            if(entite.toString().length() >=3)
            {
                return entite.toString().substring(0,3).toString();
            }
            else
            {
                return String.format("%-3s", entite.toString());
            }
        }
        return " . ";
    }
}
