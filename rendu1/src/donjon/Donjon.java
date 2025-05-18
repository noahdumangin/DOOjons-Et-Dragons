package donjon;

public class Donjon {
    private Case[][] plateau;
    private int tailleX;
    private int tailleY;

    public Donjon(int tailleX, int tailleY) {
        this.tailleX = tailleX;
        this.tailleY = tailleY;
        plateau = new Case[tailleX][tailleY];

        for (int x = 0; x < tailleX; x++) {
            for (int y = 0; y < tailleY; y++) {
                plateau[x][y] = new Case(x, y);
            }
        }
    }

    public void afficher() {
        for (int y = 0; y < tailleY; y++) {
            for (int x = 0; x < tailleX; x++) {
                System.out.print(plateau[x][y].toString() + "");
            }
            System.out.println();
        }
    }

    public Case getCase(int x, int y) {
        if (x >= 0 && x < tailleX && y >= 0 && y < tailleY)
            return plateau[x][y];
        else
            return null;
    }
}
