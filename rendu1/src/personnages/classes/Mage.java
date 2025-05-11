package personnages.classes;

import personnages.Personnage;

public class Mage extends Personnage {
    public Mage (String nom, String race)
    {
        super(nom, race);
        this.m_hp+=12;
    }
}

