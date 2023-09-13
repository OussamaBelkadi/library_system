package utilite;

import java.sql.Connection;
import java.sql.DriverManager;

public class DataBase {

    private static DataBase instance;
    private Connection connection;
    private static final String url = "jdbc:mysql://localhost:3306/bibliotheque";
    private static final String userName = "root";
    private static final String passWord = "";
    //Ansure the instance of class is doing outside of class
    private DataBase(){

    }

    public static synchronized DataBase getInstance(){
        if (instance == null){
            instance = new DataBase();
        }
        return instance;
    }

    public Connection getConnection(){
        try{
            if (connection == null){
                connection = DriverManager.getConnection(url, userName, passWord);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return connection;
    }

    public void closeConnection(){
        if (connection != null){
            try{
                connection.close();
                connection = null;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

}
