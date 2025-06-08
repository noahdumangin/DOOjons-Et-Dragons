package donjon;

import entite.Entite;

public class Donjon {
    private Case[][] m_plateau;
    private int m_tailleX;
    private int m_tailleY;

    // Création des cases
    public Donjon(int tailleX, int tailleY) {
        this.m_tailleX = tailleX;
        this.m_tailleY = tailleY;

        // On crée un plateau avec une ligne en plus pour la case invisible
        m_plateau = new Case[tailleX][tailleY + 1];

        for (int x = 0; x < tailleX; x++) {
            for (int y = 0; y < tailleY; y++) {
                m_plateau[x][y] = new Case(x, y);
            }
        }

        // On ajoute la case invisible à une coordonnée spécifique (ex: (0, m_tailleY))
        m_plateau[0][m_tailleY] = new Case(0, m_tailleY);
    }

    public void afficher() {
        System.out.print("   ");
        for (int x = 0; x < m_tailleX; x++) {
            System.out.print(x + "   ");
        }
        System.out.println();
        for (int y = 0; y < m_tailleY; y++) {  // On boucle uniquement jusqu'à m_tailleY
            System.out.print(y + " ");
            for (int x = 0; x < m_tailleX; x++) {
                System.out.print(m_plateau[x][y].toString() + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public Case getCase(int x, int y) {
        if (x >= 0 && x < m_tailleX && y >= 0 && y <= m_tailleY)  // <= car case invisible à m_tailleY possible
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

    public void setCase(int x, int y, Case newCase) {
        if (x >= 0 && x < m_tailleX && y >= 0 && y <= m_tailleY) {
            this.m_plateau[x][y] = newCase;
        }
    }

    public Case getCaseInvisible() {
        return m_plateau[0][m_tailleY];
    }
}
