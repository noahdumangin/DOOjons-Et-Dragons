package personnages;

import items.Item;

import java.util.ArrayList;

public class Classes {
    private int m_hp;
    private ArrayList<Item> m_inventory;
    private String m_nom;

    public Classes (String nom, int hp, ArrayList<Item> inventory )
    {
        this.m_hp=hp;
        this.m_inventory=inventory;
        this.m_nom=nom;
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

}
