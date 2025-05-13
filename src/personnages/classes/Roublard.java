package personnages.classes;

import personnages.Personnage;

public class Roublard extends Personnage {
    public Roublard (String nom, String race)
    {
        super(nom,race);
        this.m_hp+=16;
    }
}

