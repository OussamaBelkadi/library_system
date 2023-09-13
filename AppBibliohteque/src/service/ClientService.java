package service;

import Query.Emprunteur.QueryEmprinteur;
import Query.LibreDao.LivreDao;
import Query.LibreDao.QueryLivre;
import Query.LivreEmprinter.QueryLivreEmprinter;
import Query.LivrePerdu.QueryLivrePerdu;
import entite.Emprinteur;
import entite.Livre;
import entite.LivreEmprinter;
import utilite.DataBase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClientService {
    private final Emprinteur emprinteur;
    private final LivreEmprinter livreEmprinter;
    private final Livre livre;
    DataBase dataBase = DataBase.getInstance();
    Connection connection = dataBase.getConnection();
    public ClientService(Emprinteur emprinteur,LivreEmprinter livreEmprinter,Livre livre){
        this.emprinteur = emprinteur;
        this.livre = livre;
        this.livreEmprinter = livreEmprinter;
    }
    public List<Livre>  listBookAvailible() throws SQLException {
        LivreDao livreDao = new LivreDao();
         List<Livre> listOfBooks = livreDao.listBookAvailable();
         return listOfBooks;
    }
    public int searchEmprinteur(){
        int rs;
        try(PreparedStatement searchEmp = connection.prepareStatement(QueryEmprinteur.getEmprinteurExist())){
            searchEmp.setString(1, emprinteur.getNom());
            searchEmp.setString(2, emprinteur.getCleEmprinteur());
            ResultSet resultSet = searchEmp.executeQuery();
            if(resultSet.next()){
                rs = resultSet.getInt("id");
            }else {
                rs = 0;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return rs;
    }
    public int insertEmprintLivre() throws SQLException {
        int result=0;
        try(
                PreparedStatement inserLivreEmp = connection.prepareStatement(QueryLivreEmprinter.insertLivreEmprinter());){
            inserLivreEmp.setDate(1, java.sql.Date.valueOf(livreEmprinter.getDateEmprunt()));
            inserLivreEmp.setDate(2, java.sql.Date.valueOf(livreEmprinter.getDateRetour()));
            inserLivreEmp.setInt(3, livre.getId());
            inserLivreEmp.setInt(4, emprinteur.getId());
            result = inserLivreEmp.executeUpdate();


        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }

        return result;
    }
    public List<Livre> rechercheReserver() throws SQLException{
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
                System.out.println("Ce livre est vide");
            }
        }
        return data;
    }

    public List<Integer> livreReserver() throws SQLException {
        List <Integer> data = new ArrayList<>();
        try (PreparedStatement reserved = connection.prepareStatement(QueryEmprinteur.queryReserved())) {
            reserved.setInt(1, emprinteur.getId());
            ResultSet result = reserved.executeQuery();
            while (result.next()) {
                int numReserve = result.getInt(1);
                int idlivre =  result.getInt(2);
                data.add(numReserve);
                data.add(idlivre);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return data;
    }
    public int getIdLivre() throws  SQLException{
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
    public String livreDisponible() throws  SQLException{
        String result ="null";
        try(PreparedStatement idLivre = connection.prepareStatement(QueryLivre.selectBookStatus())){
            idLivre.setString(1,livre.getISBN());
            ResultSet resultSet = idLivre.executeQuery();
            while (resultSet.next()){
                result=resultSet.getString("status");
            };

        }
        return result;
    }
    public int searchEmprinteurCle(){

        int rs;
        try(PreparedStatement searchEmp = connection.prepareStatement(QueryEmprinteur.getEmprinteurCle())){
            searchEmp.setString(1,emprinteur.getCleEmprinteur());
            ResultSet resultSet = searchEmp.executeQuery();
            if(resultSet.next()){
                rs = resultSet.getInt("id");
            }else {
                rs = 0;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return rs;
    }
    public int insertEmprinteur() throws SQLException {
        int resultfin = 0;
        try(
                PreparedStatement insertEmprunt = connection.prepareStatement(QueryEmprinteur.insertEmprinteur())){
            insertEmprunt.setString(1, emprinteur.getNom());
            insertEmprunt.setString(2, emprinteur.getCleEmprinteur());
            int rs = insertEmprunt.executeUpdate();
            if (rs > 0){
                PreparedStatement selectId = connection.prepareStatement(QueryEmprinteur.getEmprinteurId());
                selectId.setString(1, emprinteur.getCleEmprinteur());
                ResultSet result = selectId.executeQuery();
                while(result.next()){
                    resultfin = result.getInt("id");
                }
            }else {
                System.out.println("E");
            }
            return resultfin;
        }
    }

    public void deleteEmprinteur(Emprinteur emprinteur) throws SQLException {
        int rs = 0;
        try(PreparedStatement deleteEmprinteur = connection.prepareStatement(QueryEmprinteur.deleteEmprinteur())){
            deleteEmprinteur.setInt(1, emprinteur.getId());
            rs = deleteEmprinteur.executeUpdate();
        }
    }
    public int returnBookLoaded() throws SQLException{
        int result = 0;
        try(
                PreparedStatement inserLivreEmp = connection.prepareStatement(QueryLivreEmprinter.deleteLivreEmprinter());){
            inserLivreEmp.setInt(1, livre.getId());
            inserLivreEmp.setInt(2, emprinteur.getId());
            result = inserLivreEmp.executeUpdate();

        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
        return result;
    }

    public void enregistrerLivrePredu() throws SQLException {
        try(PreparedStatement preparedStatement = connection.prepareStatement(QueryLivre.searchBookReserve())){
            preparedStatement.setInt(1, livre.getId());
            preparedStatement.executeUpdate();
            InsertLivrePerdu();
        }
    }
    public void InsertLivrePerdu() throws SQLException {
        try(PreparedStatement preparedStatement = connection.prepareStatement(QueryLivrePerdu.insertLivre())){
            preparedStatement.setString(1, livre.getTitre());
            preparedStatement.setString(2, livre.getAuteur());
            preparedStatement.setString(3, livre.getISBN());

            preparedStatement.executeUpdate();

        }
    }


}
