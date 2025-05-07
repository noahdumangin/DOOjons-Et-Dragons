import Items.Armors.Armor;
import Items.Armors.Heavy.*;
import Items.Weapons.*;
import Items.Weapons.SimpleRange.*;


public class Main {
    public static void main(String args[]){
        System.out.println("Bienvenue dans DOOnjon et Dragons");

        Armor hrn = new Harness();
        System.out.println(hrn.getName());
        System.out.println(hrn.getCA());

       Weapon wep = new LightCrossbow();
        System.out.println(wep.getName());
        System.out.println(wep.getDice());
        System.out.println(wep.getRange());

    }
}