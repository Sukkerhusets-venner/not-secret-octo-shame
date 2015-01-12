
package prosjekt.database;

import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import prosjekt.Domene.User;
import prosjekt.Domene.UserScore;

/**
 *
 * @author Solheim
 */
public class DatabaseConnectionTest {
    
    public DatabaseConnectionTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }


    /**
     * Test of getUsers method, of class DatabaseConnection.
     */
    @Test
    public void testGetUsers() {
        System.out.println("getUsers");
        DatabaseConnection instance = new DatabaseConnection();
        ArrayList<User> expResult = null;
        ArrayList<User> result = instance.getUsers();
        assertEquals(expResult, result);
        
    }

    /**
     * Test of getHighScoreList method, of class DatabaseConnection.
     */
    @Test
    public void testGetHighScoreList() {
        System.out.println("getHighScoreList");
        DatabaseConnection instance = new DatabaseConnection();
        ArrayList<UserScore> expResult = null;
        ArrayList<UserScore> result = instance.getHighScoreList();
        assertEquals(expResult, result);
        
    }

  
    /**
     * Test of registerUser method, of class DatabaseConnection.
     */
//    @Test
//    public void testRegisterUser() {
//        System.out.println("registerUser");
//        User user = new User("test", "test@test.no", "test");
//        DatabaseConnection instance = new DatabaseConnection();
//        boolean expResult = true;
//        boolean result = instance.registerUser(user);
//        assertEquals(expResult, result);
//    }
//    
//        /**
//     * Test of checkLogin method, of class DatabaseConnection.
//     */
//    @Test
//    public void testCheckLogin() {
//        System.out.println("checkLogin");
//        String email = "test@test.no";
//        DatabaseConnection instance = new DatabaseConnection();
//        String password = instance.hashString("test");
//        boolean expResult = true;
//        boolean result = instance.checkLogin(email, password);
//        assertEquals(expResult, result);
//    }
//
//
//    /**
//     * Test of getUser method, of class DatabaseConnection.
//     */
//    @Test
//    public void testGetUser() {
//        System.out.println("getUser");
//        int user_id = 1;
//        DatabaseConnection instance = new DatabaseConnection();
//        User expResult = new User(user_id, "test", "test@test.no", instance.hashString("test"));
//        User result = instance.getUser("test@test.no");
//        assertEquals(expResult, result);
//    }
//
//    /**
//     * Test of checkConnection method, of class DatabaseConnection.
//     */
//    @Test
//    public void testCheckConnection() {
//        System.out.println("checkConnection");
//        DatabaseConnection instance = new DatabaseConnection();
//        boolean expResult = true;
//        boolean result = instance.checkConnection();
//        assertEquals(expResult, result);
//
//    }
//    /**
//     * Test of editUser method, of class DatabaseConnection.
//     */
////    @Test
////    public void testEditUser() {
////        System.out.println("editUser");
////        int user_id = 1;
////        DatabaseConnection instance = new DatabaseConnection();
////        User user = instance.getUser();
////        boolean expResult = true;
////        boolean result = instance.editUser(user);
////        assertEquals(expResult, result);
////    }
//
//    /**
//     * Test of deleteUser method, of class DatabaseConnection.
//     */
////    @Test
////    public void testDeleteUser() {
////        System.out.println("deleteUser");
////        DatabaseConnection instance = new DatabaseConnection();
////        User user = instance.getUser("test@test.no");
////        boolean expResult = true;
////        boolean result = instance.deleteUser(user);
////        assertEquals(expResult, result);
////
////    }
}
