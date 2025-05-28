package donjon;

import personnages.*;
import items.*;
import monstres.*;

public class Case {
    private int m_x;
    private int m_y;
    private Personnage personnage;
    private Item item;
    private Monstre monstre;
    public Boolean estObstacle=false;

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
        if(getPersonnage()==null && getMonstre() == null && !estObstacle)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    @Override
    public String toString() {
        if (personnage != null)
        {
            if (personnage.getNom().length() >= 3)
            {
                return personnage.getNom().substring(0,3);

            }
            else
            {
                return String.format("%-3s", personnage.getNom());
            }
        }
        if (estObstacle)
        {
            return "[ ]";
        }
        if (item != null)
        {
            return " * ";
        }
        if (monstre!=null)
        {
            if (monstre.getSpecie().length() >= 3)
            {
                return monstre.getSpecie().substring(0,3).toString();

            }
            else
            {
                return String.format("%-3s", monstre.getSpecie());
            }
        }
        return " . ";
    }
}
