package personnages.classes;

import personnages.Personnage;

public class Guerrier extends Personnage {
    public Guerrier (String nom, String race)
    {
        super(nom,race);
        this.m_hp+=20;
    }
}

