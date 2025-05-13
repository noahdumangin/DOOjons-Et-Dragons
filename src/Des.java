import java.util.ArrayList;
import java.util.Random;
public class Des {
    static Random randomNumbers = new Random();
    private int nb_des;
    private int type_des;
    ArrayList<Integer> Resultat;
    static int somme=0;

    public Des(int nb_des, int type_des) {
        this.nb_des = nb_des;
        this.type_des = type_des;
    }
    public static int genererRandom(int nb_des, int type_des)
    {
        for (int i=0; i<nb_des;i++)
        {
            somme+=randomNumbers.nextInt(6)+1;
        }
        return somme;
    }
}


