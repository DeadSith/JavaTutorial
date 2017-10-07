package database;

import models.Faculty;
import models.FacultyBuilder;

import java.sql.*;
import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;

import static database.GeneralContext.getNewConnection;

public class FacultyContext {
    static void createFacultiesTable(Connection conn) throws SQLException {
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

    /**
     * @param parentId
     * @return all faculties which belong to Department with id {@code parentId}
     */
    static TreeSet<Faculty> getFaculties(int parentId) throws SQLException, ClassNotFoundException {
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
     * @param f faculty to add. Id is assigned automatically
     */
    public static void addFaculty(Faculty f) throws SQLException, ClassNotFoundException {

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
     * @param id faculty to delete
     */
    public static void deleteFaculty(int id) throws SQLException, ClassNotFoundException {
        Connection conn = getNewConnection();
        Statement st = conn.createStatement();
        int rs = st.executeUpdate("DELETE FROM java.faculties WHERE id=" + id + ";");
        conn.close();
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
