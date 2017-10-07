package database;

import models.Department;
import models.DepartmentBuilder;
import models.Faculty;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeSet;

import static database.GeneralContext.getNewConnection;

public class DepartmentContext {
    static void createDepartmentsTable(Connection conn) throws SQLException {
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

    /**
     * @return all departments in db, or empty collection if there was error
     */
    public static List<Department> getDepartments() throws SQLException, ClassNotFoundException {
        List<Department> departments = new LinkedList<>();
        Connection conn = getNewConnection();
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM java.departments;");
        while (rs.next()) {
            TreeSet<Faculty> f = FacultyContext.getFaculties(rs.getInt("id"));
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
     * @param id
     * @return department with id {@code id}
     */
    public static Department getDepartment(int id) throws SQLException, ClassNotFoundException {
        Connection conn = getNewConnection();
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM java.departments WHERE id=" + id + ";");
        if (rs.next()) {
            TreeSet<Faculty> f = FacultyContext.getFaculties(id);
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
            FacultyContext.addFaculty(f);
        }
        conn.close();
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
}
