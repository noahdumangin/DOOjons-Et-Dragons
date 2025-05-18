package donjon;

import personnages.*;
import items.*;
import monstres.*;

public class Case {
    private int x;
    private int y;
    private Personnage personnage;
    private Item item;
    private Monstre monstre;
    public Boolean estObstacle=false;

    public Case(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // Getters et Setters
    public int getX()
    {
        return x;
    }
    public int getY()
    {
        return y;
    }
    public Personnage getPersonnage()
    {
        return personnage;
    }
    public void setPersonnage(Personnage p)
    {
        this.personnage = p;
    }

    public Monstre getMonstre()
    {
        return monstre;
    }
    public void setMonstre(Monstre m)
    {
        this.monstre=m;
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
        return personnage == null && !estObstacle;
    }

    @Override
    public String toString() {
        if (personnage != null)
        {
            return personnage.getNom().substring(0,3);
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
            return monstre.getSpecie().substring(0,3);
        }
        return " . ";
    }
}
