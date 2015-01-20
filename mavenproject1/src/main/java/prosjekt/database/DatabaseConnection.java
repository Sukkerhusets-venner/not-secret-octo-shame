package prosjekt.database;

import prosjekt.Domene.User;
import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
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
        refreshConnecton();
    }
    
    public void refreshConnecton(){
        connection = null;
        String databasePath = "jdbc:mysql://158.38.48.10:3306/team6";
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

    public boolean checkConnection() {
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
        String query = "SELECT email, password FROM User WHERE email = ? and password = ? ";

        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, email);
            pstmt.setString(2, password);

            ResultSet resultSet = pstmt.executeQuery();

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

        if (!checkUserName(user.getUsername()) || !checkEmail(user.getEmail())) {
            return null;
        }

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
        String sqlStatement = "SELECT * FROM User WHERE email=?";
        User user = null;

        try {
            PreparedStatement pstmt = connection.prepareStatement(sqlStatement);
            pstmt.setString(1, email);
            ResultSet resultSet = pstmt.executeQuery();

            while (resultSet.next()) {
                int id = (Integer) resultSet.getObject(1);
                String userName = resultSet.getString(2);
                String password = resultSet.getString(4);
                user = new User(id, userName, email, password);
            }

            return user;

        } catch (Exception e) {
            printErrorMessage(e, "getUser");
        }

        return null;
    }

    public boolean editUser(User user) {

        String sqlStatement = "UPDATE User SET "
                + " name = ? ,email = ?, password = ? "
                + "WHERE User.user_id = ?";
        try {
            PreparedStatement pstmt = connection.prepareStatement(sqlStatement);
            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getEmail());
            pstmt.setString(3, hashString(user.getPassword()));
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

        try {
            PreparedStatement pstmt = connection.prepareStatement(sqlStatement);

            ResultSet resultSet = pstmt.executeQuery();

            while (resultSet.next()) {
                int id = (Integer) resultSet.getObject(1);
                String userName = resultSet.getString(2);
                String email = resultSet.getString(3);
                String password = resultSet.getString(4);
                user.add(new User(id, userName, email, password));
            }
            return user;

        } catch (Exception e) {
            printErrorMessage(e, "feil i getUsers list");
        }
        return null;
    }

    public ArrayList<UserScore> getHighScoreList() {

        final int NUMBER_OF_HIGHSCORES_SHOWN = 10;

        String sql = "SELECT  User.name, MAX(Score.score) FROM User "
                + "JOIN Game ON ( User.user_id = Game.user_id)"
                + "JOIN Score ON ( Game.score_id = Score.score_id)"
                + "GROUP BY User.user_id";
        ArrayList<UserScore> hsList = new ArrayList();

        try {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            ResultSet resultSet = pstmt.executeQuery();

            int i = 0;
            while (resultSet.next() && i < NUMBER_OF_HIGHSCORES_SHOWN) {
                String userName = resultSet.getString(1);
                int hiScore = resultSet.getInt(2);

                hsList.add(new UserScore(userName, hiScore));
                i++;
            }
            Collections.sort(hsList);
            Collections.reverse(hsList);

            return hsList;
        } catch (Exception e) {
            printErrorMessage(e, "HighScoreList");
        }

        return null;
    }

    public ArrayList<ScoreProfile> getScoreProfile(User user) {

        ArrayList<ScoreProfile> list = new ArrayList();
        ResultSet resultSet = null;
        String sqlStatement = "SELECT Problemset.set_id, Problemset.max_points,"
                + " Score.score, Score.date FROM User"
                + " JOIN Game ON User.user_id = ?"
                + " JOIN Problemset ON Problemset.set_id = Game.set_id"
                + " JOIN Score ON Score.score_id = Game.score_id";

        try {
            PreparedStatement pstmt = connection.prepareStatement(sqlStatement);
            pstmt.setInt(1, user.getId());
            resultSet = pstmt.executeQuery();
            ScoreProfile sp = null;

            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                int maxPoints = resultSet.getInt(2);
                int points = resultSet.getInt(3);
                String date = resultSet.getString(4);
                list.add(new ScoreProfile(id, maxPoints, points, date));
            }

            return list;

        } catch (Exception e) {
            printErrorMessage(e, "getScoreProfile");
        }

        return null;
    }

    public ArrayList<UserScoreOverview> getUserScoreOverview() {

        ArrayList<UserScoreOverview> uso = new ArrayList();
        ArrayList<User> users = getUsers();

        for (User user : users) {
            ArrayList<ScoreProfile> sp = getScoreProfile(user);

            uso.add(new UserScoreOverview(user, sp));
        }

        return uso;
    }

    public ArrayList<Task> getTasks(int set_id) {
        ArrayList<Task> list = new ArrayList();
        ResultSet resultSet = null;
        String sqlStatement = "SELECT Task.task_id, Task.des, Task.task_text, Task.diff, Task.start_html, Task.fasit_html, "
                + "Task.start_css, Task.fasit_css, Task.points FROM Task "
                + "JOIN TaskSet ON(Task.task_id = TaskSet.task_id) JOIN Problemset"
                + " ON(TaskSet.set_id = Problemset.set_id) WHERE Problemset.set_id"
                + "= ?";

        try {
            PreparedStatement pstmt = connection.prepareStatement(sqlStatement);
            pstmt.setInt(1, set_id);
            resultSet = pstmt.executeQuery();

            while (resultSet.next()) {
                int task_id = resultSet.getInt(1);
                String des = resultSet.getString(2);
                String text = resultSet.getString(3);
                int diff = resultSet.getInt(4);
                String html = resultSet.getString(5);
                String answerHtml = resultSet.getString(6);
                String css = resultSet.getString(7);
                String answerCss = resultSet.getString(8);
                int points = resultSet.getInt(9);
                list.add(new Task(task_id, des, text, diff, html, answerHtml, css, answerCss, points));
            }
            return list;
        } catch (Exception e) {
            printErrorMessage(e, " feil i getTasks");
        }

        return null;

    }

    public boolean registerScore(User user, int score, int setId) {

        java.sql.Date date = new java.sql.Date(new java.util.Date().getTime());
        String sql1 = "INSERT INTO Score VALUES (DEFAULT, ?, ?)";
        String sql2 = "INSERT INTO Game VALUES (?, ?, LAST_INSERT_ID())";

        ResultSet resultSet = null;

        try {
            connection.setAutoCommit(false);
            PreparedStatement pstmt = connection.prepareStatement(sql1);
            PreparedStatement pstmt2 = connection.prepareStatement(sql2);
            pstmt.setInt(1, score);
            pstmt.setDate(2, date);

            pstmt2.setInt(1, user.getId());
            pstmt2.setInt(2, setId);

            pstmt.executeUpdate();
            pstmt2.executeUpdate();

            connection.commit();

            return true;

        } catch (Exception e) {
            rollBack();
            printErrorMessage(e, "Registrer poeng");
        }

        return false;
    }

    public String changePassword(User user) {
        String sql = "UPDATE User SET User.password = ? WHERE User.user_id = ?";
        String newPassword = hashString(user.getPassword());

        try {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, newPassword);
            pstmt.setInt(2, user.getId());
            pstmt.executeUpdate();

            return newPassword;

        } catch (Exception e) {
            printErrorMessage(e, "Passordforandring");
        }

        return null;
    }

    public String getNewPassword(User user) {
        
        String sql = "UPDATE User SET User.password = ? WHERE User.user_id = ?";
        String newPassword = hashString(generatePassword());

        try {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, newPassword);
            pstmt.setInt(2, user.getId());
            pstmt.executeUpdate();

            return newPassword;

        } catch (Exception e) {
            printErrorMessage(e, "nytt passord");
        }

        return null;
    }

    public ArrayList<Message> getChat(User currentUser, User otherUser) {

        String sql = "SELECT Message.time, Message.text "
                + "FROM Message "
                + "JOIN Chat ON Message.chat_id = Chat.chat_id"
                + "JOIN User ON (User.user_id = Chat.user_id1) "
                + "OR (User.user_id = Chat.user_id2)"
                + "WHERE (Chat.user_id1 = ? AND Chat.user_id2 != ?)"
                + "OR (Chat.user_id1 = ? AND chat.user_id2 != ?)"
                + "GROUP BY Message.message_id";

        ArrayList<Message> chat = new ArrayList<>();

        try {
            PreparedStatement pstmt = connection.prepareStatement(sql);

            pstmt.setInt(1, currentUser.getId());
            pstmt.setInt(2, otherUser.getId());

            pstmt.setInt(3, otherUser.getId());
            pstmt.setInt(4, currentUser.getId());

            ResultSet resultSet = pstmt.executeQuery();

            while (resultSet.next()) {
                java.sql.Timestamp date = resultSet.getTimestamp(1);
                String text = resultSet.getString(2);

                chat.add(new Message(date, text));
            }
            return chat;

        } catch (Exception e) {
            printErrorMessage(e, "getChat");
        }
        return null;
    }

    public ArrayList<Chat> getChatList(User currentUser) {

        String sql = "SELECT User.user_id, User.name, User.email, User.password, Chat.read"
                +"FROM User"
                +"JOIN Chat ON (User.user_id = Chat.user_id1 OR User.user_id = Chat.user_id2)" 
                +"WHERE (Chat.user_id1 = ? AND Chat.user_id2 != ?) OR" 
                +"(Chat.user_id1 != ? AND Chat.user_id2 = ?)"
                +"GROUP BY User.user_id";

        ArrayList<Chat> chatList = new ArrayList<>();

        try {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, currentUser.getId());
            pstmt.setInt(2, currentUser.getId());
            pstmt.setInt(3, currentUser.getId());
            pstmt.setInt(4, currentUser.getId());
            
            ResultSet resultSet = pstmt.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                String name = resultSet.getString(2);
                String email = resultSet.getString(3);
                String password = resultSet.getString(4);
                boolean read = resultSet.getBoolean(5);
                User otherUser = new User(id, name, email, password);
                
                if (currentUser.getId() != otherUser.getId()){
                    chatList.add(new Chat(currentUser, otherUser, read));
                }
            }
            return chatList;

        } catch (Exception e) {
            printErrorMessage(e, "getChatList");
        }
        return null;
    }

    public boolean gotMessage(User currentUser) {
        String sql = "SELECT User.user_id FROM User "
                + "JOIN Chat ON "
                + "(Chat.user_id1 = ? OR chat.user_id2 = ?) "
                + "WHERE chat.read = false;";

        return false;
    }

    public boolean registerChat(Chat chat) {
        String sqlStatement = "INSERT INTO Chat VALUES (DEFAULT, ?, ?, ?)";

        try {

            PreparedStatement pstmt = connection.prepareStatement(sqlStatement);
            pstmt.setInt(1, chat.getUserCurrent().getId());
            pstmt.setInt(2, chat.getUserOther().getId());
            pstmt.setBoolean(3, false);

            pstmt.executeUpdate();

            return true;
        } catch (Exception e) {
            printErrorMessage(e, "registrer chat");
        }
        return false;
    }

    public boolean isChatRegistered(User userCurrent, User userOther) {
        String sqlStatement = "Select Chat.chat_id From Chat "
                + "Where Chat.user_id1 = ? and Chat.user_id2 = ?"
                + "Or Chat.user_id2 = ? and Chat.user_id1 = ?";

        try {
            PreparedStatement pstmt = connection.prepareStatement(sqlStatement);
            pstmt.setInt(1, userCurrent.getId());
            pstmt.setInt(2, userOther.getId());

            ResultSet resultSet = pstmt.executeQuery();

            if (resultSet.next()) {
                //Allerede i databasen (Opptatt)
                return true;
            }

        } catch (Exception e) {
            printErrorMessage(e, "feil i CheckChat");
        }
        return false;

    }

    public boolean registerMessage(Message message, int chatId) {
        String sql = "INSERT INTO Message "
                + "VALUES (DEFAULT, ?, ?, ?)";

        String sql2 = "UPDATE Chat SET Chat.read = false "
                + " WHERE Chat.chat_id = ? ";

        try {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setTimestamp(1, message.getTimestamp());
            pstmt.setString(2, message.getText());
            pstmt.setInt(3, chatId);

            pstmt.executeUpdate();

            PreparedStatement pstmt2 = connection.prepareStatement(sql2);
            pstmt2.setInt(1, chatId);

            pstmt.executeUpdate();

            return true;

        } catch (Exception e) {
            printErrorMessage(e, "registrer melding");
        }
        return false;
    }

    private boolean checkUserName(String userName) {

        ResultSet resultSet = null;
        String query
                = "SELECT name FROM User WHERE name = ? ";

        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, userName);

            resultSet = pstmt.executeQuery();

            if (resultSet.next()) {
                //Allerede i databasen (Opptatt)
                return false;
            }
        } catch (Exception e) {
            printErrorMessage(e, "CheckUserName");
        }
        return true;
    }

    private boolean checkEmail(String email) {

        ResultSet resultSet = null;
        String query
                = "SELECT email FROM User WHERE email = ? ";

        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, email);

            resultSet = pstmt.executeQuery();

            if (resultSet.next()) {
                //Allerede i databasen (Opptatt)
                return false;
            }
        } catch (Exception e) {
            printErrorMessage(e, "CheckUserName");
        }
        return true;
    }

    private void printErrorMessage(Exception e, String message) {
        System.err.println("*** Feil oppstÃ¥tt: " + message + ". ***");
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
            System.out.println("HASH Error");
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
