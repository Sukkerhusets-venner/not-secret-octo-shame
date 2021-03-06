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
        connection = null;
        String databasePath = "jdbc:mysql://158.38.48.15:3306/team6";
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

    // CRUD METHODS FOR THE WEB-APPLICATION ** **
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

    public boolean isAdmin(User user) {
        String sql = "SELECT User.user_id FROM User"
                + " JOIN Admin ON Admin.user_id = User.user_id "
                + " WHERE User.user_id = ?";

        try {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            ResultSet resultSet = pstmt.executeQuery();

            while (resultSet.next()) {
                return true;
            }
        } catch (Exception e) {

        }
        return false;
    }

    public ArrayList<User> getAdminList() {
        String sql = "SELECT * FROM User JOIN Admin "
                + "On User.user_id = Admin.user_id";
        ArrayList<User> user = new ArrayList<>();

        try {
            PreparedStatement pstmt = connection.prepareStatement(sql);

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
            printErrorMessage(e, "getAdmin");
        }
        return null;
    }

    public ArrayList<User> getUsers() {
        String sqlStatement = "SELECT * FROM User ";
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
            printErrorMessage(e, "getUsers");
        }
        return null;
    }

    public ArrayList<UserScore> getHighScoreList() {

        final int HIGHSCORES_SHOWN = 10;

        String sql = "SELECT  User.name, MAX(Score.score) FROM User "
                + "JOIN Game ON ( User.user_id = Game.user_id)"
                + "JOIN Score ON ( Game.score_id = Score.score_id)"
                + "GROUP BY User.user_id";
        ArrayList<UserScore> hsList = new ArrayList();

        try {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            ResultSet resultSet = pstmt.executeQuery();

            int i = 0;
            while (resultSet.next()) {
                String userName = resultSet.getString(1);
                int hiScore = resultSet.getInt(2);

                hsList.add(new UserScore(userName, hiScore));
                i++;
            }
            Collections.sort(hsList);
            Collections.reverse(hsList);
            ArrayList<UserScore> tmp = new ArrayList();
            if(hsList.size() > HIGHSCORES_SHOWN){
                for(int k = 0; k < HIGHSCORES_SHOWN; k++){
                    tmp.add(hsList.get(k));
                }
                hsList = tmp;
            }
            

            return hsList;
        } catch (Exception e) {
            printErrorMessage(e, "HighScoreList");
        }

        return null;
    }

    public ArrayList<ScoreProfile> getScoreProfile(User user, int maxAnt) {

        ArrayList<ScoreProfile> list = new ArrayList();
        ResultSet resultSet = null;
        String sqlStatement = "SELECT Problemset.set_id, Problemset.max_points, Score.score, Score.date FROM Score, Problemset, Game WHERE Score.score_id = Game.score_id AND Problemset.set_id = Game.set_id AND Game.user_id =? ORDER BY Score.score DESC";

        try {
            PreparedStatement pstmt = connection.prepareStatement(sqlStatement);
            pstmt.setInt(1, user.getId());
            resultSet = pstmt.executeQuery();
            if (!resultSet.isBeforeFirst()) {
                return new ArrayList<ScoreProfile>();
            } else {
                int k = 0;
                ScoreProfile sp = null;
                while (resultSet.next()) {
                    if (k >= maxAnt && maxAnt != -1) {
                        break;
                    } else {
                        int id = resultSet.getInt(1);
                        int maxPoints = resultSet.getInt(2);
                        int points = resultSet.getInt(3);
                        String date = resultSet.getString(4);
                        list.add(new ScoreProfile(id, maxPoints, points, date));
                        k++;
                    }
                }
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
            ArrayList<ScoreProfile> sp = getScoreProfile(user, -1);

            uso.add(new UserScoreOverview(user, sp));
        }

        return uso;
    }

    public ArrayList<Task> getTasks() {
        //Returns a random problemsSet

        String sql = "SELECT Problemset.set_id FROM Problemset";
        ArrayList<Integer> list = new ArrayList();
        Random random = new Random();

        try {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            ResultSet resultSet = pstmt.executeQuery();

            while (resultSet.next()) {
                list.add(resultSet.getInt(1));
            }

            return getTasks(list.get(random.nextInt(list.size())));
        } catch (Exception e) {
            printErrorMessage(e, "getTask");
        }
        return null;
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

    public boolean changePassword(User user) {
        String sql = "UPDATE User SET User.password = ? WHERE User.user_id = ?";
        String newPassword = user.getPassword();

        try {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, hashString(newPassword));
            pstmt.setInt(2, user.getId());
            pstmt.executeUpdate();

            return true;

        } catch (Exception e) {
            printErrorMessage(e, "Passordforandring");
        }

        return false;
    }

    public String getNewPassword(User user) {

        String sql = "UPDATE User SET User.password = ? WHERE User.user_id = ?";
        String newPassword = generatePassword();

        try {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, hashString(newPassword));
            pstmt.setInt(2, user.getId());
            pstmt.executeUpdate();

            return newPassword;

        } catch (Exception e) {
            printErrorMessage(e, "nytt passord");
        }

        return null;
    }
    // ** ** ** ** ** *** ** ** ** **

    // CHAT API ** ** **
    public ArrayList<Message> getChat(User currentUser, User otherUser) {

        String sql = "SELECT Message.time, Message.text "
                + " FROM Message "
                + " JOIN Chat ON Message.chat_id = Chat.chat_id "
                + " JOIN User ON (User.user_id = Chat.user_id1) "
                + " OR (User.user_id = Chat.user_id2)"
                + " WHERE (Chat.user_id1 = ? AND Chat.user_id2 = ?)"
                + " OR (Chat.user_id1 = ? AND Chat.user_id2 = ?)"
                + " GROUP BY Message.message_id";

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

        /*String sql = "SELECT User.user_id, User.name, User.email, User.password, Chat.read1, Chat.read2"
         + " FROM User"
         + " JOIN Chat ON (User.user_id = Chat.user_id1 OR User.user_id = Chat.user_id2)"
         + " WHERE (Chat.user_id1 = ? AND Chat.user_id2 != ?) OR"
         + " (Chat.user_id1 != ? AND Chat.user_id2 = ?)"
         + " GROUP BY User.user_id";*/
        String sql1 = "SELECT User.user_id, User.name, User.email, User.password, Chat.read1, Chat.read2"
                + " FROM User"
                + " JOIN Chat ON (User.user_id = Chat.user_id1)"
                + " WHERE (Chat.user_id2 = ?)";
        String sql2 = "SELECT User.user_id, User.name, User.email, User.password, Chat.read1, Chat.read2"
                + " FROM User"
                + " JOIN Chat ON (User.user_id = Chat.user_id2)"
                + " WHERE (Chat.user_id1 = ?)";

        ArrayList<Chat> chatList = new ArrayList<>();

        try {
            PreparedStatement pstmt = connection.prepareStatement(sql1);
            pstmt.setInt(1, currentUser.getId());

            ResultSet resultSet = pstmt.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                String name = resultSet.getString(2);
                String email = resultSet.getString(3);
                String password = resultSet.getString(4);
                boolean read1 = resultSet.getBoolean(5);
                boolean read2 = resultSet.getBoolean(6);
                User otherUser = new User(id, name, email, password);

                if (currentUser.getId() != otherUser.getId()) {
                    chatList.add(new Chat(currentUser, otherUser, read2, read1));
                }
            }
            PreparedStatement pstmt2 = connection.prepareStatement(sql2);
            pstmt2.setInt(1, currentUser.getId());

            ResultSet resultSet2 = pstmt2.executeQuery();

            while (resultSet2.next()) {
                int id = resultSet2.getInt(1);
                String name = resultSet2.getString(2);
                String email = resultSet2.getString(3);
                String password = resultSet2.getString(4);
                boolean read1 = resultSet2.getBoolean(5);
                boolean read2 = resultSet2.getBoolean(6);
                User otherUser = new User(id, name, email, password);

                if (currentUser.getId() != otherUser.getId()) {
                    chatList.add(new Chat(currentUser, otherUser, read1, read2));
                }
            }
            return chatList;

        } catch (Exception e) {
            printErrorMessage(e, "getChatList");
        }
        return null;
    }

    public int getChatId(User currentUser, User otherUser) {

        String sql = "SELECT Chat.chat_id FROM Chat "
                + " JOIN User ON (User.user_id = Chat.user_id1) "
                + " OR (User.user_id = Chat.user_id2) "
                + " WHERE (Chat.user_id1 = ? AND Chat.user_id2 = ?)"
                + " OR (Chat.user_id1 = ? AND Chat.user_id2 = ?)"
                + " GROUP BY Chat.chat_id";

        try {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, currentUser.getId());
            pstmt.setInt(2, otherUser.getId());
            pstmt.setInt(3, otherUser.getId());
            pstmt.setInt(4, currentUser.getId());

            ResultSet resultSet = pstmt.executeQuery();
            int chatId = 0;

            while (resultSet.next()) {
                chatId = resultSet.getInt(1);
            }

            return chatId;
        } catch (Exception e) {
            printErrorMessage(e, "getChatId");
        }

        return -1;
    }

    public int gotMessage(User currentUser) {
        int numberOfMessages = 0;

        String sql1 = "SELECT COUNT(Chat.chat_id) FROM Chat "
                + "WHERE ((Chat.user_id1 = ?) AND (Chat.read1 = false)) ";

        String sql2 = "SELECT COUNT(Chat.chat_id) FROM Chat "
                + "WHERE ((Chat.user_id2 = ?) AND (Chat.read2 = false))";

        try {
            PreparedStatement pstmt = connection.prepareStatement(sql1);
            pstmt.setInt(1, currentUser.getId());

            ResultSet resultSet = pstmt.executeQuery();

            if (resultSet.next()) {
                numberOfMessages += resultSet.getInt(1);
            }
        } catch (Exception e) {
            printErrorMessage(e, "gotMessage DEL 1");
            return -1;
        }

        try {
            PreparedStatement pstmt = connection.prepareStatement(sql2);
            pstmt.setInt(1, currentUser.getId());

            ResultSet resultSet = pstmt.executeQuery();

            if (resultSet.next()) {
                numberOfMessages += resultSet.getInt(1);
            }
        } catch (Exception e) {
            printErrorMessage(e, "gotMessage DEL 2");
            return -1;
        }

        return numberOfMessages;
    }

    public boolean registerChat(Chat chat) {
        String sqlStatement = "INSERT INTO Chat VALUES (DEFAULT, ?, ?, ?, ?)";

        try {

            PreparedStatement pstmt = connection.prepareStatement(sqlStatement);
            pstmt.setInt(1, chat.getUserCurrent().getId());
            pstmt.setInt(2, chat.getUserOther().getId());
            pstmt.setBoolean(3, false);
            pstmt.setBoolean(4, false);

            pstmt.executeUpdate();

            return true;
        } catch (Exception e) {
            printErrorMessage(e, "registrer chat");
        }
        return false;
    }

    public boolean isChatRegistered(User userCurrent, User userOther) {
        String sqlStatement = "SELECT Chat.chat_id FROM Chat "
                + " WHERE (Chat.user_id1 = ? AND Chat.user_id2 = ? )"
                + " OR (Chat.user_id1 = ? AND Chat.user_id2 = ? )";

        try {
            PreparedStatement pstmt = connection.prepareStatement(sqlStatement);
            pstmt.setInt(1, userCurrent.getId());
            pstmt.setInt(2, userOther.getId());
            pstmt.setInt(3, userOther.getId());
            pstmt.setInt(4, userCurrent.getId());

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

        String sql2 = "UPDATE Chat SET Chat.read1 = ?, Chat.read2 = ?"
                + " WHERE Chat.chat_id = ? ";

        try {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setTimestamp(1, message.getTimestamp());
            pstmt.setString(2, message.getText());
            pstmt.setInt(3, chatId);

            pstmt.executeUpdate();

            PreparedStatement pstmt2 = connection.prepareStatement(sql2);
            pstmt2.setBoolean(1, false);
            pstmt2.setBoolean(2, false);
            pstmt2.setInt(3, chatId);

            pstmt2.executeUpdate();

            return true;

        } catch (Exception e) {
            printErrorMessage(e, "registrer melding");
        }
        return false;
    }

    public boolean markAsRead(User currentUser, int chatId) {

        boolean isUserOne = false;

        String sql1 = "SELECT Chat.user_id1 FROM Chat WHERE Chat.chat_id = ?";
        String sql2 = "UPDATE Chat SET Chat.read1 = true WHERE Chat.chat_id = ?";
        String sql3 = "UPDATE Chat SET Chat.read2 = true WHERE Chat.chat_id = ?";

        // CHECKS IF THE CURRENT USER IS STORED AS USER-1 OR USER-2
        try {
            PreparedStatement pstmt = connection.prepareStatement(sql1);
            pstmt.setInt(1, chatId);

            ResultSet resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
                if (resultSet.getInt(1) == currentUser.getId()) {
                    isUserOne = true;
                }
            }

            try {
                if (isUserOne) {
                    pstmt = connection.prepareStatement(sql2);
                    pstmt.setInt(1, chatId);
                } else {
                    pstmt = connection.prepareStatement(sql3);
                    pstmt.setInt(1, chatId);
                }

                pstmt.executeUpdate();
                return true;
            } catch (Exception e) {
                printErrorMessage(e, "markAsRead del 2");
            }

        } catch (Exception e) {
            printErrorMessage(e, "markAsRead");
        }

        return false;
    }
    // ** ** ** ** ** **

    // PRIVATE HELPER METHODS
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
    // ** ** ** ** ** **

    // PASSWORD GENERATION AND HASHING
    private String hashString(String string) {
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
    // ** ** ** ** ** **
}
