package cal.info;

import java.time.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ServiceVente {
    private List<Vente> ventes = new ArrayList<>();
    ServiceInventaire si = ServiceInventaire.getInstance();

    public ServiceVente(){
        Vente v = new Vente();
        v.setDateVente(new Date(2021, 05, 12));
        List<Chausette> chausettes = new ArrayList<>();
        chausettes.add(new Chausette(1, "Mauve", "M4", "Polyestre", 19));
        v.setChausettes(chausettes);
        v.calculerTotal();
        creerVente(v);
    }

    public void creerVente(Vente v){
        if (ventes.isEmpty()){
            v.setIdentifiant(1);
        }else{
            System.out.println(ventes.size());
            v.setIdentifiant(ventes.get(ventes.size() - 1).getIdentifiant() + 1);
        }
        v.setDateVente(Date.from(Instant.now()));
        v.calculerTotal();
        ventes.add(v);
        for (Chausette c1 : v.getChausettes()){
            for (Chausette c2 : si.listerChausettes()){
                if (c1.getTaille().equals(c2.getTaille()) && c1.getPrix() == c2.getPrix() && c1.getCouleur().equals(c2.getCouleur()) && c1.getTypeTissu().equals(c2.getTypeTissu()) && c1.getIdentifiant() == c2.getIdentifiant()){
                    si.supprimerChausette(c2.getIdentifiant());
                }
            }
        }
    }
    public void annulerVente(int id){
        for (Vente vente : ventes){
            if (vente.getIdentifiant() == id){
                for (Chausette c : vente.getChausettes()){
                    si.ajouterChausette(c);
                }
                ventes.remove(vente);
            }
        }
    }
    public List<Vente> listerVentes(){
        return ventes;
    }
    public Vente rechercherVente(int id){
        for (Vente vente : ventes){
            if (vente.getIdentifiant() == id){
                return vente;
            }
        }
        return null;
    }
    public Vente rechercherVenteParDate(Date d){
        for (Vente vente : ventes){
            if (vente.getDateVente() == d){
                return vente;
            }
        }
        return null;
    }
}
