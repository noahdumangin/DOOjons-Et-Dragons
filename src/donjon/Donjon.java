package donjon;
import personnages.Personnage;
import monstres.Monstre;
public class Donjon {
    private Case[][] m_plateau;
    private int m_tailleX;
    private int m_tailleY;


    //Création des cases
    public Donjon(int tailleX, int tailleY) {
        this.m_tailleX = tailleX;
        this.m_tailleY = tailleY;
        m_plateau = new Case[tailleX][tailleY];

        for (int x = 0; x < tailleX; x++) {
            for (int y = 0; y < tailleY; y++) {
                m_plateau[x][y] = new Case(x, y);
            }
        }
    }

    public void afficher() {
        for (int y = 0; y < m_tailleY; y++) {
            for (int x = 0; x < m_tailleX; x++) {
                System.out.print(m_plateau[x][y].toString());
            }
            System.out.println();
        }
        System.out.println("\n");
    }

    public Case getCase(int x, int y) {
        if (x >= 0 && x < m_tailleX && y >= 0 && y < m_tailleY)
            return m_plateau[x][y];
        else
            return null;
    }

    public int getTailleX() {
        return m_tailleX;
    }

    public int getTailleY() {
        return m_tailleY;
    }

    public void echangerCases(int x1, int y1, int x2, int y2)
    {
        Case case1 = getCase(x1, y1);
        Case case2 = getCase(x2, y2);

        if (case1 == null || case2 == null) {
            System.out.println("Coordonnées invalides.");
            return;
        }

        Personnage tempPerso = case1.getPersonnage();
        case1.setPersonnage(case2.getPersonnage());
        case2.setPersonnage(tempPerso);


        Monstre tempMonstre = case1.getMonstre();
        case1.setMonstre(case2.getMonstre());
        case2.setMonstre(tempMonstre);
    }
    public void setCase(int x, int y, Case newCase)
    {
        if (x >= 0 && x < m_tailleX && y >= 0 && y < m_tailleY)
        {
            this.m_plateau[x][y] = newCase;
        }
    }



}
