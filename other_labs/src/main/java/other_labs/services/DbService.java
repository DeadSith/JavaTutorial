package other_labs.services;

import other_labs.Department;
import other_labs.DepartmentBuilder;
import other_labs.Faculty;
import other_labs.FacultyBuilder;

import java.sql.*;
import java.sql.Date;
import java.util.*;

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

    /**
     * @return all departments in db, or empty collection if there was error
     */
    public static Collection<Department> getDepartments() throws SQLException, ClassNotFoundException {
        List<Department> departments = new LinkedList<>();
        Connection conn = getNewConnection();
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM java.departments;");
        while (rs.next()) {
            TreeSet<Faculty> f = getFaculties(rs.getInt("id"));
            Department d = new DepartmentBuilder().setId(rs.getInt("id"))
                    .setName(rs.getString("name")).setPhoneNumber(rs.getString("phone_number"))
                    .setCreationDate(rs.getDate("creation_date").toLocalDate())
                    .setFaculties(f).build();
            departments.add(d);
        }
        conn.close();
        return departments;
    }

    /**
     * @param parentId
     * @return all faculties which belong to Department with id {@code parentId}
     */
    private static TreeSet<Faculty> getFaculties(int parentId) throws SQLException, ClassNotFoundException {
        TreeSet<Faculty> faculties = new TreeSet<>();
        Connection conn = getNewConnection();
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM java.faculties WHERE department=" + parentId + ";");
        while (rs.next()) {
            Faculty f = new FacultyBuilder().setId(rs.getInt("id"))
                    .setName(rs.getString("name"))
                    .setCreationDate(rs.getDate("creation_date").toLocalDate())
                    .setTeachers(new TreeSet<>(Arrays.asList(rs.getString("teachers").split(","))))
                    .setSubjects(new TreeSet<>(Arrays.asList(rs.getString("subjects").split(","))))
                    .build();
            faculties.add(f);
        }
        conn.close();
        return faculties;
    }

    /**
     * @param d department to add. Id is added automatically.
     */
    public static void addDepartment(Department d) throws SQLException, ClassNotFoundException {
        Connection conn = getNewConnection();
        Statement st = conn.createStatement();
        int r = st.executeUpdate("INSERT INTO java.departments VALUES ('" + Date.valueOf(d.getCreationDate()) + "', DEFAULT, '" + d.getName() +
                "', '" + d.getPhoneNumber() + "');");
        ResultSet rs = st.executeQuery("SELECT id FROM java.departments WHERE name='" + d.getName() + "';");
        rs.next();
        d.setId(rs.getInt("id"));
        for (Faculty f : d.getSortedFaculties()) {
            addFaculty(f);
        }
        conn.close();
    }

    /**
     * @param id
     * @return faculty with id {@code id}
     */
    public static Faculty getFaculty(int id) throws SQLException, ClassNotFoundException {
        Connection conn = getNewConnection();
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM java.faculties WHERE id=" + id + ";");
        if (rs.next()) {
            Faculty f = new FacultyBuilder().setId(rs.getInt("id"))
                    .setName(rs.getString("name"))
                    .setCreationDate(rs.getDate("creation_date").toLocalDate())
                    .setTeachers(new TreeSet<>(Arrays.asList(rs.getString("teachers").split(","))))
                    .setSubjects(new TreeSet<>(Arrays.asList(rs.getString("subjects").split(","))))
                    .build();
            return f;
        }
        conn.close();
        return null;
    }

    /**
     * @param id
     * @return department with id {@code id}
     */
    public static Department getDepartment(int id) throws SQLException, ClassNotFoundException {
        Connection conn = getNewConnection();
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM java.departments WHERE id=" + id + ";");
        if (rs.next()) {
            TreeSet<Faculty> f = getFaculties(id);
            Department d = new DepartmentBuilder().setId(id)
                    .setName(rs.getString("name")).setPhoneNumber(rs.getString("phone_number"))
                    .setCreationDate(rs.getDate("creation_date").toLocalDate())
                    .setFaculties(f).build();
            return d;
        }
        conn.close();
        return null;
    }

    /**
     * @param f faculty to add. Id is assigned automatically
     */
    private static void addFaculty(Faculty f) throws SQLException, ClassNotFoundException {

        Connection conn = getNewConnection();
        Statement st = conn.createStatement();
        int r = st.executeUpdate("INSERT INTO java.faculties VALUES (DEFAULT, '" + f.getName() + "', '" +
                Date.valueOf(f.getCreationDate()) + "', '" + concatTreeSet(f.getTeachers()) + "', '" +
                concatTreeSet(f.getSubjects()) + "', " + f.getDepartment().getId() + ");");
        ResultSet rs = st.executeQuery("SELECT id FROM java.faculties WHERE name='" + f.getName() + "' AND department='" + f.getDepartment().getId() + "';");
        rs.next();
        f.setId(rs.getInt("id"));
        conn.close();
    }


    /**
     * @param f Faculty to update
     * @return update was successful
     */
    public static boolean updateFaculty(Faculty f) throws SQLException, ClassNotFoundException {
        Connection conn = getNewConnection();
        Statement st = conn.createStatement();
        int rs = st.executeUpdate("UPDATE java.faculties SET name='" + f.getName()
                + "', creation_date='" + f.getCreationDate() + "', teachers='"
                + concatTreeSet(f.getTeachers()) + "', subjects='"
                + concatTreeSet(f.getSubjects()) + "',department="
                + f.getDepartment().getId() + " WHERE id=" + f.getId() + ";");
        conn.close();
        return (rs != 0);
    }

    /**
     * @param d department to update
     * @return update was successful
     */
    public static boolean updateDepartment(Department d) throws SQLException, ClassNotFoundException {

        Connection conn = getNewConnection();
        Statement st = conn.createStatement();
        int rs = st.executeUpdate("UPDATE java.departments SET name='" + d.getName()
                + "', creation_date='" + Date.valueOf(d.getCreationDate()) + "', phone_number='"
                + d.getPhoneNumber() + "' WHERE id=" + d.getId() + ";");
        conn.close();
        return (rs != 0);
    }

    /**
     * @param id department to delete. Faculties are deleted automatically
     */
    public static void deleteDepartment(int id) throws SQLException, ClassNotFoundException {
        Connection conn = getNewConnection();
        Statement st = conn.createStatement();
        int rs = st.executeUpdate("DELETE FROM java.departments WHERE id=" + id + ";");
        conn.close();
    }

    /**
     * @param id faculty to delete
     */
    public static void deleteFaculty(int id) throws SQLException, ClassNotFoundException {
        Connection conn = getNewConnection();
        Statement st = conn.createStatement();
        int rs = st.executeUpdate("DELETE FROM java.faculties WHERE id=" + id + ";");
        conn.close();
    }

    /**
     * creates new schema
     */
    public static void setDatabase() throws SQLException, ClassNotFoundException {
        Connection conn = getNewConnection();
        createSchema(conn);
        createDepartmentsTable(conn);
        createFacultiesTable(conn);
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

    private static void createDepartmentsTable(Connection conn) throws SQLException {
        Statement st = conn.createStatement();
        st.execute("CREATE TABLE java.departments\n" +
                "(\n" +
                "    creation_date DATE,\n" +
                "    id INTEGER NOT NULL DEFAULT nextval('java.departments_id_seq'::REGCLASS),\n" +
                "    name CHARACTER VARYING(50) COLLATE pg_catalog.\"default\" NOT NULL,\n" +
                "    phone_number CHARACTER VARYING(15) COLLATE pg_catalog.\"default\" NOT NULL,\n" +
                "    CONSTRAINT departments_pkey PRIMARY KEY (id)\n" +
                ")\n" +
                "WITH (\n" +
                "    OIDS = FALSE\n" +
                ")\n" +
                "TABLESPACE pg_default;\n" +
                "\n" +
                "ALTER TABLE java.departments\n" +
                "    OWNER TO \"user\";\n" +
                "\n" +
                "GRANT ALL ON TABLE java.departments TO \"user\";");
    }

    private static void createFacultiesTable(Connection conn) throws SQLException {
        Statement st = conn.createStatement();
        st.execute("CREATE TABLE java.faculties\n" +
                "(\n" +
                "    id INTEGER NOT NULL DEFAULT nextval('java.faculties_id_seq'::REGCLASS),\n" +
                "    name CHARACTER VARYING(50) COLLATE pg_catalog.\"default\" NOT NULL,\n" +
                "    creation_date DATE NOT NULL,\n" +
                "    teachers TEXT COLLATE pg_catalog.\"default\",\n" +
                "    subjects TEXT COLLATE pg_catalog.\"default\",\n" +
                "    department INTEGER,\n" +
                "    CONSTRAINT faculties_pkey PRIMARY KEY (id),\n" +
                "    CONSTRAINT department FOREIGN KEY (department)\n" +
                "        REFERENCES java.departments (id) MATCH SIMPLE\n" +
                "        ON UPDATE NO ACTION\n" +
                "        ON DELETE CASCADE\n" +
                "        DEFERRABLE INITIALLY DEFERRED\n" +
                ")\n" +
                "WITH (\n" +
                "    OIDS = FALSE\n" +
                ")\n" +
                "TABLESPACE pg_default;\n" +
                "\n" +
                "ALTER TABLE java.faculties\n" +
                "    OWNER TO \"user\";\n" +
                "\n" +
                "GRANT ALL ON TABLE java.faculties TO \"user\";");
    }

    private static String concatTreeSet(Set<String> input) {
        StringBuilder sb = new StringBuilder();
        for (String s : input) {
            sb.append(s);
            sb.append(",");
        }
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }
}
