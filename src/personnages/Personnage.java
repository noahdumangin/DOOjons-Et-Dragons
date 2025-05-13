package personnages;
import items.*;
import java.util.ArrayList;


public class Personnage {
    public String m_nom;

    public int m_hp=0;
    public int m_strength=0;
    public int m_dext=0;
    public int m_speed=0;
    public int m_init=0;


    public Personnage(String nom, String race)
    {
        this.m_nom=nom;
        switch (race)
        {
            case "Humain":
                this.m_hp+=2;
                this.m_strength+=2;
                this.m_dext+=2;
                this.m_speed+=2;
                this.m_init+=2;
                break;
            case "Nain":
                this.m_strength+=2;
                break;
            case "Elfe":
                this.m_dext+=6;
                break;
            case "Halfelins":
                this.m_dext+=4;
                this.m_speed+=2;
                break;
        }


    }
    public void afficherStats() {
        System.out.println("Nom : " + m_nom);
        System.out.println("PV : " + m_hp);
        System.out.println("Force : " + m_strength);
        System.out.println("Dexterité : " + m_dext);
        System.out.println("Vitesse : " + m_speed);
        System.out.println("Initiative : " + m_init);
    }
}
