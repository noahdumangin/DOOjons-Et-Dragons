package entite;

import donjon.Case;
import donjon.Donjon;

public interface Entite {

    public void seDeplacer(int dest_x, int dest_y, Donjon donjon);

    public void attaquer();

}
