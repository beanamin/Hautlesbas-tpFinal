package cal.info;

public class Chausette {
    private int identifiant;
    private String couleur;
    private String taille;
    private String typeTissu;
    private double prix;

    public Chausette(int identifiant, String couleur, String taille, String typeTissu, double prix){
        this.identifiant = identifiant;
        this.couleur = couleur;
        this.taille = taille;
        this.typeTissu = typeTissu;
        this.prix = prix;
    }
    public Chausette(){}

    public double getPrix() {
        return prix;
    }

    public int getIdentifiant() {
        return identifiant;
    }

    public String getCouleur() {
        return couleur;
    }

    public String getTaille() {
        return taille;
    }

    public String getTypeTissu() {
        return typeTissu;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public void setCouleur(String couleur) {
        this.couleur = couleur;
    }

    public void setTaille(String taille) {
        this.taille = taille;
    }

    public void setTypeTissu(String typeTissu) {
        this.typeTissu = typeTissu;
    }

    public void setIdentifiant(int identifiant) {
        this.identifiant = identifiant;
    }
}
