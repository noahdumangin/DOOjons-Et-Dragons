package items.Armors;

import items.Item;

public class Armor extends Item
{
    private int m_CA;

    public Armor(String nom, int ca)
    {
        super(nom);
        m_CA = ca;
    }
    public int getCA()
    {
        return m_CA;
    }
}
