package items;
import outils.Des;

import java.util.ArrayList;

public class Weapon extends Item{

    private Des m_dmg;
    private int m_atk_reach;

    public Weapon(String nom, boolean heavy, Des dmg, int atk_reach)
    {
        super(nom, heavy);
        this.m_atk_reach=atk_reach;
        this.m_dmg=dmg;
    }
    public int retournerDmg() {
        int somme = 0;
        ArrayList<Integer> Result = m_dmg.genererRandom();
        for (int i = 0; i < Result.size(); i++) {
            somme += Result.get(i);
        }
        return somme;
    }
    public int getAtk_reach()
    {
        return m_atk_reach;
    }

    public int add_dmg(Weapon w) {
        int damage = retournerDmg();
        damage += 1;
        return damage;
    }

    public int add_atk_reach(Weapon w) {
        int attaque = getAtk_reach();
        attaque += 1;
        return attaque;
    }
}

