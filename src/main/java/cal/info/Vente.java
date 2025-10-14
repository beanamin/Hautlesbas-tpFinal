package cal.info;

import java.util.Date;
import java.util.List;

public class Vente {
    private int identifiant;
    private Date dateVente;
    private double total;
    private List<Chausette> chausettes;

    public void ajouterChausette (Chausette c){
        chausettes.add(c);
    }
    public double calculerTotal (){
        int totalCalcul = 0;
        for (Chausette chausette : chausettes){
            totalCalcul += chausette.getPrix();
        }
        this.total = totalCalcul;
        return totalCalcul;
    }

    public int getIdentifiant() {
        return identifiant;
    }

    public Date getDateVente() {
        return dateVente;
    }

    public void setIdentifiant(int identifiant) {
        this.identifiant = identifiant;
    }

    public double getTotal() {
        return total;
    }

    public List<Chausette> getChausettes() {
        return chausettes;
    }

    public void setChausettes(List<Chausette> chausettes) {
        this.chausettes = chausettes;
    }

    public void setDateVente(Date dateVente) {
        this.dateVente = dateVente;
    }

    public void setTotal(double total) {
        this.total = total;
    }

}
