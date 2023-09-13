import Controller.MenuPrinciple.AdminController;
import Controller.MenuPrinciple.ClientController;

import Query.LibreDao.QueryLivre;
import Query.LivreEmprinter.QueryLivreEmprinter;
import Query.LivrePerdu.QueryLivrePerdu;
import entite.Emprinteur;
import entite.Livre;
import entite.LivreEmprinter;
import entite.LivrePerdu;
import service.AdminService;
import service.ClientService;
import utilite.DataBase;
import view.AdminView;
import view.ClientView;
import view.LibraryView;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {



    public static void main(String[] args) throws SQLException {

        // Create a ScheduledExecutorService with a single thread
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

        // Schedule the task to run every 2 hours
        scheduler.scheduleAtFixedRate(() -> {
            try {
                // Call your method here
                rechercheLivrePerdu();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }, 0, 2, TimeUnit.HOURS);

        // Calculate the initial delay until the first execution

        DesplayMenu();

    }
    public  static void DesplayMenu() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        AdminService adminService = new AdminService();
        Livre livre = new Livre();
        LivreEmprinter livreEmprinter = new LivreEmprinter();

        Emprinteur emprinteur = new Emprinteur();
        ClientService clientService = new ClientService(emprinteur,livreEmprinter,livre);
        // You can keep the program running as long as you need, or you can shut it down when done.
        while(true) {
            System.out.println("---------------------**************---------------------");
            System.out.println("\tBienvenus dans l'application de gestion du biblipotheque");
            System.out.println("---------------------**************---------------------");
            System.out.println("Chosie l'option qure vous convient: ");
            System.out.println("\t1. Bibliothecaire");
            System.out.println("\t2. Emprinteur");
            System.out.println("\t3. Sortir \n");
            System.out.print("Enter votre choix: ");
            int choix = scanner.nextInt();
            scanner.nextLine();
            switch(choix){
                case 1 -> {
                    LibraryView adminView = new AdminView(new AdminController(livre,adminService), livre);
                    adminView.desplayMenu();
                }
                case 2 ->{
                    LibraryView clientView = new ClientView(livre, livreEmprinter, emprinteur,new ClientController(clientService, livreEmprinter));
                    clientView.desplayMenu();
                }

                case 3 ->{
                    DataBase dataBase1 = DataBase.getInstance();
                    System.out.println("Aurevoir");
                    dataBase1.closeConnection();
                    scanner.close();
                    System.exit(0);

                }

            }

        }
    }
    public  static void rechercheLivrePerdu() throws SQLException {
        DataBase dataBase = DataBase.getInstance();
        Connection connection = dataBase.getConnection();
        Livre livre = new Livre();
        Emprinteur emprinteur = new Emprinteur();
        LivrePerdu livrePerdu = new LivrePerdu();
        try(PreparedStatement recherchePerdu = connection.prepareStatement(QueryLivreEmprinter.rechercheLivrePerdu())){
            ResultSet resultSet = recherchePerdu.executeQuery();
            if (resultSet.next()) {

                livre.setId(resultSet.getInt("idLivre"));
                emprinteur.setId(resultSet.getInt("IdEmprinteur"));


                PreparedStatement preparedStatement = connection.prepareStatement(QueryLivre.searchBookReserve());
                preparedStatement.setInt(1, livre.getId());
                ResultSet resultSet1 = preparedStatement.executeQuery();
                while(resultSet1.next()){
                    livrePerdu.setIdLivre(livre.getId());
                    livrePerdu.setTitre(resultSet1.getString(2));
                    livrePerdu.setAuteur(resultSet1.getString(3));
                    livrePerdu.setIdEmprinteur(emprinteur.getId());
                    livrePerdu.setISBN(resultSet1.getString("ISBN"));
                }
                PreparedStatement insertLivrePerdus = connection.prepareStatement(QueryLivrePerdu.insertLivre());
                insertLivrePerdus.setString(1, livrePerdu.getTitre());
                insertLivrePerdus.setString(2, livrePerdu.getAuteur());
                insertLivrePerdus.setString(3, livrePerdu.getISBN());
                insertLivrePerdus.setInt(4, livrePerdu.getIdEmprinteur());
                insertLivrePerdus.setInt(5, livrePerdu.getIdLivre());
                 int resultLivre = insertLivrePerdus.executeUpdate();
                if(resultLivre != 0){
                    PreparedStatement supLivrePerdus = connection.prepareStatement(QueryLivreEmprinter.deleteLivreEmprinter());
                    supLivrePerdus.setInt(1, livrePerdu.getIdLivre());
                    supLivrePerdus.setInt(2, livrePerdu.getIdEmprinteur());
                    supLivrePerdus.executeUpdate();

                    PreparedStatement rechercheLivre = connection.prepareCall(QueryLivreEmprinter.rechercheLivre());
                    rechercheLivre.setInt(1, livrePerdu.getIdLivre());
                    ResultSet resultSet2 = rechercheLivre.executeQuery();
                    if(resultSet2.next()){
                        int existLivreEmprinter = resultSet2.getInt("idLivre");
                        if (existLivreEmprinter > 0){
                            PreparedStatement modifieQuantite= connection.prepareStatement(QueryLivrePerdu.changeQuantite());
                            modifieQuantite.setInt(1, livrePerdu.getIdLivre());
                            modifieQuantite.executeUpdate();
                        }
                    }else {
                        PreparedStatement modifieQuantite= connection.prepareStatement(QueryLivrePerdu.changeStatuschangeQuantite());
                        modifieQuantite.setInt(1, livrePerdu.getIdLivre());
                        modifieQuantite.executeUpdate();
                    }

                }
            }
        }
    }

}