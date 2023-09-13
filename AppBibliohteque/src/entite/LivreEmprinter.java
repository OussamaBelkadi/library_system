package entite;

import java.time.LocalDate;

public class LivreEmprinter {
    private LocalDate dateEmprunt;
    private LocalDate dateRetour;
    private int idEnprunteur;
    private int idLivre;

    public void setIdLivre(int idLivre) {
        this.idLivre = idLivre;
    }

    public void setIdEnprunteur(int idEnprunteur) {
        this.idEnprunteur = idEnprunteur;
    }

    public void setDateEmprunt(LocalDate dateEmprunt) {
        this.dateEmprunt = dateEmprunt;
    }

    public void setDateRetour(LocalDate dateRetour) {
        this.dateRetour = dateRetour;
    }

    public int getIdLivre() {
        return idLivre;
    }

    public int getIdEnprunteur() {
        return idEnprunteur;
    }

    public LocalDate getDateEmprunt() {
        return dateEmprunt;
    }

    public LocalDate getDateRetour() {
        return dateRetour;
    }
}
