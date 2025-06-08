package items;
import outils.Des;

public class Weapon extends Item{

    private Des m_dmg;
    private int m_atk_reach;
    private int m_bonusDegats = 0;


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
    public int getDmg()
    {
        return m_dmg.genererRandom() + m_bonusDegats;
    }

    @Override
    public TypeItem getType() {
        return TypeItem.ARME;
    }

    public void ameliorer() {
        m_dmg = new Des(m_dmg.getNbDes() + 1, m_dmg.getTypeDes());
        m_bonusDegats +=1;
    }

    public int getNbDes() { return m_dmg.getNbDes(); }
    public int getTypeDes() { return m_dmg.getTypeDes(); }
    public int getBonusDegats() { return m_bonusDegats; }
}