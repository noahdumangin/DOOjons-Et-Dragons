package systeme.actions.sorts;

import entite.Entite;
import outils.Des;
import personnages.Personnage;
import systeme.Affichage;
import systeme.Entree;

import systeme.GestionnaireDonjon;

public class Guerison implements Sort{



    @Override
    public boolean executer(Entite entite, GestionnaireDonjon gestionnaireDonjon) {

        Entree entre = new Entree();
        Affichage affichage = new Affichage();
        affichage.afficher("Liste des personnages :");
        for(int i=0;i<gestionnaireDonjon.listePersonnage().size();i++)
        {
            Personnage personnage = gestionnaireDonjon.listePersonnage().get(i);
            affichage.afficher(i+1+"."+personnage.toString()+" : "+personnage.getHp()+"/"+personnage.getMaxHp());
        }
        int choix = Entree.lireInt("Sélectionnez l'id d'un des joueurs que vous voulez soigner");
        if(choix<=gestionnaireDonjon.listePersonnage().size() && choix > 0)
        {
            Personnage personnageCible = gestionnaireDonjon.listePersonnage().get(choix-1);
            Des heal = new Des(1,10);
            if(personnageCible.changeHp(heal.genererRandom()))
                return true;
        }

        affichage.afficher("Veuillez rentrer un id valable");

        return false;
    }

    @Override
    public String Description() {
        return "Guérison";
    }
}
