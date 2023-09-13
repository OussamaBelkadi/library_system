package Controller.MenuPrinciple;

import entite.Livre;
import service.AdminService;
import view.AdminView;

import java.sql.SQLException;
import java.util.List;

public class AdminController implements LibraryController{
    private final Livre livre;
    private final AdminService adminService;
    public AdminController(Livre livre, AdminService adminService){
        this.livre = livre;
        this.adminService = adminService;
    }
    @Override
    public void run() {

    }
    public int insertLivre(Livre livre){
        return adminService.insertLivre(livre);
    }

    public boolean getLivre(Livre livre) throws SQLException {
        boolean result = false;
        if(!adminService.getLivre(livre).isEmpty()) {
            List<Object> resultSet = adminService.getLivre(livre);

            livre.setId((int) resultSet.get(0));
            livre.setTitre((String) resultSet.get(1));
            livre.setAuteur((String) resultSet.get(2));
            livre.setQuantite((int) resultSet.get(3));
            livre.setISBN((String) resultSet.get(4));
            livre.setStatus((String) resultSet.get(5));
            result=true;
        }
        return result;
    }

    public int modifierLivre(Livre livre) throws SQLException {
        return adminService.modifierLivre(livre);
    }

    public List<Livre> rechercheTitre(Livre livre) throws SQLException {
        return adminService.rechercheTitre(livre);
    }
    public List<Livre> rechercheAuteur(Livre livre) throws SQLException {
        return adminService.rechercheAuteur(livre);
    }

    public  int suprimerLivre(Livre livre) throws SQLException {
        return adminService.suprimerLivre(livre);
    }
    public List<Livre> statisticLivre() throws SQLException {
        return adminService.statisticBook();
    }
    public List<Livre> ListLivreNoDisponible() throws SQLException {
        return adminService.listBookNotAvailible();
    }
    public List<Livre> listLivreDisponible() throws SQLException {return adminService.listBookAvailible();}

}
