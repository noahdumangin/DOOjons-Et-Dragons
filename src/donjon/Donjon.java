package donjon;
import personnages.*;
import monstres.*;
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
        System.out.print("   ");
        for (int x = 0; x < m_tailleX; x++) {
            System.out.print(x + "   ");
        }
        System.out.println();
        for (int y = 0; y < m_tailleY; y++) {
            System.out.print(y + " ");
            for (int x = 0; x < m_tailleX; x++) {
                System.out.print(m_plateau[x][y].toString() + " ");
            }
            System.out.println();
        }
        System.out.println();
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




}
