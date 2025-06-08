package systeme;
import donjon.Donjon;
import entite.Entite;

import java.util.Comparator;
import java.util.ArrayList;
import java.util.Collections;
import systeme.*;

import personnages.Personnage;
import systeme.actions.*;

public class TourDeJeu
{

    private Affichage affichage = new Affichage();
    private GestionnaireDonjon gd;
    private Entree entree = new Entree();
    private int tour;
    private ArrayList<Entite> listeEntite;
    private int action_restante;
    Donjon m_donjon;


    public TourDeJeu(GestionnaireDonjon gestionnaireDonjon, Donjon donjon) //constructeur
    {
        this.gd = new GestionnaireDonjon(donjon);
        this.listeEntite = gestionnaireDonjon.getListeEntite();
        this.entree = new Entree();
        gererOrdre(this.listeEntite);
        this.action_restante=0;
        this.m_donjon=donjon;
    }

    public ArrayList<Action> getActionsDisponibles(Entite entite, GestionnaireDonjon gestionnaireDonjon)
    {
        ArrayList<Action> actions = new ArrayList<>();
        if(entite.getType()== Entite.TypeEntite.PERSONNAGE)
        {
            actions.addAll(((Personnage) entite).getActionDeBase());
            if (gestionnaireDonjon.PersonnageSurItem(entite,m_donjon)) //Si le personnage est sur un item, alors on lui rajoute l'action Ramasser
            {
                actions.add(new ActionRamasser());
            }
        }
        if(entite.getType()== Entite.TypeEntite.MONSTRE)
        {
            actions.addAll(entite.getActionDeBase());
        }
        return actions;
    }

    private void gererOrdre(ArrayList<Entite> listeEntite)
    {
        Collections.sort(listeEntite, Comparator.comparingInt(Entite::getInit).reversed());
    }

    public void afficherOrdre(int actuelle)
    {
        affichage.afficher("Ordre d'action :\n");

        for(int i=0;i<listeEntite.size();i++)
        {
            Entite entite_temp = listeEntite.get(i);
            if(!entite_temp.estMort())
            {
                String fleche="  ";
                if(listeEntite.indexOf(entite_temp)==actuelle)
                {
                    fleche="->";
                }
                affichage.afficher(fleche+entite_temp.toString()+" "+entite_temp.getDescription() );
            }
        }
    }
    public void jouer(Entite entite, GestionnaireDonjon gestionnaireDonjon)
    {
        ArrayList<Action> listeActions= getActionsDisponibles(entite, gestionnaireDonjon);
        affichage.afficher("Actions disponibles :");
        for(int i=0;i<listeActions.size();i++)
        {
            affichage.afficher(i+1+"."+listeActions.get(i).getNom());
        }

        int choix  = Entree.lireInt("Choisissez une action :");

        if (choix >= 1 && choix <= listeActions.size())
        {
            boolean succes = listeActions.get(choix - 1).executer(entite, gestionnaireDonjon);
            if(succes)
            {
                action_restante--;
                affichage.afficher("Succès !");
            }
            else
            {
                affichage.afficher("l'action a échoué");
            }
        }
        else
        {
            affichage.afficher("Action invalide.");
        }
    }
    public void jouerTour(GestionnaireDonjon gestionnaireDonjon)
    {

        for(int i=0;i< listeEntite.size();i++)
        {
            if(!listeEntite.get(i).estMort())
            {
                action_restante=3;
                afficherOrdre(i);
                affichage.afficher("C'est au tour de "+listeEntite.get(i).toString());
                while (action_restante > 0)
                {
                    m_donjon.afficher();
                    Entite entite_temp= listeEntite.get(i);
                    entite_temp.afficherHP();
                    if(entite_temp.getType()== Entite.TypeEntite.PERSONNAGE)
                    {
                        Personnage personnage_temp = (Personnage) entite_temp;
                        if(personnage_temp.getArmor()!=null)
                        affichage.afficher("Armure : "+personnage_temp.getArmor().toString() );
                        else {
                            affichage.afficher("Armure : Aucune");
                        }
                        if(personnage_temp.getWeapon()!=null)
                        {
                            affichage.afficher("Arme : "+personnage_temp.getWeapon().toString()+"(degats");
                        }
                        else
                        {
                            affichage.afficher("Arme : Aucune");
                        }
                        personnage_temp.afficherInventaire();


                    }
                    entite_temp.afficherStats();

                    affichage.afficher("Action restantes : " +action_restante  +"/3");
                    jouer(listeEntite.get(i),gestionnaireDonjon);
                    //affichage.afficher("Laissez le maitre du jeu doit-il intervenir ?");

                }
            }
            //if(li)

        }
    }
}