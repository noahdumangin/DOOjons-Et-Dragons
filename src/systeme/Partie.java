package systeme;

import donjon.Donjon;
import entite.Entite;
import personnages.Personnage;
import systeme.Affichage;
import systeme.GestionnaireDonjon;
import systeme.TourDeJeu;

import java.util.ArrayList;

public class Partie
{

    Affichage affichage = new Affichage();


    public Partie(Donjon donjon, GestionnaireDonjon gestionnaireDonjon)

    {
    }


    public void jouerPartie(Donjon donjon, GestionnaireDonjon gestionnaireDonjon )
    {
        TourDeJeu tourDeJeu = new TourDeJeu(gestionnaireDonjon, donjon);

        ArrayList<Entite> listeEntite = gestionnaireDonjon.getListeEntite();
        donjon.afficher();
        while(!gameOver(listeEntite))
        {
            tourDeJeu.jouerTour(gestionnaireDonjon);
        }
    }

    public boolean gameOver(ArrayList<Entite> listeEntite)
    {
        for (int i=0;i<listeEntite.size();i++)
        {
            if(!listeEntite.get(i).estMort() && listeEntite.get(i).getType()== Entite.TypeEntite.PERSONNAGE)
            {
                return false;
            }
            affichage.afficher("Tout les joueurs sont mort !");
            affichage.afficher("\n" +
                    "\n" +
                    "  ▄████  ▄▄▄       ███▄ ▄███▓▓█████     ▒█████   ██▒   █▓▓█████  ██▀███  \n" +
                    " ██▒ ▀█▒▒████▄    ▓██▒▀█▀ ██▒▓█   ▀    ▒██▒  ██▒▓██░   █▒▓█   ▀ ▓██ ▒ ██▒\n" +
                    "▒██░▄▄▄░▒██  ▀█▄  ▓██    ▓██░▒███      ▒██░  ██▒ ▓██  █▒░▒███   ▓██ ░▄█ ▒\n" +
                    "░▓█  ██▓░██▄▄▄▄██ ▒██    ▒██ ▒▓█  ▄    ▒██   ██░  ▒██ █░░▒▓█  ▄ ▒██▀▀█▄  \n" +
                    "░▒▓███▀▒ ▓█   ▓██▒▒██▒   ░██▒░▒████▒   ░ ████▓▒░   ▒▀█░  ░▒████▒░██▓ ▒██▒\n" +
                    " ░▒   ▒  ▒▒   ▓▒█░░ ▒░   ░  ░░░ ▒░ ░   ░ ▒░▒░▒░    ░ ▐░  ░░ ▒░ ░░ ▒▓ ░▒▓░\n" +
                    "  ░   ░   ▒   ▒▒ ░░  ░      ░ ░ ░  ░     ░ ▒ ▒░    ░ ░░   ░ ░  ░  ░▒ ░ ▒░\n" +
                    "░ ░   ░   ░   ▒   ░      ░      ░      ░ ░ ░ ▒       ░░     ░     ░░   ░ \n" +
                    "      ░       ░  ░       ░      ░  ░       ░ ░        ░     ░  ░   ░     \n" +
                    "                                                     ░                   \n" +
                    "\n");
            return true;

        }
        return false;

    }
}
