package entite;

public class Livre {

    private int id;
    private String titre;
    private String auteur;
    private String ISBN;
    private String status;
    private int quantite;

    private int count;


    public void setCount(int count) {
        this.count = count;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setAuteur(String auteur) {
        this.auteur = auteur;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public int getId() {
        return id;
    }

    public String getTitre() {
        return titre;
    }

    public String getAuteur() {
        return auteur;
    }

    public String getStatus() {
        return status;
    }

    public int getQuantite() {
        return quantite;
    }

    public String getISBN() {
        return ISBN;
    }

    public int getCount() {
        return count;
    }
}
