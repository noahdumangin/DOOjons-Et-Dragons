package systeme;

import donjon.Donjon;
import entite.Entite;
import items.Item;
import monstres.Monstre;
import personnages.Personnage;
import systeme.Affichage;
import systeme.GestionnaireDonjon;
import systeme.TourDeJeu;

import java.util.ArrayList;
import java.util.Random;

public class Partie
{

    private Affichage affichage = new Affichage();
    private Entree entree = new Entree();
    private Random random = new Random();


    private ArrayList<Personnage> personnages;
    private ArrayList<Monstre> bestiaire;

    public Partie(ArrayList<Personnage> personnages, ArrayList<Monstre> bestiaire) {
        this.personnages = personnages;
        this.bestiaire = bestiaire;
    }

    public void demarrer() {
        for (int numDonjon = 1; numDonjon <= 5; numDonjon++) {
            affichage.afficher("\n=== Donjon " + numDonjon + " ===");

            int tailleX = entree.lireInt("Entrez la taille X du donjon : ");
            int tailleY = entree.lireInt("Entrez la taille Y du donjon : ");

            Donjon donjon = new Donjon(tailleX, tailleY);
            GestionnaireDonjon gestionnaireDonjon = new GestionnaireDonjon(donjon);
            TourDeJeu tour = new TourDeJeu(gestionnaireDonjon, donjon);

            // Ajouter personnages vivants dans le donjon
            for (Personnage p : personnages) {
                if (!p.estMort()) {
                    gestionnaireDonjon.ajouterEntite(p, random.nextInt(tailleX), random.nextInt(tailleY));
                }
            }

            // Ajouter obstacles
            int nbObstacles = entree.lireInt("Combien d'obstacles souhaitez-vous placer ?");
            for (int i = 0; i < nbObstacles; i++) {
                int x = random.nextInt(tailleX);
                int y = random.nextInt(tailleY);
                donjon.getCase(x, y).setObstacle(true);
            }

            // Ajouter monstres
            int nbMonstres = entree.lireInt("Combien de monstres voulez-vous ajouter ?");
            for (int i = 0; i < nbMonstres; i++) {
                affichage.afficher("Choisissez un monstre dans le bestiaire :");
                for (int j = 0; j < bestiaire.size(); j++) {
                    affichage.afficher((j + 1) + ". " + bestiaire.get(j).getSpecie());
                }
                int choix = entree.lireInt("Votre choix :") - 1;
                Monstre monstreChoisi = new Monstre(bestiaire.get(choix));
                gestionnaireDonjon.ajouterEntite(monstreChoisi, random.nextInt(tailleX), random.nextInt(tailleY));
            }

            // Afficher plateau et lancer la partie de ce donjon
            donjon.afficher();
            phasePreparation(personnages, gestionnaireDonjon);
            while (!donjonTermine(gestionnaireDonjon)) {
                tour.jouerTour(gestionnaireDonjon);

                if (aucunPersoEnVie()) {
                    affichage.afficher("\nTous les joueurs sont morts !");
                    afficherGameOver();
                    return;
                }
            }

            affichage.afficher("\n=== Donjon " + numDonjon + " terminé ! ===");
        }

        affichage.afficher("Vous avez complété les 5 donjons !");
        affichage.afficher("\n" +
                "\n" +
                " ██▒   █▓ ██▓ ▄████▄  ▄▄▄█████▓ ▒█████   ██▓ ██▀███  ▓█████     ▐██▌ \n" +
                "▓██░   █▒▓██▒▒██▀ ▀█  ▓  ██▒ ▓▒▒██▒  ██▒▓██▒▓██ ▒ ██▒▓█   ▀     ▐██▌ \n" +
                " ▓██  █▒░▒██▒▒▓█    ▄ ▒ ▓██░ ▒░▒██░  ██▒▒██▒▓██ ░▄█ ▒▒███       ▐██▌ \n" +
                "  ▒██ █░░░██░▒▓▓▄ ▄██▒░ ▓██▓ ░ ▒██   ██░░██░▒██▀▀█▄  ▒▓█  ▄     ▓██▒ \n" +
                "   ▒▀█░  ░██░▒ ▓███▀ ░  ▒██▒ ░ ░ ████▓▒░░██░░██▓ ▒██▒░▒████▒    ▒▄▄  \n" +
                "   ░ ▐░  ░▓  ░ ░▒ ▒  ░  ▒ ░░   ░ ▒░▒░▒░ ░▓  ░ ▒▓ ░▒▓░░░ ▒░ ░    ░▀▀▒ \n" +
                "   ░ ░░   ▒ ░  ░  ▒       ░      ░ ▒ ▒░  ▒ ░  ░▒ ░ ▒░ ░ ░  ░    ░  ░ \n" +
                "     ░░   ▒ ░░          ░      ░ ░ ░ ▒   ▒ ░  ░░   ░    ░          ░ \n" +
                "      ░   ░  ░ ░                   ░ ░   ░     ░        ░  ░    ░    \n" +
                "     ░       ░                                                       \n" +
                "\n");
    }

    private boolean aucunPersoEnVie()
    {
        for (Personnage p : personnages) {
            if (!p.estMort()) {
                return false;
            }
        }
        return true;
    }

    private boolean donjonTermine(GestionnaireDonjon gestionnaireDonjon) {
        for (Entite e : gestionnaireDonjon.getListeEntite()) {
            if (e.getType() == Entite.TypeEntite.MONSTRE && !e.estMort()) {
                return false;
            }
        }
        return true;
    }

    private void afficherGameOver() {
        affichage.afficher("\nGAME OVER — vous avez péri dans le donjon !");
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
    }

    public void phasePreparation(ArrayList<Personnage> joueurs, GestionnaireDonjon gestionnaireDonjon) {
        Affichage affichage = new Affichage();
        Entree entree = new Entree();

        affichage.afficher("\n════════════════════════════════════");
        affichage.afficher("        PHASE D'ÉQUIPEMENT         ");
        affichage.afficher("════════════════════════════════════");

        for (Personnage p : joueurs)
        {
            if (!p.estMort()) {
                affichage.afficher("\n->" + p.toString() + ", c'est ton tour pour t'équiper !");
                boolean continuer = true;
                while (continuer)
                {
                    p.afficherInventaire();
                    if (p.getInventory().isEmpty())
                    {
                        affichage.afficher("Inventaire vide.");
                        break;
                    }

                    int choix = entree.lireInt("Choisissez l'ID d'un objet à équiper (0 pour passer) :");
                    if (choix == 0) {
                        continuer = false;
                    }
                    else if (choix >= 1 && choix <= p.getInventory().size())
                    {
                        Item item = p.getInventory().get(choix - 1);
                        p.equiper(item);
                    }
                    else
                    {
                        affichage.afficher("Choix invalide.");
                    }
                }
            }
        }

        affichage.afficher("\n════════════════════════════════════");
        affichage.afficher(" Fin de la phase d'équipement !");
        affichage.afficher("════════════════════════════════════\n");
    }

}
