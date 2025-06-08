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
    private ArrayList<Item> listeItems;

    public Partie(ArrayList<Personnage> personnages, ArrayList<Monstre> bestiaire, ArrayList<Item> listeItems) {
        this.personnages = personnages;
        this.bestiaire = bestiaire;
        this.listeItems=listeItems;
    }

    public void demarrer() {
        for (int numDonjon = 1; numDonjon <= 5; numDonjon++) {
            affichage.afficher("\n=== Donjon " + numDonjon + " ===");

            int tailleX = entree.lireInt("Entrez la taille X du donjon : ");
            int tailleY = entree.lireInt("Entrez la taille Y du donjon : ");
            if(tailleX<=0)
            {
                tailleX=6; //taille par défault
            }
            if(tailleY<=0)
            {
                tailleY=6;
            }
            Donjon donjon = new Donjon(tailleX, tailleY);
            GestionnaireDonjon gestionnaireDonjon = new GestionnaireDonjon(donjon);
            TourDeJeu tour = new TourDeJeu(gestionnaireDonjon, donjon);

            // Ajouter les personnages
            for (Personnage p : personnages)
            {
                if (!p.estMort()) {
                    affichage.afficher("Placer " + p + " :\n(-1 -1 pour placement aléatoire)");
                    int x = entree.lireInt("X :");
                    int y = entree.lireInt("Y :");
                    if (x == -1 && y == -1) {
                        do
                        {
                            x = random.nextInt(tailleX);
                            y = random.nextInt(tailleY);
                        } while (!donjon.getCase(x, y).isLibre());
                    }
                    gestionnaireDonjon.ajouterEntite(p, x, y);
                }
            }

// Ajouter obstacles
            int nbObstacles = entree.lireInt("Combien d'obstacles souhaitez-vous placer ?");
            for (int i = 0; i < nbObstacles; i++) {
                affichage.afficher("Position de l'obstacle " + (i + 1) + " :\n(-1 -1 pour placement aléatoire)");
                int x = entree.lireInt("X :");
                int y = entree.lireInt("Y :");
                if (x == -1 && y == -1)
                {
                    do
                    {
                        x = random.nextInt(tailleX);
                        y = random.nextInt(tailleY);
                    } while (donjon.getCase(x, y).estObstacle());
                }
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
                Monstre monstreChoisi = new Monstre(bestiaire.get(choix)); // constructeur copie

                affichage.afficher("Position du monstre " + monstreChoisi.getSpecie() + " :\n(-1 -1 pour placement aléatoire)");
                int x = entree.lireInt("X :");
                int y = entree.lireInt("Y :");
                if (x == -1 && y == -1) {
                    do {
                        x = random.nextInt(tailleX);
                        y = random.nextInt(tailleY);
                    } while (!donjon.getCase(x, y).isLibre());
                }
                gestionnaireDonjon.ajouterEntite(monstreChoisi, x, y);
            }

// Ajouter items
            int nbItems = entree.lireInt("Combien d'items voulez-vous placer ?");
            for (int i = 0; i < nbItems; i++) {
                affichage.afficher("Choisissez un item dans la liste :");
                for (int j = 0; j < listeItems.size(); j++) {
                    affichage.afficher((j + 1) + ". " + listeItems.get(j).toString());
                }
                int choixItem = entree.lireInt("Votre choix :") - 1;
                Item itemChoisi = listeItems.get(choixItem);

                affichage.afficher("Position de l'item " + itemChoisi.getNom() + " :\n(-1 -1 pour placement aléatoire)");
                int x = entree.lireInt("X :");
                int y = entree.lireInt("Y :");
                if (x == -1 && y == -1) {
                    do {
                        x = random.nextInt(tailleX);
                        y = random.nextInt(tailleY);
                    } while (!donjon.getCase(x, y).isLibre() || donjon.getCase(x, y).getItem() != null);
                }
                donjon.getCase(x, y).setItem(itemChoisi);
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
