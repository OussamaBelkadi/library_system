package entite;

public class Emprinteur {
    private int id;
    private String nom;
    private String cleEmprinteur;

    public void setId(int id) {
        this.id = id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setCleEmprinteur(String cleEmprinteur) {
        this.cleEmprinteur = cleEmprinteur;
    }

    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getCleEmprinteur() {
        return cleEmprinteur;
    }
}

