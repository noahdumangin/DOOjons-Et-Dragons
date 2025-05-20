package donjon;
import personnages.*;
import monstres.*;
public class Donjon {
    private Case[][] m_plateau;
    private int m_tailleX;
    private int m_tailleY;

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
                System.out.print(m_plateau[x][y].toString() + "");
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


    public int[] convertirCaseEnCoordonnees(String caseStr) {
        if (caseStr == null || caseStr.length() < 2) {
            return null;
        }

        char colonneChar = caseStr.charAt(0);
        int ligne = Integer.parseInt(caseStr.substring(1)) - 1; // On soustrait 1 pour passer à l'index 0

        int x = colonneChar - 'A'; // Convertir la lettre en index (A=0, B=1, ...)
        int y = ligne; // La ligne est déjà en index 0

        return new int[]{x, y};
    }

    public boolean deplacerPersonnage(Personnage p, String caseDepart, String caseArrivee) {
        int[] coordDepart = convertirCaseEnCoordonnees(caseDepart);
        int[] coordArrivee = convertirCaseEnCoordonnees(caseArrivee);

        if (coordDepart == null || coordArrivee == null) {
            System.out.println("Case invalide !");
            return false;
        }

        int xDepart = coordDepart[0];
        int yDepart = coordDepart[1];
        int xArrivee = coordArrivee[0];
        int yArrivee = coordArrivee[1];

        Case depart = getCase(xDepart, yDepart);
        Case arrivee = getCase(xArrivee, yArrivee);
        int portee =(xArrivee - xDepart) +(yArrivee - yDepart);

        if (arrivee != null && arrivee.isLibre() && arrivee != depart) {
            if (p.getSpeed() / 3 >= portee) {
                arrivee.setPersonnage(p);
                depart.setPersonnage(null);
                return true;
            }
            else
            {
                System.out.println("Cible trop éloignée !");
                return false;
            }
        }
        else
        {
            System.out.println("Déplacement impossible !");
            return false;
        }
    }

    public boolean deplacerMonstre(Monstre m, String caseDepart, String caseArrivee) {
        int[] coordDepart = convertirCaseEnCoordonnees(caseDepart);
        int[] coordArrivee = convertirCaseEnCoordonnees(caseArrivee);

        if (coordDepart == null || coordArrivee == null) {
            System.out.println("Case invalide !");
            return false;
        }

        int xDepart = coordDepart[0];
        int yDepart = coordDepart[1];
        int xArrivee = coordArrivee[0];
        int yArrivee = coordArrivee[1];

        Case depart = getCase(xDepart, yDepart);
        Case arrivee = getCase(xArrivee, yArrivee);
        int portee =(xArrivee - xDepart) +(yArrivee - yDepart);

        if (arrivee != null && arrivee.isLibre() && arrivee != depart) {
            if (m.getSpeed() / 3 >= portee) {
                arrivee.setMonstre(m);
                depart.setMonstre(null);
                return true;
            }
            else
            {
                System.out.println("Cible trop éloignée !");
                return false;
            }
        }
        else
        {
            System.out.println("Déplacement impossible !");
            return false;
        }
    }
    public int getTailleX() {
        return m_tailleX;
    }

    public int getTailleY() {
        return m_tailleY;
    }




}
