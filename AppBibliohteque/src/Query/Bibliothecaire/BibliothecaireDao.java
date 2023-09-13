package Query.Bibliothecaire;

import entite.Bibliothecaire;
import utilite.DataBase;

import java.security.MessageDigest;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Base64;

public class BibliothecaireDao {

    private String hashPass;
    DataBase dataBase = DataBase.getInstance();
    Connection connection = dataBase.getConnection();
    public void insertBibliothecaire( Bibliothecaire  bibliothecaire){

        try(
            PreparedStatement insertBibliothecaire = connection.prepareStatement(QueryBibiliothecaire.insertBibliothecaire())) {
            System.out.println(bibliothecaire.getNom());
            byte[] salt = generateSalt();
            this.hashPass =hashPassword(bibliothecaire.getMotPass(), salt);
            insertBibliothecaire.setString(1, bibliothecaire.getNom());
            insertBibliothecaire.setString(2, this.hashPass);
            int rows = insertBibliothecaire.executeUpdate();
            if(rows > 0){
                System.out.println("Bibliothecaire creer");
            }else{
                System.out.println("Problemme apares l'aure de l'enregistrement");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static byte[] generateSalt() {
        byte[] salt = new byte[16];
        SecureRandom random = new SecureRandom();
        random.nextBytes(salt);
        return salt;
    }

    public static String hashPassword(String password, byte[] salt) {
        try {
            // Create a MessageDigest instance with the SHA-256 algorithm
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");

            // Add the salt to the password bytes and hash them
            messageDigest.update(salt);
            byte[] passwordBytes = password.getBytes();
            byte[] hashedBytes = messageDigest.digest(passwordBytes);

            // Convert the hashed bytes to a Base64-encoded string
            String hashedPassword = Base64.getEncoder().encodeToString(hashedBytes);

            return hashedPassword;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
