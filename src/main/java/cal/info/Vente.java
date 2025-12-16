package cal.info;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.Instant;
import java.util.Date;
import java.util.List;

public class Vente {


    private int id;
    private Date dateVente;
    private double total;
    private List<Chausette> chausettes;
    public Vente(List<Chausette> chausettes){
        this.chausettes = chausettes;
        this.total = calculerTotal();
        this.dateVente = Date.from(Instant.now());
    }
    public Vente(){
        this.dateVente = Date.from(Instant.now());
    }
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

    public int getId() {
        return id;
    }

    public Date getDateVente() {
        return dateVente;
    }

    public void setId(int identifiant) {
        this.id = identifiant;
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

//        "id": 1,
//        "dateVente": 1765774800000,
//        "total": 19.0,
//        "chausettes": [
//    {
//        "id": 1,
//            "couleur": "Mauve",
//            "taille": "M4",
//            "typeTissu": "Polyestre",
//            "prix": 19.0
//    }
//    ]
}
