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
import java.util.ArrayList;
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

        password = hashString(password);
        
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

    
    public String registerUser(User user) {

        String sqlStatement = "INSERT INTO User(user_id, name, email, password) "
                + "VALUES (DEFAULT, ?, ?, ?)";
        try {
            String generatedPassword = generatePassword();
            
            PreparedStatement pstmt = connection.prepareStatement(sqlStatement);
            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getEmail());
            pstmt.setString(3, hashString(generatedPassword));

            pstmt.executeUpdate();
            
            return generatedPassword;
        } catch (Exception e) {
            rollBack();
            printErrorMessage(e, "Registrer bruker");
        }

        return null;
    }
    
    
    public User getUser(String email) {
        ResultSet resultSet = null;
        String sqlStatement = "SELECT*FROM User WHERE email=?";
        User user = null;
        
        
        try {
        PreparedStatement pstmt = connection.prepareStatement(sqlStatement);
        pstmt.setString(1, email);
        resultSet = pstmt.executeQuery();
        
        
        while (resultSet.next()) {
            int id = (Integer) resultSet.getObject(1);
            String userName = resultSet.getString(2);
            String password = resultSet.getString(4);
            user = new User(id, userName, email, password);       
        }
       
        return user;
        
        } catch(Exception e) {
            printErrorMessage(e, "getUser");
        }
        
        return null;
    }
    
    
    public boolean editUser(User user){
        
        String sqlStatement = "UPDATE User SET "
                + " id = DEFAULT, username = ? ,email = ?, password = ? "
                + "WHERE User.id = ?";
        try {
            PreparedStatement pstmt = connection.prepareStatement(sqlStatement);
            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getEmail());
            pstmt.setString(3, user.getPassword());
            pstmt.setInt(4, user.getId());

            pstmt.executeUpdate();
            
            return true;
        } catch (Exception e) {
            rollBack();
            printErrorMessage(e, "Forandre bruker");
        }

        return false;
    }

    
    public boolean deleteUser(User user) {
        
        String sqlStatement = "DELETE FROM User WHERE User.user_id=?";
        
        try {
            PreparedStatement pstmt = connection.prepareStatement(sqlStatement);
            pstmt.setInt(1, user.getId());
            
            pstmt.executeUpdate();
            
            return true;
        } catch (Exception e) {
            printErrorMessage(e, "delete user failed");
        }
        return false;
    }
    
    public ArrayList<User> getUsers() {
        String sqlStatement = "SELECT * FROM User "; //join on score hvor godkjenningen vil ligg
        ArrayList<User> user = new ArrayList<>();
        ResultSet resultSet = null;
        try {
            PreparedStatement pstmt = connection.prepareStatement(sqlStatement);
           
            resultSet = pstmt.executeQuery();

            while (resultSet.next()) {
                int id = (Integer) resultSet.getObject(1);
                String userName = resultSet.getString(2);
                String email = resultSet.getString(3);
                String password = resultSet.getString(4);
                user.add(new User(id, userName, email, password));
                //user.add(getUser(resultSet.getString(1)));
            }
            return user;

        } catch (Exception e) {
            printErrorMessage(e, "feil i getUsers list");
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
    

    public String hashString(String string) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");

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
