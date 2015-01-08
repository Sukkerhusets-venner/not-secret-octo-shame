/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prosjekt.database;

import prosjekt.Domene.User;
import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.sql.DataSource;
import java.util.Random;
import prosjekt.Domene.*;

/**
 *
 * @author Solheim
 */
public class DatabaseConnection {

    private Connection connection;
    private DataSource dataSource;
    /**
     *
     */
    
    public DatabaseConnection() {

        connection = null;
        String databasePath = "jdbc:mysql://158.38.48.10:3306/team6";   // MAA FYLLES INN !Q!!!!!!
        String databaseUserName = "team6";
        String databasePassword = "Team62015";
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

    public boolean checkConnection(){
        return connection != null;
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
            pstmt.setString(2, password);

            resultSet = pstmt.executeQuery();

            if (resultSet.next()) {
                //Login sucessfull
                return true;
            }
        } catch (Exception e) {
            printErrorMessage(e, "Login");
        }
        return false;
    }

    public boolean registerUser(User user) {

        String sqlStatement = "INSERT INTO User(user_id, name, email, password) "
                + "VALUES (DEFAULT, ?, ?, ?)";
        try {
            PreparedStatement pstmt = connection.prepareStatement(sqlStatement);
            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getEmail());
            pstmt.setString(3, hashString(generatePassword()));

            pstmt.executeQuery();
            
            return true;
        } catch (Exception e) {
            rollBack();
            printErrorMessage(e, "Registrer bruker");
        }

        return false;
    }
    public User getUser(int user_id) {
        ResultSet resultSet = null;
        String sqlStatement = "SELECT*FROM User WHERE User_id=" + user_id;
        User use = null;
        
        try {
        PreparedStatement pstmt = connection.prepareStatement(sqlStatement); 
        
        return use;
        } catch(Exception e) {
            printErrorMessage(e, "getUser");
        }
        
        return null;
    }
    
    
    private void printErrorMessage(Exception e, String message) {
        System.err.println("*** Feil oppstått: " + message + ". ***");
        e.printStackTrace(System.err);
    }

    
    private void rollBack() {
        try {
            if (connection != null && !connection.getAutoCommit()) {
                connection.rollback();
            }
        } catch (SQLException e) {
            printErrorMessage(e, "rollback()");
        }
    }
    

    private String hashString(String string) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            string = "mitt passord er apekatt";

            md.update(string.getBytes("UTF-8")); // Change this to "UTF-16" if needed
            byte[] digest = md.digest();

            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < digest.length; i++) {
                sb.append(Integer.toHexString((digest[i] & 0xFF) | 0x100).substring(1, 3));
            }
            return sb.toString();
        } catch (Exception e) {
            System.out.println("Error");
        }

        return null;
    }

    
    private String generatePassword() {

        Random random = new Random();
        StringBuilder tmp = new StringBuilder();

        for (char ch = '0'; ch <= '9'; ++ch) {
            tmp.append(ch);
        }
        for (char ch = 'a'; ch <= 'z'; ++ch) {
            tmp.append(ch);
        }

        char[] symbols = tmp.toString().toCharArray();
        char[] buf = new char[10];

        for (int idx = 0; idx < buf.length; ++idx) {
            buf[idx] = symbols[random.nextInt(symbols.length)];
        }
        String passord = new String(buf);
        return passord;
    }
}
