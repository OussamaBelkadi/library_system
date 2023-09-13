package Query.Bibliothecaire;

public class QueryBibiliothecaire {

    public static String insertBibliothecaire(){
        return "Insert into bibliothecaire (nom, motPass) VALUES (?, ?)";
    }
}
