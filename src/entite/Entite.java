package entite;

import systeme.actions.Action;

import java.util.ArrayList;

public interface Entite
{
    /*void seDeplacer(int x_cible, int y_cible, Donjon donjon);

    void attaquer(int x_cible, int y_cible, Donjon donjon);*/


        int getX();
        int getY();
        void setPosition(int x, int y);
        String toString();
        int getSpeed();
        int getAtk_reach();
        int getDmg();
        int getCaractAtt(int reach);
        int getCA();
        int changeHp(int hp);
        String afficherHP();
        boolean estMort();
        int getInit();
        String getDescription();
        ArrayList<Action> getAction();



}
