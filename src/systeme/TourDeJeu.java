package systeme;
import donjon.Donjon;
import entite.Entite;

import java.util.Comparator;
import java.util.ArrayList;
import java.util.Collections;

import systeme.actions.Action;

public class TourDeJeu
{

    private Affichage affichage = new Affichage();
    private GestionnaireDonjon gd;
    private Entree entree = new Entree();
    private int tour;
    private ArrayList<Entite> listeEntite;


    public TourDeJeu(GestionnaireDonjon gestionnaireDonjon, Donjon donjon) //constructeur
    {
        this.gd = new GestionnaireDonjon(donjon);
        this.listeEntite = gestionnaireDonjon.getListeEntite();
        this.entree = new Entree();
        gererOrdre(this.listeEntite);
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
            String fleche="  ";
            if(listeEntite.indexOf(entite_temp)==actuelle)
            {
                fleche="->";
            }
            affichage.afficher(fleche+entite_temp.toString()+" "+entite_temp.getDescription() );
        }
    }
    public void jouer(Entite entite, GestionnaireDonjon gestionnaireDonjon)
    {
        ArrayList<Action> listeActions= entite.getAction();
        affichage.afficher("Actions disponibles :");
        for(int i=0;i<listeActions.size();i++)
        {
            affichage.afficher(i+1+"."+listeActions.get(i).getNom());
        }
        affichage.afficher("Choisissez une action :");
        int choix  = entree.scanner.nextInt();

        if (choix >= 1 && choix <= listeActions.size())
        {
            listeActions.get(choix - 1).executer(entite, gestionnaireDonjon);
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
            affichage.afficher("C'est au tour de "+listeEntite.get(i).toString());
            for(int j=0;j<3;j++)
            {
                jouer(listeEntite.get(i),gestionnaireDonjon);
            }

        }
    }
}

