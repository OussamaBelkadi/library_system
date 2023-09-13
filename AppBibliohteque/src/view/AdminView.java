package view;

import Controller.MenuPrinciple.AdminController;
import entite.Livre;
import rac.ResultRac;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class AdminView implements LibraryView {
    private final AdminController adminController;
    private final Livre livre;
    Scanner scanner = new Scanner(System.in);

    public AdminView(AdminController adminController , Livre livre) {
        this.adminController = adminController;
        this.livre = livre;
    }

    public void desplayMenu() throws SQLException {
        while(true) {
            System.out.println("---------------------**************---------------------");
            System.out.println("Bienvenus dans l'application de gestion du biblipotheque partie ADMIN");
            System.out.println("---------------------**************---------------------");
            System.out.println("\tChosie l'option qure vous convient:");
            System.out.println("\t\t1. Ajouter Livre");
            System.out.println("\t\t2. Modifier Livre");
            System.out.println("\t\t3. List Livre Disponnible");
            System.out.println("\t\t4. List Livre Indisponnible");
            System.out.println("\t\t5. Recherche d'un livre");
            System.out.println("\t\t6. Suprition  Livre");
            System.out.println("\t\t7. Statisatic");
            System.out.print("Enter votre choix: ");
            int choix = scanner.nextInt();
            scanner.nextLine();
            switch (choix){
                case 1 -> formInsert();
                case 2 -> modifierLivre();
                case 3 -> listDisponibleLivre();
                case 4 -> listNoDisponibleLivre();
                case 5 -> rechercheLivre();
                case 6 -> suprimerLivre();
                case 7 -> statisticLivreBiblio();



            }

        }
    }
    public void listDisponibleLivre() throws SQLException {
        List<Livre> listLivre = adminController.listLivreDisponible();
        ResultRac.resultExecution(listLivre);
    }
    public void listNoDisponibleLivre() throws SQLException {
        List<Livre> listLivre = adminController.ListLivreNoDisponible();
        ResultRac.resultExecution(listLivre);
    }
    public void formInsert(){

        try{
            System.out.println("\tCreation d'un nouveau livre");
            System.out.println("----------------------------");
            System.out.println("Entrer le titre de livre");
            livre.setTitre(scanner.nextLine());
            System.out.println("Entrer l'auteur du livre");
            livre.setAuteur(scanner.nextLine());
            System.out.println("Entrer le ISBN");
            livre.setISBN(scanner.nextLine());
            System.out.println("Entrer la quantite disponible dans la bibliotheque");

            livre.setQuantite(scanner.nextInt());
            int result = adminController.insertLivre(livre);
            if (result != 0){
                System.out.println("----------------------============---------------------------");
                System.out.println("\t\t\t\tLe livre est insert");
                System.out.println("----------------------============---------------------------");
                desplayMenu();

            }else {
                System.out.println("----------------------============----------------------------");
                System.out.println("\t\t\t\tLe livre n'est pas enregistrer");
                System.out.println("----------------------============----------------------------");
                desplayMenu();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public void modifierLivre() throws SQLException {
        System.out.print("enter le code de livre ISBN: ");
        livre.setISBN(scanner.nextLine());
        boolean rs = adminController.getLivre(livre);
        System.out.println("-----------------------------------");
        System.out.println("\t\tDonnees Livre");
        System.out.println("-----------------------------------");
        System.out.println("Titre: "+livre.getTitre());
        System.out.println(" l'auteur : "+livre.getAuteur());
        System.out.println(" ISBN: "+livre.getISBN());
        System.out.println(" Quantite: "+livre.getQuantite());
        System.out.println(" Status: "+livre.getStatus());
        if (rs) {
            System.out.println("-----------------------------------------");
            System.out.println("\t\tModification des donnees");
            System.out.println("-----------------------------------------");
            System.out.print("Entrer le titre de livre : ");
            String titre = scanner.nextLine();
            if (!titre.isEmpty()) {
                livre.setTitre(titre);
            }
            System.out.print("Entrer l'auteur du livre : ");
            String auteur = scanner.nextLine();
            if (!auteur.isEmpty()) {
                livre.setAuteur(auteur);
            }
            System.out.print("Entrer le ISBN : ");
            String ISBN = scanner.nextLine();
            if (!ISBN.isEmpty()) {
                livre.setISBN(ISBN);
            }
            System.out.print("Entrer la quantite disponible dans la bibliotheque : ");
            String qt = scanner.nextLine();
            if (!qt.isEmpty()) {
                int quantite = Integer.parseInt(qt);
                livre.setQuantite(quantite);
            }
            int result = adminController.modifierLivre(livre);
            if (result != 0) {
                System.out.println("=======================================================");
                System.out.println("\tL livre qui contient ISBN: " + livre.getISBN() + " est modifier avec succes");
                System.out.println("=======================================================");
                desplayMenu();

            } else {
                System.out.println("=======================================================");
                System.out.println("\t\tLe livre n'a pas ete modifier ");
                System.out.println("=======================================================");
                desplayMenu();
            }
        }else {
            System.out.println("--------------------**********----------------------");
            System.out.println("\t\tIl n'a pas de livre avec le SBIN: "+livre.getISBN());
            System.out.println("--------------------**********----------------------");
            desplayMenu();
        }
    }

    public  void rechercheLivre() throws  SQLException{

        System.out.println("-------------------*********************--------------------");
        System.out.println("\t\tRechercher votre selon votre soit par titre ou auteur");
        System.out.println("-------------------*********************--------------------");
        System.out.println("\t1- Par Titre: ");
        System.out.println("\t2- Par Auteur: ");
        System.out.print("Emtrer votre choix: ");
        int choix = scanner.nextInt();

        if (choix == 1) {
            System.out.print("Enter le titre du livre: ");
            scanner.nextLine();
            livre.setTitre(scanner.nextLine());

            if (!livre.getTitre().isEmpty()){
                List<Livre> listOfBooks = adminController.rechercheTitre(livre);
                if (!listOfBooks.isEmpty()){
                    ResultRac.resultExecution(listOfBooks);
                    desplayMenu();

                }else {
                    System.out.println("=======================================================");
                    System.out.println("\t\tIl n'a pas un livre qui contient titre: "+livre.getTitre());
                    System.out.println("=======================================================");
                    desplayMenu();
                }
            }
        }else if (choix == 2) {
            System.out.println("Enter le auteur du livre: ");

            scanner.nextLine();
            livre.setAuteur(scanner.nextLine());
            if (!livre.getAuteur().isEmpty()){
                List<Livre> listOfBooks = adminController.rechercheAuteur(livre);
                if (!listOfBooks.isEmpty()){
                    ResultRac.resultExecution(listOfBooks);
                    desplayMenu();

                }else {
                    System.out.println("=======================================================");
                    System.out.println("\t\tIl n'a pas un livre sont auteur est: "+livre.getAuteur());
                    System.out.println("=======================================================");
                    desplayMenu();

                }
            }
        }else {
            System.out.println("le choix est incorrect");
            desplayMenu();

        }
    }
    public void suprimerLivre() throws SQLException {
        try {
            System.out.println("--------------**********----------------");
            System.out.println("Enter ISBN:");
            livre.setISBN(scanner.nextLine());
            System.out.println("--------------**********----------------");

            int result = adminController.suprimerLivre(livre);
            if (result != 0) {
                System.out.println("----------------------============----------------------------");
                System.out.println("\tLe livre qu'il a le ISBN " + livre.getISBN() + " est supprimer ");
                System.out.println("----------------------============----------------------------");
                desplayMenu();
            } else {
                System.out.println("----------------------============----------------------------");
                System.out.println("\t\tIl n'a pas un livre avec ISBN " + livre.getISBN());
                System.out.println("----------------------============----------------------------");
                desplayMenu();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
        public void statisticLivreBiblio() throws SQLException {
            List<Livre> listOfData = adminController.statisticLivre();
            System.out.println("-------------------*************---------------------");
            System.out.println("\t\tStatistic Selon status livre");
            System.out.println("-------------------*************---------------------");
            for (Livre statisticBook : listOfData) {

                System.out.println("-------------------*************---------------------");

                System.out.print("\t\t" + statisticBook.getStatus() + ":  ");
                System.out.print(statisticBook.getCount() + "\n");
                System.out.println("-------------------*************---------------------");
            }

    }

}
