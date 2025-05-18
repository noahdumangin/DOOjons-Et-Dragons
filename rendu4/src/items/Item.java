package items;

public class Item {
    protected String m_nom;
    protected boolean m_heavy;

    public Item(String nom, boolean heavy)
    {
        this.m_nom=nom;
        this.m_heavy=heavy;
    }
    public String getNom()
    {
        return m_nom;
    }
}
