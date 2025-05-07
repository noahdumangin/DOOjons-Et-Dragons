package Personnages.Classes;

import Items.*;
import Items.Armors.Armor;
import Items.Weapons.Weapon;
import Personnages.*;

import java.util.ArrayList;

public class Guerriers extends Personnage
{
    private String m_nom;
    private String m_race;
    private Stat m_stat;
    private ArrayList<Item> m_inventory;
    private Armor m_armor;
    private Weapon m_weapon;
}
