package sorts;
import personnages.*;
public class Guerison extends Sort{
    private String m_persochoisi;

    public void Lancer(Personnage PersoCible, Personnage PersoLanceur, int Soin)
    {
        if (!PersoCible.estMort())
        {
            if (PersoCible.getHp()!=PersoCible.getMaxHp())
            {
                PersoCible.addHP(Soin);
            }
            else
            {
                System.out.println("Le personnage est déjà en pleine forme");
            }
        }
        else
        {
            System.out.println("Vous ne pouvez pas ressuciter un personnage mort");
        }
    }

}
