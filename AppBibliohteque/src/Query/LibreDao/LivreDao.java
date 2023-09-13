package Query.LibreDao;

import entite.Livre;
import utilite.DataBase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LivreDao {

    DataBase dataBase = DataBase.getInstance();
    Connection connection = dataBase.getConnection();

//    Recuperation d'un ID d'un livre precis
    public int getIdLivre(Livre livre) throws  SQLException{
        int result = 0;
        try(PreparedStatement idLivre = connection.prepareStatement(QueryLivre.selectBookId())){
           idLivre.setString(1,livre.getISBN());
            ResultSet resultSet = idLivre.executeQuery();
            while (resultSet.next()){
                result=resultSet.getInt("id");
            };

        }
        return result;
    }

//    list des livre dans la base des donnes
    public List<Livre> listLivres() throws SQLException {
        List<Livre> data = new ArrayList<>();
        try(
            PreparedStatement listLivreDb = connection.prepareStatement(QueryLivre.listBooks())){
            ResultSet rs = listLivreDb.executeQuery();
            while(rs.next()){
                Livre livre = new Livre();
                livre.setTitre(rs.getString("titre"));
                livre.setAuteur(rs.getString("auteur"));
                livre.setQuantite(rs.getInt("quantite"));
                livre.setStatus(rs.getString("status"));
                livre.setISBN(rs.getString("ISBN"));
                data.add(livre);
            }
        }
        return data;
    }

//    Insertion des livres dans la base de donne.
    public int insertLivre(Livre livre) throws SQLException {
        int resultInsert = 0;
        try(
                PreparedStatement InsertLivreDb = connection.prepareStatement(QueryLivre.insertBook())){
            InsertLivreDb.setString(1, livre.getTitre());
            InsertLivreDb.setString(2, livre.getAuteur());
            InsertLivreDb.setInt(3, livre.getQuantite());
            InsertLivreDb.setString(4, livre.getISBN());
            resultInsert = InsertLivreDb.executeUpdate();
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
        return resultInsert;
    }

//    Recuperer les donnees d'un livre
    public List<Object> getBook(Livre livre) throws SQLException{
        List<Object> data = new ArrayList<>();
        try(
            PreparedStatement getBook = connection.prepareStatement(QueryLivre.searchBook())){
            getBook.setString(1, livre.getISBN());
             ResultSet rs=getBook.executeQuery();
            if(rs.next()) {
                data.add(rs.getInt("id"));
                data.add(rs.getString("titre"));
                data.add(rs.getString("auteur"));
                data.add(rs.getInt("quantite"));
                data.add(rs.getString("ISBN"));

            }
        }
        return data;
    }

//    La recherche d'un livre du la base des donnees
    public List<Livre> search(Livre livre) throws SQLException{
        List<Livre> data = new ArrayList<>();
        try(
            PreparedStatement getBook = connection.prepareStatement(QueryLivre.searchBook())){
            getBook.setString(1, livre.getISBN());
            ResultSet rs = getBook.executeQuery();

            if(rs.next()) {
                    livre.setTitre(rs.getString("titre"));
                    livre.setAuteur(rs.getString("auteur"));
                    livre.setStatus(rs.getString("status"));
                    livre.setQuantite(rs.getInt("quantite"));
                    livre.setISBN(rs.getString("ISBN"));
                    data.add(livre);
            }

        }
        return data;
    }
    public List<Livre> searchBy(Livre livre) throws SQLException{
        if (!livre.getTitre().isEmpty()){
            return searchByTitre(livre);
        }
        return null;
    }
    public List<Livre> searchByTitre(Livre livre) throws SQLException{
        List<Livre> data = new ArrayList<>();
        try(
                PreparedStatement getBook = connection.prepareStatement(QueryLivre.searchByTitle())){
            getBook.setString(1, livre.getTitre());
            ResultSet rs = getBook.executeQuery();

            if(rs.next()) {
                livre.setTitre(rs.getString("titre"));
                livre.setAuteur(rs.getString("auteur"));
                livre.setStatus(rs.getString("status"));
                livre.setQuantite(rs.getInt("quantite"));
                livre.setISBN(rs.getString("ISBN"));
                data.add(livre);
            }

        }
        return data;
    }
    public List<Livre> searchByAuteur(Livre livre) throws SQLException{
        List<Livre> data = new ArrayList<>();
        try(
                PreparedStatement getBook = connection.prepareStatement(QueryLivre.searchByAuteur())){
            getBook.setString(1, livre.getAuteur());
            ResultSet rs = getBook.executeQuery();

            if(rs.next()) {
                livre.setTitre(rs.getString("titre"));
                livre.setAuteur(rs.getString("auteur"));
                livre.setStatus(rs.getString("status"));
                livre.setQuantite(rs.getInt("quantite"));
                livre.setISBN(rs.getString("ISBN"));
                data.add(livre);
            }

        }
        return data;
    }

//    Rechaerche simple d'un livre reserver
    public List<Livre> searchReserved(Livre livre) throws SQLException{
        List<Livre> data = new ArrayList<>();
        try(PreparedStatement getBook = connection.prepareStatement(QueryLivre.searchBookReserve())){
            getBook.setInt(1, livre.getId());
            boolean test = getBook.execute();
            if(test) {
                ResultSet rs = getBook.executeQuery();

                while (rs.next()) {
                    livre.setTitre(rs.getString("titre"));
                    livre.setAuteur(rs.getString("auteur"));
                    livre.setStatus(rs.getString("status"));
                    livre.setQuantite(rs.getInt("quantite"));
                    livre.setISBN(rs.getString("ISBN"));
                    data.add(livre);
                }
            }else {
                System.out.println("Ce livre est");
            }
        }
        return data;
    }

//    Supprition d'un livre du la base des donnees
    public int deleteBook(Livre livre) throws  SQLException{
        int result = 0;
        try(
        PreparedStatement getBook = connection.prepareStatement(QueryLivre.selectBookId())){
            getBook.setString(1, livre.getISBN());

            ResultSet rs = getBook.executeQuery();
            if (rs.next()){

                int id = rs.getInt("id");

                    PreparedStatement supBook = connection.prepareStatement(QueryLivre.deleteBook());
                    supBook.setInt(1, id);
                    result = supBook.executeUpdate();
            }
            return result;
        }
    }

//    Modification  des donnees d'un livre
    public void upDateBook(Livre livre) throws SQLException{
        try(
                PreparedStatement upDate = connection.prepareStatement(QueryLivre.updateBook())){

            upDate.setString(1, livre.getTitre());
            upDate.setString(2, livre.getAuteur());
            upDate.setInt(3, livre.getQuantite());
            upDate.setString(4, livre.getISBN());
            upDate.setInt(5,livre.getId());

            int rsUpdate = upDate.executeUpdate();
            if (rsUpdate > 0){
                System.out.println("Le livre est modifier");
            }else {
                System.out.println("l'oppersation de modification n'est complet");
            }
        }
    }

//    Affichge des livre selon leur Status
    public List<Livre> listBookAvailable() throws SQLException {
        List<Livre> data = new ArrayList<>();
        try(PreparedStatement listLivreDb = connection.prepareStatement(QueryLivre.listBookOfStatus())){
            listLivreDb.setString(1, "Disponible");
            ResultSet resultSet = listLivreDb.executeQuery();
            while (resultSet.next()){
                String title = resultSet.getString("titre");
                String author = resultSet.getString("auteur");
                String isbn = resultSet.getString("ISBN");
                int quantite = resultSet.getInt("quantite");
                String status = resultSet.getString("status");
                Livre livre = new Livre();
                livre.setTitre(title);
                livre.setAuteur(author);
                livre.setQuantite(quantite);
                livre.setISBN(isbn);
                livre.setStatus(status);
                data.add(livre);


            }
        }
        return data;
    }
    public List<Livre> listBookNotAvailable() throws SQLException {
        List<Livre> data = new ArrayList<>();
        try(PreparedStatement listLivreDb = connection.prepareStatement(QueryLivre.listBookOfStatus())){
            listLivreDb.setString(1, "Indisponible");
            ResultSet resultSet = listLivreDb.executeQuery();
            while (resultSet.next()){
                String title = resultSet.getString("titre");
                String author = resultSet.getString("auteur");
                String isbn = resultSet.getString("ISBN");
                int quantite = resultSet.getInt("quantite");
                String status = resultSet.getString("status");
                Livre livre = new Livre();
                livre.setTitre(title);
                livre.setAuteur(author);
                livre.setQuantite(quantite);
                livre.setISBN(isbn);
                livre.setStatus(status);
                data.add(livre);


            }
        }
        return data;
    }

    public List<Livre> statisticBook() throws SQLException {
        List<Livre> data = new ArrayList<>();
        try(PreparedStatement listLivreDb = connection.prepareStatement(QueryLivre.statistic())){

            ResultSet resultSet = listLivreDb.executeQuery();
            while (resultSet.next()){

                int count = resultSet.getInt("count");
                String status = resultSet.getString("status");
                Livre livre = new Livre();
                livre.setCount(count);
                livre.setStatus(status);
                data.add(livre);


            }
        }
        return data;
    }
}
