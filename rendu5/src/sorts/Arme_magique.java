package sorts;

import items.Weapon;

public class Arme_magique extends Sort{
    public void lancer(Weapon Armechoisi){

        int damage = Armechoisi.add_dmg(Armechoisi);
        int portee= Armechoisi.add_atk_reach(Armechoisi);


        System.out.println("L'arme " + Armechoisi.getNom() + " a été améliorée, " + "\n"+
                "elle fait "+ damage +" dégâts et a une portée de "+portee+"!"+"\n");
    }
}