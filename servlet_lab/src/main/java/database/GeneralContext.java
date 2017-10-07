package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class GeneralContext {
    public static Connection getNewConnection() throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");
        String url = "jdbc:postgresql:postgres";
        Properties props = new Properties();
        props.setProperty("user", "user");
        props.setProperty("password", "password");
        Connection conn = DriverManager.getConnection(url, props);
        return conn;
    }

    public static void setDatabase() throws SQLException, ClassNotFoundException {
        Connection conn = getNewConnection();
        createSchema(conn);
        DepartmentContext.createDepartmentsTable(conn);
        FacultyContext.createFacultiesTable(conn);
        conn.close();
    }

    private static void createSchema(Connection conn) throws SQLException {
        Statement st = conn.createStatement();
        st.execute("CREATE SCHEMA java\n" +
                "    AUTHORIZATION \"user\";\n" +
                "\n" +
                "ALTER DEFAULT PRIVILEGES IN SCHEMA java\n" +
                "GRANT ALL ON TABLES TO \"user\";");
    }
}
