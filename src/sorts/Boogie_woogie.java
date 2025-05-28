package sorts;

import personnages.Personnage;
import donjon.*;

public class Boogie_woogie extends Sort {

    private Donjon donjon;

    public Boogie_woogie(Donjon donjon) {
        this.donjon = donjon;
    }

    public void Lancer(Personnage PersoChoisi1, Personnage PersoChoisi2, Donjon donjon) {
        int x1 = PersoChoisi1.getX();
        int y1 = PersoChoisi1.getY();

        int x2 = PersoChoisi2.getX();
        int y2 = PersoChoisi2.getY();

        donjon.echangerCases(x1, y1, x2, y2);

        System.out.println("Le personnage " + PersoChoisi1.getNom() + " et le personnage " +
                PersoChoisi2.getNom() + " ont été échangés de places!\n");
    }
}
