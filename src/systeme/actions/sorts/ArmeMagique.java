package systeme.actions.sorts;

import entite.Entite;
import items.Weapon;
import outils.Des;
import personnages.Personnage;
import systeme.Affichage;
import systeme.Entree;
import systeme.GestionnaireDonjon;

public class ArmeMagique implements Sort{


    @Override
    public boolean executer(Entite entite, GestionnaireDonjon gestionnaireDonjon) {
        Entree entre = new Entree();
        Affichage affichage = new Affichage();

        affichage.afficher("Liste des personnages :");
        for (int i = 0; i < gestionnaireDonjon.listePersonnage().size(); i++)
        {
            Personnage personnage = gestionnaireDonjon.listePersonnage().get(i);
            affichage.afficher((i + 1) + ". " + personnage.toString());
        }

        int choix = Entree.lireInt("Sélectionnez l'id du joueur dont vous voulez enchanter une arme");
        if (choix <= 0 || choix > gestionnaireDonjon.listePersonnage().size())
            return false;

        Personnage personnageChoisi = gestionnaireDonjon.listePersonnage().get(choix - 1);

        if (personnageChoisi.listeArmes().isEmpty()) {
            affichage.afficher("Ce personnage n'a aucune arme.");
            return false;
        }

        affichage.afficher("Armes disponibles :");
        for (int i = 0; i < personnageChoisi.listeArmes().size(); i++) {
            Weapon armeTemp = personnageChoisi.listeArmes().get(i);
            affichage.afficher((i + 1) + ". " + armeTemp.getNom() + " (" + armeTemp.getNbDes() + "d" + armeTemp.getTypeDes() + " + " + armeTemp.getBonusDegats() + ")");
        }

        choix = Entree.lireInt("Sélectionnez l'arme à enchanter :");
        if (choix <= 0 || choix > personnageChoisi.listeArmes().size())
            return false;

        Weapon armeAEnchanter = personnageChoisi.listeArmes().get(choix - 1);
        armeAEnchanter.ameliorer();

        affichage.afficher("L'arme " + armeAEnchanter.getNom() + " a été enchantée !");
        return true;
    }


    @Override
    public String Description() {
        return "Arme Magique";
    }
}
