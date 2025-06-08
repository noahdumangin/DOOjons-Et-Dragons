package personnages;

import items.Item;
import systeme.actions.sorts.Sort;

import java.util.ArrayList;

public class Classes {
    private int m_hp;
    private ArrayList<Item> m_inventory;
    private String m_nom;
    private boolean peutLancerDesSorts;
    private ArrayList<Sort> m_sorts;

    public Classes (String nom, int hp, ArrayList<Item> inventory, ArrayList<Sort>Sorts)
    {
        this.m_hp=hp;
        this.m_inventory=inventory;
        this.m_nom=nom;
        this.m_sorts=Sorts;
    }

    public int getHp()
    {
        return m_hp;
    }
    public ArrayList<Item> getInventory ()
    {
        return this.m_inventory;
    }
    public String getNom()
    {
        return m_nom;
    }
    public ArrayList<Sort> getSorts() {
        return m_sorts;
    }

}
