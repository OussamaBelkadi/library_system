package Controller.MenuPrinciple;

import entite.Emprinteur;
import entite.Livre;
import entite.LivreEmprinter;
import service.ClientService;

import java.sql.SQLException;
import java.util.List;

public class ClientController implements LibraryController{
    private ClientService clientService;
    private LivreEmprinter livreEmprinter;
    private Emprinteur emprinteur;
    @Override
    public void run() {

    }
    public ClientController(ClientService clientService, LivreEmprinter livreEmprinter){
        this.clientService = clientService;
        this.livreEmprinter = livreEmprinter;
    }
    public int rechercheEmprinteur(){
            return clientService.searchEmprinteur();

    }
    public int insertEmprintLivre() throws SQLException {
        return clientService.insertEmprintLivre();
    }
    public int insertEmprinteur() throws SQLException{
        return clientService.insertEmprinteur();
    }
    public List<Livre> rechercheLivre() throws SQLException {
        return  clientService.rechercheReserver();
    }
    public List<Integer> LivreReserver() throws SQLException{
        return clientService.livreReserver();
    }
    public int getIdLivre() throws SQLException {
        return clientService.getIdLivre();
    }
    public int rechercheParCle(){return clientService.searchEmprinteurCle();
    }
    public int returnLivreEmperinter() throws SQLException {return clientService.returnBookLoaded();}

    public List<Livre> listLivreDisponible() throws SQLException {return clientService.listBookAvailible();}

    public String disponibleLivre() throws SQLException {return  clientService.livreDisponible();}
}
