package entite;

import systeme.actions.Action;

import java.util.ArrayList;

public interface Entite
{
        int getX();
        int getY();
        void setPosition(int x, int y);
        String toString();

        TypeEntite getType();

        int getSpeed();
        int getAtk_reach();
        int getDmg();
        int getCaractAtt(int reach);
        int getCA();
        boolean changeHp(int hp);
        String afficherHP();
        boolean estMort();
        int getInit();
        String getDescription();
        ArrayList<Action> getActionDeBase();
        public int attaquer(Entite cible);

        public enum TypeEntite
        {
                PERSONNAGE,
                MONSTRE
        }



}
