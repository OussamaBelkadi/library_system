package entite;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.*;
import java.util.Base64;

public class Bibliothecaire {
    private String nom;
    private String motPass;
    private String hashPass;

    public String getNom() {
        return nom;
    }
    public String getMotPass(){
        return motPass;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setMotPass(String motPass) {
        this.motPass = motPass;
    }

    public String authentifier() {

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/bibliotheque", "root", "")) {
            String query = "Select motPass from bibliothecaire where nom=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, this.getNom());
            ResultSet resultSet = preparedStatement.executeQuery();
            this.hashPass = resultSet.toString();
            if (this.hashPass.equals(hashPasswordUser(this.getMotPass()))){
                return "sss";
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return hashPasswordUser(this.getMotPass());
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
    public  boolean enregistrer() {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/bibliotheque", "root", "")) {
//            Check if the user is already exists in Database
            String query= "Select COUNT(*) AS count FROM bibliothecaire where nom = ?";
            PreparedStatement preparedStatement1 = connection.prepareStatement(query);
            preparedStatement1.setString(1, this.getNom());
            ResultSet result = preparedStatement1.executeQuery();
            if(result.next() && result.getInt("count")==0){
                byte[] salt = generateSalt();

                this.hashPass = hashPassword(this.getMotPass(), salt);
                String queryInsert = "Insert into bibliothecaire (nom, motPass) VALUES (?, ?)";
                PreparedStatement insertStatement = connection.prepareStatement(queryInsert);
                insertStatement.setString(1, this.getNom());
                insertStatement.setString(2, this.hashPass);
                int rowsAffected = insertStatement.executeUpdate();
                return rowsAffected > 0;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }


    public static String hashPasswordUser(String input) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = messageDigest.digest(input.getBytes());
            return Base64.getEncoder().encodeToString(hashedBytes);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
}