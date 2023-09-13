package Query.LivreEmprinter;


public class QueryLivreEmprinter {

    public static String insertLivreEmprinter(){
        return "INSERT INTO `livresemprinter`(`dateEmprunt`, `deteRetour`, `idLivre`, `idEmprinteur`) VALUES (?,?,?,?)";
    }
    public static String deleteLivreEmprinter(){
        return  "DELETE FROM `livresemprinter` WHERE idLivre=? AND idEmprinteur=?";
    }

    public static String rechercheLivre(){
        return  "SELECT idlivre  FROM livresemprinter WHERE idLivre = ? LIMIT 1";
    }
    public static String rechercheLivrePerdu(){
        return  "SELECT idLivre ,IdEmprinteur FROM livresemprinter WHERE deteRetour < CURDATE() LIMIT 1";
    }
}
