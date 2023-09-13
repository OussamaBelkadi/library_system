package Query.LivrePerdu;

public class QueryLivrePerdu {
    public static String insertLivre(){return "INSERT INTO `livreperdu` (`titre`, `auteur`, `ISBN`, `idEmprinteur`, `idLivre`) VALUES  (?,?,?,?,?)";}
    public static String changeQuantite(){return "UPDATE livres SET quantite= quantite-1 WHERE id=?;";}

    public static String changeStatuschangeQuantite(){return "UPDATE livres SET quantite= 0, statusPerdus=1 WHERE id=? AND quantite=1;";}
}
