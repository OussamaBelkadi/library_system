package rac;

import entite.Livre;

import java.util.List;

public class ResultRac {
    public static  void resultExecution(List<Livre> listOfBooks) {
        System.out.println("=======================================================");
        System.out.println("Titre"+"\tAuteur"+"\tQuantite"+"\tISBN"+"\tStatus");
        System.out.println("=======================================================");
        for(Livre book : listOfBooks) {
            System.out.println(book.getTitre()+"\t\t"+book.getAuteur()+"\t\t"+book.getQuantite()+"\t\t"+book.getISBN()+"\t\t"+book.getStatus());
            System.out.println("--------------------------------------------------------------");
        }
    }
}
