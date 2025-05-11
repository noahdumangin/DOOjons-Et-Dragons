package personnages.classes;

import personnages.Personnage;

public class Clerc extends Personnage {
    public Clerc (String nom, String race)
    {
        super(nom,race);
        this.m_hp+=16;
    }
}

