
package prosjekt.database;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import prosjekt.Domene.User;

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
     * Test of checkLogin method, of class DatabaseConnection.
     */
    @Test
    public void testCheckLogin() {
        System.out.println("checkLogin");
        String email = "admin@admin.no";
        String password = "admin";
        DatabaseConnection instance = new DatabaseConnection();
        boolean expResult = true;
        boolean result = instance.checkLogin(email, password);
        assertEquals(expResult, result);
    }

    /**
     * Test of registerUser method, of class DatabaseConnection.
     */
    @Test
    public void testRegisterUser() {
        System.out.println("registerUser");
        User user = new User("test", "test@test.no", "test");
        DatabaseConnection instance = new DatabaseConnection();
        boolean expResult = true;
        boolean result = instance.registerUser(user);
        assertEquals(expResult, result);
    }

    /**
     * Test of getUser method, of class DatabaseConnection.
     */
    @Test
    public void testGetUser() {
        System.out.println("getUser");
        int user_id = 0;
        DatabaseConnection instance = new DatabaseConnection();
        User expResult = null;
        //User result = instance.getUser(user_id);
        //assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of checkConnection method, of class DatabaseConnection.
     */
    @Test
    public void testCheckConnection() {
        System.out.println("checkConnection");
        DatabaseConnection instance = new DatabaseConnection();
        boolean expResult = true;
        boolean result = instance.checkConnection();
        assertEquals(expResult, result);

    }

    /**
     * Test of closeConnection method, of class DatabaseConnection.
     */
    @Test
    public void testCloseConnection() {
        System.out.println("closeConnection");
        DatabaseConnection instance = new DatabaseConnection();
        instance.closeConnection();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of editUser method, of class DatabaseConnection.
     */
    @Test
    public void testEditUser() {
        System.out.println("editUser");
        User user = null;
        DatabaseConnection instance = new DatabaseConnection();
        boolean expResult = false;
        boolean result = instance.editUser(user);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}
