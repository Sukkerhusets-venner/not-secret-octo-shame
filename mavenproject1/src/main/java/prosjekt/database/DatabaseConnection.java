/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prosjekt.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import prosjekt.Beans.*;

/**
 *
 * @author Solheim
 */
public class DatabaseConnection {

    private Connection connection;

    /**
     *
     */
    public DatabaseConnection() {

        String databasePath = "158.38.48.10:3306";   // MAA FYLLES INN !Q!!!!!!
        String databaseUserName = "team6";
        String databasePassword = "team62015";
        String dbDriver = "com.mysql.jdbc.Driver";

        try {
            Class.forName(dbDriver).newInstance();
            connection = DriverManager.getConnection(
                    databasePath,
                    databaseUserName,
                    databasePassword);

        } catch (Exception e) {
            printErrorMessage(e, "Oppretting av forbindelse");
        }
    }

    public void closeConnection() {

        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                printErrorMessage(e, "Lukking av forbindelse");
            }
        }
    }

    public boolean checkLogin(String email, String password) {

        ResultSet resultSet = null;
        String query
                = "SELECT email, password FROM User WHERE email = ? and password = ? ";

        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, email);
            pstmt.setString(2 , password);
            
            resultSet = pstmt.executeQuery( );

            if (resultSet.next()) {
                //Login sucessfull
                return true;
            }
        } catch (Exception e) {
            printErrorMessage(e, "Login");
        }
        return false;
    }
    
    public boolean registerUser(User user){
    
        return false;
    }

    private void printErrorMessage(Exception e, String message) {
        System.err.println("*** Feil oppstått: " + message + ". ***");
        e.printStackTrace(System.err);
    }
}
