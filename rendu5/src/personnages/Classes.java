package personnages;

import items.Item;

import java.util.ArrayList;

public class Classes {
    private int m_hp;
    private ArrayList<Item> m_inventory;

    public Classes (int hp, ArrayList<Item> inventory)
    {
        this.m_hp=hp;
        this.m_inventory=inventory;
    }

    public int getHp()
    {
        return m_hp;
    }
    public ArrayList<Item> getInventory ()
    {
        return this.m_inventory;
    }
}
