package Items.Armors;

import Items.Item;

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
