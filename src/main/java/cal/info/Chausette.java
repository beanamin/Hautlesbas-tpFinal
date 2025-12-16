package cal.info;

public class Chausette {
    private int id;
    private String couleur;
    private String taille;
    private String typeTissu;
    private double prix;

    public Chausette(int id, String couleur, String taille, String typeTissu, double prix){
        this.id = id;
        this.couleur = couleur;
        this.taille = taille;
        this.typeTissu = typeTissu;
        this.prix = prix;
    }
    public Chausette(){}

    public double getPrix() {
        return prix;
    }

    public int getId() {
        return id;
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

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object obj){
        // si l'objet est comparé à lui-même
        if (this == obj) return true;

        // si l'objet comparé est null ou de type différent
        if (obj == null || getClass() != obj.getClass()) return false;

        // Alors, on peut convertir l'objet en Etudiant
        Chausette chausette = (Chausette) obj;

        // retourner vrai si le matricule et le nom de l'etudiant sont les mêmes
        return id == chausette.getId() && couleur.equals(chausette.getCouleur()) && prix == chausette.getPrix() && taille.equals(chausette.getTaille()) && typeTissu.equals(chausette.getTypeTissu());
    }
}
