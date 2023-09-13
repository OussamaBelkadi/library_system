package Query.Emprunteur;

public class QueryEmprinteur {

    public static String insertEmprinteur(){
        return "Insert into `emprinteurs` (nom,cleEmprinteur) VALUES (?,?)";
    }
    public static String getEmprinteurId(){
        return "SELECT `id` FROM `emprinteurs` WHERE cleEmprinteur=?;";
    }
    public static String getEmprinteurCle(){
        return "SELECT `id` FROM `emprinteurs` WHERE cleEmprinteur=?;";
    }
    public static String getEmprinteurExist(){
        return "SELECT `id` FROM `emprinteurs` WHERE  nom=? AND cleEmprinteur=? ;";
    }
    public static String deleteEmprinteur(){
        return "DELETE FROM `emprinteur` WHERE `id` = ?";
    }

    public static String queryReserved(){
        return "SELECT COUNT(*) AS con ,idLivre  FROM livresemprinter le INNER JOIN emprinteurs e ON le.idEmprinteur=e.id WHERE e.id=?;";
    }
}
