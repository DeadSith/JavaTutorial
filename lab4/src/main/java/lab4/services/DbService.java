package lab4.services;

import lab4.Department;
import lab4.DepartmentBuilder;
import lab4.Faculty;
import lab4.FacultyBuilder;

import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
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
    public static Collection<Department> getDepartments() {
        List<Department> departments = new LinkedList<>();
        try {
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
        } catch (Exception ignored) {

        }
        return departments;
    }

    private static TreeSet<Faculty> getFaculties(int parentId) {
        TreeSet<Faculty> faculties = new TreeSet<>();
        try {
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
        } catch (Exception ignored) {

        }
        return faculties;
    }

    public void addDepartment(Department d) {
        try {
            Connection conn = getNewConnection();
            Statement st = conn.createStatement();
            int r = st.executeUpdate("INSERT INTO java.departments VALUES ('" + Date.valueOf(d.getCreationDate()) + "', DEFAULT, '" + d.getName() +
                    "', '" + d.getPhoneNumber() + "');");
            ResultSet rs = st.executeQuery("SELECT id FROM java.departments WHERE name='" + d.getName() + "';");
            d.setId(rs.getInt("id"));
            for (Faculty f : d.getSortedFaculties()) {
                addFaculty(f);
            }
            conn.close();
        } catch (Exception ignored) {

        }
    }

    private void addFaculty(Faculty f) {
        try {
            Connection conn = getNewConnection();
            Statement st = conn.createStatement();
            int rs = st.executeUpdate("INSERT INTO java.faculties VALUES (DEFAULT, '" + f.getName() + "', '" +
                    Date.valueOf(f.getCreationDate()) + "', '" + concatTreeSet(f.getTeachers()) + "', '" +
                    concatTreeSet(f.getSubjects()) + "', " + f.getDepartment().getId() + ");");
            conn.close();
        } catch (Exception ignored) {

        }
    }

    public void updateFaculty(String fieldName, Object value, int id) {
        try {
            if (fieldName.equals("teachers") || fieldName.equals("subjects"))
                value = concatTreeSet((Set<String>) value);
            if (fieldName.equals("creation_date"))
                value = Date.valueOf((LocalDate) value);
            Connection conn = getNewConnection();
            Statement st = conn.createStatement();
            int rs = st.executeUpdate("UPDATE java.faculties SET " + fieldName
                    + "=" + value.toString() + "WHERE id=" + id + ";");
            conn.close();
        } catch (Exception ignored) {

        }
    }

    public void updateDepartment(String fieldName, Object value, int id) {
        try {
            if (fieldName.equals("creation_date"))
                value = Date.valueOf((LocalDate) value);
            Connection conn = getNewConnection();
            Statement st = conn.createStatement();
            int rs = st.executeUpdate("UPDATE java.departments SET " + fieldName
                    + "=" + value.toString() + "WHERE id=" + id + ";");
            conn.close();
        } catch (Exception ignored) {

        }
    }

    public void deleteDepartment(int id) {
        try {
            Connection conn = getNewConnection();
            Statement st = conn.createStatement();
            int rs = st.executeUpdate("DELETE FROM java.departments WHERE id=" + id + ";");
            conn.close();
        } catch (Exception ignored) {

        }
    }

    public void deleteFaculty(int id) {
        try {
            Connection conn = getNewConnection();
            Statement st = conn.createStatement();
            int rs = st.executeUpdate("DELETE FROM java.faculties WHERE id=" + id + ";");
            conn.close();
        } catch (Exception ignored) {

        }
    }

    public void createDatabase() {
        try {
            Connection conn = getNewConnection();
            Statement st = conn.createStatement();
            st.execute("CREATE SCHEMA java\n" +
                    "    AUTHORIZATION \"user\";\n" +
                    "\n" +
                    "ALTER DEFAULT PRIVILEGES IN SCHEMA java\n" +
                    "GRANT ALL ON TABLES TO \"user\";");
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
            conn.close();
        } catch (Exception ignored) {

        }
    }

    private String concatTreeSet(Set<String> input) {
        StringBuilder sb = new StringBuilder();
        for (String s : input) {
            sb.append(s);
            sb.append(",");
        }
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }
}
