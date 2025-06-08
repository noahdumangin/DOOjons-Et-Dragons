package systeme;
import donjon.*;
import entite.Entite;
import personnages.Personnage;
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
    public boolean deplacerEntite(Entite e, int dest_x, int dest_y)
    {
        if(donjon.getCase(dest_x,dest_y).isLibre())
        {
            int distance = Math.abs(dest_x- e.getX()) + Math.abs(dest_y-e.getY());
            int portee = e.getSpeed()/3;
            if(portee < 1) //pour éviter que l'entité ne reste bloqué si sa vitesse est inférieure a 3
                portee=1;

            if(distance <= portee)
            {
                Case destination = donjon.getCase(dest_x, dest_y);
                donjon.getCase(e.getX(),e.getY()).setEntite(null);
                destination.setEntite(e);
                e.setPosition(dest_x,dest_y);

                return true;
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
        return false;
    }

    public boolean seTeleporterBoogieWoogie(Entite entite, int dest_x, int dest_y) //Deux méthodes différentes pour se téléporter car Boogie Woogie va faire se tp à un moment une Entité sur une autre
    {
        if(!donjon.getCase(dest_x, dest_y).estObstacle())
        {
            Case destination = donjon.getCase(dest_x, dest_y);
            donjon.getCase(entite.getX(), entite.getY()).setEntite(null);
            destination.setEntite(entite);
            entite.setPosition(dest_x, dest_y);
            return true;
        }
        return false;
    }
    public void seTeleporterAbime(Entite entite)
    {

        Case abime = donjon.getCaseInvisible();
        donjon.getCase(entite.getX(), entite.getY()).setEntite(null);
        abime.setEntite(null);

        abime.setEntite(entite);
        entite.setPosition(0, donjon.getTailleY());
    }

    public boolean seTeleporter(Entite entite, int dest_x, int dest_y)
    {
        if(donjon.getCase(dest_x, dest_y).isLibre())
        {
            Case destination = donjon.getCase(dest_x, dest_y);
            donjon.getCase(entite.getX(), entite.getY()).setEntite(null);
            destination.setEntite(entite);
            entite.setPosition(dest_x, dest_y);
            return true;
        }
        return false;
    }


    public boolean attaquer(Entite attaquant, int x_cible, int y_cible)
    {
        Entite cible = donjon.getCase(x_cible, y_cible).getEntite();
        if (cible == null) {
            affichage.afficher("Aucune cible à ces coordonnées");
            return false;
        }
        if (cible == attaquant) {
            affichage.afficher("Impossible de s'attaquer soi-même");
            return false;
        }

        int distance = Math.abs(cible.getX() - attaquant.getX()) + Math.abs(cible.getY() - attaquant.getY());
        if (distance > attaquant.getAtk_reach())
        {
            affichage.afficher("La cible est hors-portée");
            return false;
        }

        int degats = attaquant.attaquer(cible);

        if (degats == -1)
        {
            affichage.afficher("La cible est hors-portée"); // double sécurité
            return false;
        }
        else if (degats == 0)
        {
            affichage.afficher("L'attaque a échoué !");
            return false;
        }
        else
        {
            affichage.afficher(attaquant.toString() + " attaque " + cible.toString() + " et lui inflige " + degats + " dégâts");
            cible.changeHp(-degats);

            if (cible.estMort())
            {
                affichage.afficher(cible.toString() + " est mort");
                meurt(cible);
            }
            else
            {
                affichage.afficher(cible.afficherHP());
            }
            return true;
        }
    }


    public boolean PersonnageSurItem(Entite entite, Donjon donjon)
    {
        Personnage personnage = (Personnage) entite;
        return(donjon.getCase(personnage.getX(), personnage.getY()).getItem()!=null);
    }


    public void meurt(Entite entite)
    {
        donjon.getCase(entite.getX(), entite.getY()).setEntite(null);
    }

    public ArrayList<Entite> getListeEntite()
    {
        return listeEntite;
    }

    public ArrayList<Personnage> listePersonnage()
    {
        ArrayList<Personnage> persos = new ArrayList<>();

        for (int i = 0; i < listeEntite.size(); i++) {
            if (listeEntite.get(i).getType() == Entite.TypeEntite.PERSONNAGE) {
                persos.add((Personnage) listeEntite.get(i));
            }
        }

        return persos;
    }



    public Donjon getDonjon()
    {
        return this.donjon;
    }


}