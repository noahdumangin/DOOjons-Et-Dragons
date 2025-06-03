package systeme;
import donjon.*;
import entite.Entite;
import outils.Des;

import java.util.ArrayList;
public class GestionnaireDonjon {

    private Donjon donjon;

    private ArrayList<Entite> listeEntite;

    Affichage affichage= new Affichage();

    public GestionnaireDonjon(Donjon donjon)
    {
        this.donjon=donjon;
        this.listeEntite = new ArrayList<>();
    }

    public void ajouterEntite(Entite e, int x, int y)
    {
        if (donjon.getCase(x,y).isLibre())
        {
            donjon.getCase(x, y).setEntite(e);
            e.setPosition(x, y);
            listeEntite.add(e);
        }
    }
    public void deplacerEntite(Entite e, int dest_x, int dest_y)
    {
        if(donjon.getCase(dest_x,dest_y).isLibre())
        {
            int distance = Math.abs(dest_x- e.getX()) + Math.abs(dest_y-e.getY());
            if(distance <= e.getSpeed()/3)
            {
                Case destination = donjon.getCase(dest_x, dest_y);
                donjon.getCase(e.getX(),e.getY()).setEntite(null);
                destination.setEntite(e);
                e.setPosition(dest_x,dest_y);
                donjon.afficher();
            }
            else
            {
                affichage.afficher("Cette case est hors-portée");
            }

        }
        else
        {
            affichage.afficher("Cette case est déjà occupée");
        }
    }



    public void attaquer(Entite attaquant, int x_cible, int y_cible)
    {
        Entite cible = donjon.getCase(x_cible,y_cible).getEntite();
        if (cible == null)
        {
            affichage.afficher("Aucune cible à ces coordonnées");
            return;
        }
        if(cible==attaquant)
        {
            affichage.afficher("Impossible de s'attaquer soit-même");
            return;
        }
        int distance = Math.abs(cible.getX()-attaquant.getX()) + Math.abs(cible.getY()-attaquant.getY()) ;
        int portee = attaquant.getAtk_reach();
        if(distance > portee)
        {
            affichage.afficher("La cible est hors-portée");
            return;
        }
        int buff_attaque= new Des (1,20).genererRandom();
        int caractAttaque= attaquant.getCaractAtt(portee);
        int totalAttaque=buff_attaque+caractAttaque;

        if(totalAttaque>=cible.getCA())
        {
            int degats= attaquant.getDmg();
            affichage.afficher(attaquant.toString()+" attaque " + cible.toString()+" et lui inflige "+degats+" dégâts");
            cible.changeHp(-degats);
            if(cible.estMort())
            {
                affichage.afficher(cible.toString()+" est mort");
                meurt(cible);
                donjon.afficher();
            }
            else
            {
                affichage.afficher(cible.afficherHP());
                donjon.afficher();
            }
        }

    }


    public void meurt(Entite entite)
    {
        listeEntite.remove(entite);
        donjon.getCase(entite.getX(), entite.getY()).setEntite(null);
    }

    public ArrayList<Entite> getListeEntite()
    {
        return listeEntite;
    }

}









//Limbes
/*public void attaquerMonstre(Personnage attaquant, Monstre cible)
    {
        Des attaque = new Des(1, 20);
        int buff_attaque = attaque.genererRandom();
        int distance = Math.abs(cible.getX()-attaquant.getX()) + Math.abs(cible.getY()-attaquant.getY()) ;
        if(attaquant.getWeapon()==null)
        {
            affichage.afficher("Pas d'arme d'équipée");
            return;
        }
        if (distance > attaquant.getWeapon().getAtk_reach())
        {
            affichage.afficher("La cible est trop éloignée");
            return;
        }
        int caract_attaque;
        if(attaquant.getWeapon().getAtk_reach() >1)
        {
            caract_attaque=attaquant.getDext();
        }
        else
        {
            caract_attaque=attaquant.getStrength();
        }
        int total_attaque=caract_attaque+buff_attaque;
        if(total_attaque >= cible.getCA())
        {
            int degats= attaquant.getWeapon().getDmg().genererRandom();
            affichage.afficher(attaquant.getNom() + " attaque " + cible.getSpecie() + " et inflige " + degats + " dégâts !");
            cible.changeHp(-degats);
            cible.afficherHP();
        }
        else
        {
            affichage.afficher("La classe d'armure du monstre est trop élevée ! L'attaque a échoué !");
        }
    }
    public void attaquerPersonnage(Personnage cible, Monstre attaquant)
    {
        Des attaque = new Des(1, 20);
        int buff_attaque = attaque.genererRandom();
        int distance = Math.abs(cible.getX()-attaquant.getX()) + Math.abs(cible.getY()-attaquant.getY()) ;
        if(distance > attaquant.getAtk_reach())
        {
            affichage.afficher("La cible est trop éloignée");
            return;
        }
        int caract_attaque;
        if(attaquant.getAtkReach()>1)
        {
            caract_attaque=attaquant.getDext();
        }
        else
        {
            caract_attaque=attaquant.getStrength();
        }
        int total_attaque=caract_attaque+buff_attaque;
        if (total_attaque>=cible.getCA())
        {
            int degats=attaquant.getDmg();
            affichage.afficher(attaquant.getSpecie() + " attaque " + cible.getNom() + " et inflige " + degats + " dégâts !");
            cible.changeHp(-degats);
            cible.afficherHP();
        }
    }*/
