package items;
import outils.Des;

public class Weapon extends Item{

    private Des m_dmg;
    private int m_atk_reach;
    private int m_dmg_bonus;

    public Weapon(String nom, boolean heavy, Des dmg, int atk_reach)
    {
        super(nom, heavy);
        this.m_atk_reach=atk_reach;
        this.m_dmg=dmg;
    }
    public int getAtk_reach()
    {
        return m_atk_reach;
    }
    public Des getDmg()
    {
        return m_dmg;
    }

    public int add_dmg()
    {
        m_dmg_bonus+=1;
        return m_dmg_bonus;
    }
    public int add_atk_reach()
    {
        m_atk_reach+=1;
        return m_atk_reach;
    }
}

