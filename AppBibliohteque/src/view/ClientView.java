package view;

import Controller.MenuPrinciple.ClientController;

import entite.Emprinteur;
import entite.Livre;
import entite.LivreEmprinter;
import rac.ResultRac;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class ClientView implements LibraryView{
    Scanner scanner = new Scanner(System.in);
    private final Livre livre;
    private final LivreEmprinter livreEmprinter;
    private final Emprinteur emprinteur;
    private final ClientController clientController;

    private static boolean isValidString(String input) {
        return !input.isEmpty() && input.matches("^[a-zA-Z ]+[0-9]+$");
    }

    private static boolean isValidDate(String Date){
        return Date.matches("\\d{4}-\\d{2}-\\d{2}");
    }
    public ClientView(Livre livre,LivreEmprinter livreEmprinter,Emprinteur emprinteur,ClientController clientController){
        this.livre = livre;
        this.livreEmprinter = livreEmprinter;
        this.emprinteur = emprinteur;
        this.clientController =  clientController;
    }
    @Override
    public void desplayMenu() throws SQLException {
        while(true) {
            System.out.println("---------------------**************---------------------");
            System.out.println("Bienvenus dans l'application de gestion du biblipotheque partie Client");
            System.out.println("---------------------**************---------------------");
            System.out.println("\tChosie l'option qure vous convient:");
            System.out.println("\t\t1. Livre desponible dans la bibliotheque");
            System.out.println("\t\t2. Reserver Livre");
            System.out.println("\t\t3. Returner Livre");
            System.out.println("\t\t4. Return Mune principale");

            System.out.print("Enter votre choix: ");
            int choix = scanner.nextInt();
            scanner.nextLine();
            switch (choix){
                case 1 -> {
                    try {
                        listLivreDisponible();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
                case 2 -> reservelivre();
                case 3 -> returnerLivre();

            }

        }
    }

    public void listLivreDisponible() throws SQLException {
        List<Livre> listOfLivre = clientController.listLivreDisponible();
        ResultRac.resultExecution(listOfLivre);

    }
    public String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);
    }
    public void reservelivre(){
        try{

            System.out.println("------------------------------");
            System.out.println("\tRealiser un emprint");
            System.out.println("-------------------------------");
            System.out.print("Etes vous un membre de la bibliotheque (oui/non): ");
            String reponse = scanner.nextLine();
            String oui = "oui";
            if(reponse.equalsIgnoreCase(oui)){
                clientEmprinterExist();
            } else{
                clientEmprinterNotExist();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public void clientEmprinterExist(){
        try{
            System.out.println("\t--------------------------------");
            System.out.println("\t\tBienvenue une autre fois");
            System.out.println("\t------------------------------------");
            System.out.print("Pouvez vous entrer votre nom: ");
            String nom =scanner.nextLine();
            if (isValidString(nom)){
                emprinteur.setNom(nom);
            }else {
                System.out.println("Entrer un correct input");
                reservelivre();
            }



            System.out.print("Pouvez vous entrer votre cle d'identification: ");
            String cle =scanner.nextLine();
            if (isValidString(cle)){
                emprinteur.setCleEmprinteur(cle);
            }else {
                System.out.println("Entrer un correct input");
            }

            emprinteur.setId(clientController.rechercheEmprinteur());
            if (emprinteur.getId() != 0){
                List<Integer> data = clientController.LivreReserver();
                int rs = data.get(0);
                if(rs == 0){
                    System.out.println("---------------------------------");
                    System.out.print("Entrer le code ISBN: ");
                    String ISBN =scanner.nextLine();
                    if (isValidString(cle)){
                        livre.setISBN(ISBN);
                    }else {
                        System.out.println("Entrer un correct input");
                        reservelivre();
                    }

                    livre.setId(clientController.getIdLivre());
                    if(livre.getId() !=0){
                        String disponible = clientController.disponibleLivre();
//                        System.out.println(disponible);
                        if (disponible.equals("Disponible")){
                            System.out.print("Entrer la date de retour: ");
                            String dateRetString = scanner.nextLine();

                            if (isValidDate(dateRetString)){
                                LocalDate parseDateRot = LocalDate.parse(dateRetString);
                                livreEmprinter.setDateRetour(parseDateRot);
                                clientController.insertEmprintLivre();
                            }else {
                                System.out.println("tu dois entrer le format yyyy-mm-jj");
                            }

                            LocalDate parseDateEmp = LocalDate.parse(getDateTime());
                            livreEmprinter.setDateEmprunt(parseDateEmp);

                            System.out.println("--------------------====================--------------------");
                            System.out.println("\t\t\tLe livre est emprinter a ISBN: "+livre.getISBN());
                            System.out.println("--------------------====================--------------------");
                        }else {
                            System.out.println("--------------------====================--------------------");
                            System.out.println("\t\t\tLe livre est indesponible a ISBN: "+livre.getISBN());
                            System.out.println("--------------------====================--------------------");
                        }


                    }else {
                        System.out.println("------------------------===========------------------------");
                        System.out.println("\t\tIl n'a pas se livre avec ISBN: "+ livre.getISBN());
                        System.out.println("------------------------===========------------------------");
                    }
                }else {
                    livre.setId(data.get(1));
                    System.out.println("------------------------===========------------------------");
                    System.out.println("\t\tVous avez déjà reserver un livre ci dessous");
                    System.out.println("------------------------===========------------------------");
                    List<Livre> livreList = clientController.rechercheLivre();
                    for (Livre book : livreList){
                        System.out.println("\t\t"+book.getTitre()+"\t"+book.getAuteur()+"\t"+book.getQuantite()+"\t\t"+book.getISBN()+"\t\t"+book.getStatus());
                        System.out.println("--------------------------------------------------------------");
                    }

                }
            }else {
                System.out.println("-----------------------------------------------------------------------");
                System.out.println("Il a pas un utilisateur avec le nom: " + emprinteur.getNom()+" et un cle: " + emprinteur.getCleEmprinteur());
                System.out.println("-----------------------------------------------------------------------");

            }


        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public void clientEmprinterNotExist() throws SQLException {
        System.out.print("Entrer le nom de l'emprinteur: ");
        String nom =scanner.nextLine();
        if (isValidString(nom)){
            emprinteur.setNom(nom);
        }else {
            System.out.println("Entrer un correct input");
            reservelivre();
        }

        System.out.print("Entrer le cleClient: ");
        String cle =scanner.nextLine();
        if (isValidString(cle)){
            emprinteur.setCleEmprinteur(cle);
        }else {
            System.out.println("Entrer un correct input");
            reservelivre();
        }
        emprinteur.setId(clientController.rechercheParCle());
        if (emprinteur.getId() != 0){
            System.out.println("Le cle "+" '"+emprinteur.getCleEmprinteur()+"' "+ " client est déjà utiliser");
        }else {
            System.out.print("Entrer le code ISBN: ");
            String ISBN = scanner.nextLine();
            if (isValidString(ISBN)){
                livre.setISBN(ISBN);
            }else{
                System.out.println("Tu dois entrer une correct format");
            }


            livre.setId(clientController.getIdLivre());

            if (livre.getId() != 0){

                System.out.print("Entrer la date de retour: ");

                String dateRetString = scanner.nextLine();

                if (isValidDate(dateRetString)){
                }else {
                    System.out.println("Tu dois entrer une correct format");
                }
                emprinteur.setId(clientController.insertEmprinteur());
                if(emprinteur.getId() !=0){
                    LocalDate parseDateEmp = LocalDate.parse(getDateTime());
                    livreEmprinter.setDateEmprunt(parseDateEmp);
                    LocalDate parseDateRot = LocalDate.parse(dateRetString);
                    livreEmprinter.setDateRetour(parseDateRot);
                    int result = clientController.insertEmprintLivre();
                    if(result!=0){
                        System.out.println("--------------------====================--------------------");
                        System.out.println("\t\t\tLe livre est emprinter a ISBN: "+livre.getISBN());
                        System.out.println("--------------------====================--------------------");
                    }else{
                        System.out.println("--------------------====================--------------------");
                        System.out.println("\t\t\tUn probleme provient lors de l'emprint du livre");
                        System.out.println("--------------------====================--------------------");
                    }
                }

            }else {
                System.out.println("------------------------===========------------------------");
                System.out.println("\t\tIl n'a pas se livre avec ISBN: "+ livre.getISBN());
                System.out.println("------------------------===========------------------------");
            }
        }
    }
    public void returnerLivre(){
        try{

            System.out.println("------------------------------");
            System.out.println("\tRetour d'un livre emprinter");
            System.out.println("-------------------------------");
            System.out.print("Enter le ISBN du livre: ");
            livre.setISBN(scanner.nextLine());
            livre.setId(clientController.getIdLivre());
            if(livre.getId() != 0){
                System.out.print("Entrer le nom de l'emprinteur: ");
                emprinteur.setNom(scanner.nextLine());
                System.out.print("Entrer le cleClient: ");
                emprinteur.setCleEmprinteur(scanner.nextLine());
                emprinteur.setId(clientController.rechercheParCle());

                if (emprinteur.getId() != 0){
                    int result = clientController.returnLivreEmperinter();
//                    returnBookLoaded(livre, emprinteur);
                    if (result != 0){
                        System.out.println("--------------------====================--------------------");
                        System.out.println("\t\t\tlivre est redue avec success");
                        System.out.println("--------------------====================--------------------");
                    }else {
                        System.out.println("--------------------====================--------------------");
                        System.out.println("\t\t\tlivre retour du livre est echouer");
                        System.out.println("--------------------====================--------------------");

                    }
                }else {
                    System.out.println("--------------------====================--------------------");
                    System.out.println("\t\tIl n'a pas un client qu' il a le cle: "+"' "+emprinteur.getCleEmprinteur()+" '");
                    System.out.println("--------------------====================--------------------");
                }
            } else{
                System.out.println("--------------------====================--------------------");
                System.out.println("\t\tIl n'a pas ce livre ISBN: "+livre.getISBN());
                System.out.println("--------------------====================--------------------");
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
