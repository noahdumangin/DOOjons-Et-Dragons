package systeme.actions.sorts;

import entite.Entite;
import systeme.Affichage;
import systeme.Entree;
import systeme.GestionnaireDonjon;
import systeme.actions.Action;

public class BoogieWoogie implements Sort {


    @Override
    public boolean executer(Entite entite, GestionnaireDonjon gestionnaireDonjon) {

        Entree entre = new Entree();
        Affichage affichage = new Affichage();
        affichage.afficher("Liste des entités :");
        for(int i=0;i<gestionnaireDonjon.getListeEntite().size();i++)
        {
            affichage.afficher(i+1+"." +gestionnaireDonjon.getListeEntite().get(i).toString());
        }
        int choix = Entree.lireInt("\nVeuillez choisir l'id de la cible 1");

        if(choix>gestionnaireDonjon.getListeEntite().size())
        {
            return false;
        }
        Entite cible1 = gestionnaireDonjon.getListeEntite().get(choix-1);
        Entree.fermer();
        choix = Entree.lireInt("\nVeuillez choisir l'id de la cible 2");
        if(choix>gestionnaireDonjon.getListeEntite().size())
        {
            return false;
        }
            Entite cible2 = gestionnaireDonjon.getListeEntite().get(choix-1);
        Entree.fermer();

        if(cible2 != cible1) {
            int temp_x_cible1 = cible1.getX();
            int temp_y_cible1 = cible1.getY();
            int temp_x_cible2 = cible2.getX();
            int temp_y_cible2 = cible2.getY();

            affichage.afficher(temp_x_cible1+"-"+temp_y_cible1);
            affichage.afficher(temp_x_cible2+"-"+temp_y_cible2);

            gestionnaireDonjon.seTeleporterAbime(cible2);
            gestionnaireDonjon.seTeleporterBoogieWoogie(cible1, temp_x_cible2, temp_y_cible2);
            gestionnaireDonjon.seTeleporterBoogieWoogie(cible2, temp_x_cible1, temp_y_cible1);

            affichage.afficher(cible1.getX()+"-"+cible1.getY());
            affichage.afficher(cible2.getX()+"-"+cible2.getY());


            affichage.afficher("CLAP ! " + cible1.toString() + " et " + cible2.toString() + " ont échangé de position !");
            return true;
        }


        affichage.afficher("Echec de la téleportation");
        return false;
    }

    @Override
    public String Description() {
        return "Boogie Woogie";
    }
}

