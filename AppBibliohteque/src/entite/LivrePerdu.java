package entite;

public class LivrePerdu {
    private int id;
    private String titre;
    private  String auteur;
    private int idEmprinteur;
    private int idLivre;
    private  String ISBN;

    public void setId(int id) {
        this.id = id;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public void setAuteur(String auteur) {
        this.auteur = auteur;
    }

    public void setIdEmprinteur(int idEmprinteur) {
        this.idEmprinteur = idEmprinteur;
    }

    public void setIdLivre(int idLivre) {
        this.idLivre = idLivre;
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

    public int getIdEmprinteur() {
        return idEmprinteur;
    }

    public int getIdLivre() {
        return idLivre;
    }

    public void setISBN(String isbn) {
        this.ISBN = isbn;
    }

    public String getISBN() {
        return ISBN;
    }
}
