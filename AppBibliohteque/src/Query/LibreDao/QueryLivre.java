package Query.LibreDao;

public class QueryLivre {

    public static String listBooks(){
        return "SELECT * FROM livres";
    }
    public static String insertBook(){

        return "INSERT INTO livres (titre,auteur,quantite,ISBN,status,statusPerdus) VALUES (?,?,?,?,'Disponible',0)";
    }

    public static String searchBook(){
        return  "SELECT * FROM `livres` where ISBN=?";
    }
    public static String listBookOfStatus(){
        return "SELECT * FROM `livres` where status=?";
    }

    public static String selectBookId(){
        return "SELECT `id` FROM `livres` WHERE ISBN=?;";
    }
    public static String selectBookStatus(){
        return "SELECT `status` FROM `livres` WHERE ISBN=?;";
    }
    public static String searchBookReserve(){return "SELECT * FROM `livres` WHERE id=?";}
    public static String searchByTitle(){return "SELECT * FROM `livres` WHERE titre=?";}
    public static String searchByAuteur(){return "SELECT * FROM `livres` WHERE auteur=?";}

    public static String updateBook(){
        return "UPDATE livres SET titre=?, auteur=?, quantite=?, ISBN=? WHERE id=?";
    }

    public static String deleteBook(){return "DELETE FROM `livres`  WHERE `id` =?";}

    public static  String statistic(){return "Select status, COUNT(*) as count FROM livres GROUP BY status ORDER BY status";}
}
