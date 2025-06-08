package personnages;

import donjon.Case;
import donjon.Donjon;
import entite.Entite;
import monstres.Monstre;
import outils.Des;

public class GameMaster
{
    /*private String m_nom;

    public GameMaster(String nom)
    {
        m_nom = nom;
    }

    public void degatMonstre(Monstre cible, Des des)
    {
        cible.prendreDegats(des.sommeDes());
    }

    public void degatPerso(Personnage cible, Des des)
    {
        cible.prendreDegats(des.sommeDes());
    }

    public Donjon deplacerPerso(Donjon plateau, Personnage cible, int x, int y)
    {
        if (plateau.getCase(x,y).isLibre())
        {
            plateau.getCase(x,y).setPersonnage(cible);
        }
        else
        {
            System.out.println("téléportation impossible case occupé");
        }
        return plateau;
    }

    public void deplacerMonstre(Donjon plateau, Monstre cible, int x, int y)
    {
        if (plateau.getCase(x,y).isLibre())
        {
            plateau.getCase(x,y).setMonstre(cible);
        }
        else
        {
            System.out.println("téléportation impossible case occupé");
        }
    }

    public void changeCase(Donjon plateau, Case newCase,int x, int y)
    {
        plateau.setCase(x,y,newCase);
    }*/
}
