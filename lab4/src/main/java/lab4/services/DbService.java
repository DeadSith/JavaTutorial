package lab4.services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DbService {

    /**
     * @return new connection to DB
     */
    public static Connection getNewConnection() throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");
        String url = "jdbc:postgresql:postgres";
        Properties props = new Properties();
        props.setProperty("user", "user");
        props.setProperty("password", "password");
        Connection conn = DriverManager.getConnection(url, props);
        return conn;
    }
}
