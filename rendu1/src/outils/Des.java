package outils;

import java.util.ArrayList;
import java.util.Random;
public class Des {
    static Random randomNumbers = new Random();
    private int m_nb_des;
    private int m_type_des;
    static int somme=0;

    public Des(int nb_des, int type_des) {
        this.m_nb_des = nb_des;
        this.m_type_des = type_des;
    }
    public ArrayList<Integer> genererRandom()
    {
        ArrayList<Integer> resultat = new ArrayList<>();
        for (int i=0; i<m_nb_des;i++)
        {
            resultat.add(randomNumbers.nextInt(m_type_des)+1);
        }
        return resultat;
    }
}


