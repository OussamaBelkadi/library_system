package service;

import Query.LibreDao.LivreDao;
import Query.LibreDao.QueryLivre;
import entite.Livre;
import utilite.DataBase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdminService {
    DataBase dataBase = DataBase.getInstance();
    Connection connection = dataBase.getConnection();
    public int insertLivre(Livre livre){
        int resultInsert = 0;
        try(PreparedStatement InsertLivreDb = connection.prepareStatement(QueryLivre.insertBook())){
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

    public List<Object> getLivre(Livre livre) throws SQLException{
        List<Object> data = new ArrayList<>();
        try(PreparedStatement getBook = connection.prepareStatement(QueryLivre.searchBook())){
            getBook.setString(1, livre.getISBN());
            ResultSet rs=getBook.executeQuery();
            if(rs.next()) {
                data.add(rs.getInt("id"));
                data.add(rs.getString("titre"));
                data.add(rs.getString("auteur"));
                data.add(rs.getInt("quantite"));
                data.add(rs.getString("ISBN"));
                data.add(rs.getString("status"));

            }
        }
        return data;
    }

    public int modifierLivre(Livre livre) throws SQLException{
        int result = 0;
        try(
                PreparedStatement upDate = connection.prepareStatement(QueryLivre.updateBook())){

            upDate.setString(1, livre.getTitre());
            upDate.setString(2, livre.getAuteur());
            upDate.setInt(3, livre.getQuantite());
            upDate.setString(4, livre.getISBN());
            upDate.setInt(5,livre.getId());

            result = upDate.executeUpdate();

        }
        return result;
    }

    public List<Livre> rechercheTitre(Livre livre) throws SQLException{
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
    public List<Livre> rechercheAuteur(Livre livre) throws SQLException{
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

    public int suprimerLivre(Livre livre) throws  SQLException{
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
    public List<Livre> listBookNotAvailible() throws  SQLException{
        LivreDao livreDao = new LivreDao();
        List<Livre> listOfBooks = livreDao.listBookNotAvailable();
        return listOfBooks;
    }
    public List<Livre>  listBookAvailible() throws SQLException {
        LivreDao livreDao = new LivreDao();
        List<Livre> listOfBooks = livreDao.listBookAvailable();
        return listOfBooks;
    }
}
