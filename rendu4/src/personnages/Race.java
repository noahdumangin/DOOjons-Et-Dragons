package personnages;

public class Race {

    private String m_nom;
    private int m_hp;
    private int m_strength;
    private int m_dext;
    private int m_speed;
    private int m_init;

    public Race (String nom, int hp, int strength, int dext, int speed, int init)
    {
        this.m_hp=hp;
        this.m_strength=strength;
        this.m_dext=dext;
        this.m_init=init;
        this.m_speed=speed;
    }

    public String getNom()
    {
        return m_nom;
    }
    public int getHp()
    {
        return m_hp;
    }
    public int getStrength()
    {
        return m_strength;
    }
    public int getDext()
    {
        return m_dext;
    }
    public int getSpeed()
    {
        return m_speed;
    }
    public int getInit()
    {
        return m_init;
    }

}
