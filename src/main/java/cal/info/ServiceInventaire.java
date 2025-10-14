package cal.info;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

public class ServiceInventaire {
    private static List<Chausette> inventaire = new ArrayList<>();

    private static ServiceInventaire instance = null;
    public static ServiceInventaire getInstance() {
        if (instance == null) {
            instance = new ServiceInventaire();
        }
        return instance;
    }
    public ServiceInventaire() {
        Chausette c1 = new Chausette();
        c1.setPrix(19);
        c1.setTaille("M4");
        c1.setCouleur("Mauve");
        c1.setTypeTissu("Polyestre");
        ajouterChausette(c1);
        Chausette c2 = new Chausette();
        c2.setPrix(22);
        c2.setTaille("F12");
        c2.setCouleur("Orange");
        c2.setTypeTissu("Cotton");
        ajouterChausette(c2);

    }


    public void ajouterChausette(Chausette c){
        if (inventaire.isEmpty()){
            c.setIdentifiant(1);
        }else{
            c.setIdentifiant(inventaire.get(inventaire.size() - 1).getIdentifiant() + 1);
        }
        inventaire.add(c);
    }
    public void modifierChausette(Chausette c){
        for (Chausette chausette : inventaire){
            if (chausette.getIdentifiant() == c.getIdentifiant()){
                chausette.setPrix(c.getPrix());
                chausette.setTaille(c.getTaille());
                chausette.setCouleur(c.getCouleur());
                chausette.setTypeTissu(c.getTypeTissu());
            }
        }
    }
    public void supprimerChausette(int id){
        for (Chausette chausette : inventaire){
            if (chausette.getIdentifiant() == id){
                inventaire.remove(chausette);
            }
        }
    }
    public List<Chausette> listerChausettes(){
    return inventaire;
    }
    public List<Chausette> rechercherChausette(String couleur, String taille){
        List<Chausette> chausetteList = new ArrayList<>();
        for (Chausette chausette : inventaire){
            if (chausette.getCouleur().equals(couleur) && chausette.getTaille().equals(taille)){
                chausetteList.add(chausette);
            }
        }
        if (!chausetteList.isEmpty()){
            return chausetteList;
        }
        return null;
    }

}
