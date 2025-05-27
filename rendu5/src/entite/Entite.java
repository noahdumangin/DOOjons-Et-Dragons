package entite;

import donjon.Donjon;

public interface Entite
{
    void seDeplacer(int x_cible, int y_cible, Donjon donjon);

    void attaquer(int x_cible, int y_cible, Donjon donjon);

}
