package monstres;

import outils.Des;
import donjon.*;
import personnages.*;

import java.util.ArrayList;

public class Monstre {
    private int m_num;
    private String m_specie;
    private int m_atk_reach;
    private Des m_dmg;
    private int m_hp;
    private int m_strength;
    private int m_dext;
    private int m_armor_class;
    private int m_init;
    private int m_speed;
    private int m_max_hp;


    public Monstre(int num, String specie, int atk_reach, Des dmg, int hp, int strength, int dext, int armor_class, int init, int speed)
    {
        this.m_armor_class=armor_class;
        this.m_atk_reach=atk_reach;
        this.m_dext=dext;
        this.m_num=num;
        this.m_specie=specie;
        this.m_dmg=dmg;
        this.m_strength=strength;
        this.m_init=init;
        this.m_hp=hp;
        this.m_speed=speed;
        this.m_max_hp=hp;
    }

    public String getSpecie()
    {
        return m_specie;
    }
    public int getSpeed()
    {
        return m_speed;
    }
    public int getInit()
    {
        return m_init;
    }
    public int getAtkReach()
    {
        return m_atk_reach;
    }
    public int getCA()
    {
        return m_armor_class;
    }
    // Dans Monstre.java
    public boolean seDeplacer(Donjon donjon, String caseArrivee) {
        // Trouver la case actuelle du monstre
        int[] coordActuelles = trouverPositionDansDonjon(donjon);
        if (coordActuelles == null) return false;

        return donjon.deplacerMonstre(this, coordActuelles[0] + "," + coordActuelles[1], caseArrivee);
    }

    private int[] trouverPositionDansDonjon(Donjon donjon) {
        for (int x = 0; x < donjon.getTailleX(); x++) {
            for (int y = 0; y < donjon.getTailleY(); y++) {
                Case c = donjon.getCase(x, y);
                if (c != null && c.getMonstre() == this) {
                    return new int[]{x, y};
                }
            }
        }
        return null;
    }
    public int getArmorClass() {
        return m_armor_class;
    }



    public boolean estMort() {
        return m_hp <= 0;
    }

    public boolean attaquer(Donjon donjon, String caseCible) {
        int[] coordAttaquant = trouverPositionDansDonjon(donjon);
        int[] coordCible = donjon.convertirCaseEnCoordonnees(caseCible);

        if (coordAttaquant == null || coordCible == null) return false;

        // Vérifier la portée
        int distance = Math.abs(coordCible[0] - coordAttaquant[0]) +
                Math.abs(coordCible[1] - coordAttaquant[1]);

        if (distance > m_atk_reach) {
            System.out.println("Cible hors de portée !");
            return false;
        }

        Case caseCibleObj = donjon.getCase(coordCible[0], coordCible[1]);
        Personnage persoCible = caseCibleObj.getPersonnage();

        if (persoCible == null) {
            System.out.println("Aucun personnage à attaquer sur cette case !");
            return false;
        }

        // Jet d'attaque
        Des deAttaque = new Des(1, 20);
        int jetAttaque = deAttaque.genererRandom().get(0);
        int totalAttaque = jetAttaque + m_strength;

        // CA de base est 10 si pas d'armure
        int caCible = persoCible.getArmor() != null ? persoCible.getArmor().getCA() : 10;

        System.out.println(m_specie + " attaque " + persoCible.getNom() +
                " (Jet: " + jetAttaque + " + Bonus: " + m_strength + " = " + totalAttaque +
                " vs CA: " + caCible + ")");

        if (totalAttaque > caCible) {
            // Touche !
            ArrayList<Integer> degatsDes = m_dmg.genererRandom();
            int degats = degatsDes.stream().mapToInt(Integer::intValue).sum();
            persoCible.prendreDegats(degats);
            System.out.println(m_specie + " inflige " + degats + " dégâts à " + persoCible.getNom());

            if (persoCible.estMort()) {
                System.out.println(persoCible.getNom() + " est mort !");
                caseCibleObj.setPersonnage(null);
            }
            return true;
        } else {
            System.out.println("Attaque ratée !");
            return false;
        }
    }
    public void prendreDegats(int degats) {
        this.m_hp -= degats;
        if (this.m_hp < 0) {
            this.m_hp = 0;
        }
        System.out.println(m_specie + " #" + m_num + " a maintenant " + m_hp + " PV");
    }



}

