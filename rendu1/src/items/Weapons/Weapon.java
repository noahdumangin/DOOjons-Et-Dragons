package items.Weapons;

import items.Item;

public class Weapon extends Item
{
    private int[] m_dice = new int[2];
    private int m_range;

    public Weapon(String nom, int[] dice, int range)
    {
        super(nom);
        m_dice = dice;
        m_range = range;
    }

    

    public int[] getDice()
    {
        return m_dice;
    }
    public int getRange()
    {
        return m_range;
    }
}
