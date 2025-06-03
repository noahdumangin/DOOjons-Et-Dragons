package items;

import java.util.ArrayList;

public class Armor extends Item{

    private int m_CA;

    public Armor(String nom, boolean heavy, int CA)
    {
        super(nom,heavy);
        this.m_CA=CA;
    }
    public int getCA()
    {
        return m_CA;
    }

}
