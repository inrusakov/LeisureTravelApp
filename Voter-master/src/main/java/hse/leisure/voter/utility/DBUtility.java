package hse.leisure.voter.utility;

import hse.leisure.voter.configuration.MySQL;
import org.springframework.stereotype.Controller;

import javax.annotation.PostConstruct;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Objects;

@Controller
public class DBUtility {
    /**
     * Проверить целостность базы данных.
     */
    @PostConstruct
    public void DatabaseCheck() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String ipAddress = "jdbc:mysql://" + MySQL.IP + ":" + MySQL.Port + "/Voter?useUnicode=true&serverTimezone=UTC";
            Connection connection = null;
            Statement statement = null;
            ResultSet resultSet = null;
            try {
                connection = DriverManager.getConnection(ipAddress, MySQL.Username, MySQL.Password);
                statement = connection.createStatement();
                resultSet = statement.executeQuery("SELECT * FROM Voter_Vote");
            } catch (SQLException E) {
                System.out.println(Arrays.toString(E.getStackTrace()));
            }
            try {
                while (Objects.requireNonNull(resultSet).next()) {
                    resultSet.getString("VID");
                }
            } catch (SQLException SQLE) {
                SQLE.printStackTrace();
            } catch (NullPointerException NPE) {
                try {
                    String SQL = "CREATE TABLE `Voter_Vote` (\n" +
                            "  `VID` int(11) NOT NULL AUTO_INCREMENT,\n" +
                            "  `Title` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL,\n" +
                            "  `Describe` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL,\n" +
                            "  `Selection` varchar(10000) CHARACTER SET utf8mb4 DEFAULT NULL,\n" +
                            "  `Type` int(11) DEFAULT '0',\n" +
                            "  `Limit` int(11) DEFAULT '-1',\n" +
                            "  PRIMARY KEY (`VID`)\n" +
                            ") ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;";
                    statement.executeUpdate(SQL);
                    SQL = "INSERT INTO `Voter_Vote` VALUES (1, 'Title', 'Description', '<%1<%92<%Vote1<%2<%42<%Vote2<%3<%41<%Vote3<%4<%37<%Vote4<%5<%48<%Vote5<%6<%50<%Vote6', 0, 2);";
                    statement.executeUpdate(SQL);
                } catch (NullPointerException E2) {
                    System.out.println(Arrays.toString(E2.getStackTrace()));
                } catch (Exception E) {
                    E.printStackTrace();
                }
            }
            try {
                Objects.requireNonNull(resultSet).close();
                Objects.requireNonNull(statement).close();
                connection.close();
            } catch (SQLException E) {
                System.out.println(Arrays.toString(E.getStackTrace()));
            } catch (NullPointerException ignored) {
            }
        } catch (ClassNotFoundException CNFE) {
            System.out.println(Arrays.toString(CNFE.getStackTrace()));
        }
    }
}
