package cal.info;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

public class ServiceInventaire {
    private List<Chausette> inventaire = new ArrayList<>();

    public void ajouterChausette(Chausette c){
        inventaire.add(c);
    }
    public void modifierChausette(Chausette c){

    }
    public void supprimerChausette(int id){

    }
    public List<Chausette> listerChausettes(){
    return null;
    }
    public List<Chausette> rechercherChausette(String couleur, String taille){
        return null;
    }

}
