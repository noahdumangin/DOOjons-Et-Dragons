package items;

public class Item
{
    private String m_nom;

    public Item(String nom)
    {
        m_nom = nom;
    }

    public String getName()
    {
        return getClass().getSimpleName();
    }
}
