package lab4.services;

import lab4.Department;
import lab4.DepartmentBuilder;
import lab4.Faculty;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import java.util.stream.Collectors;

public class DepartmentService {
    /**
     * @param d Department to get info about
     * @return LinkedHashMap with average load of teachers in each faculty sorted by load
     */
    public static Map<String, Double> getAverageTeacherLoad(Department d) {
        return d.getSortedFaculties().stream()
                .sorted(DepartmentService::facultyComparator)
                .collect(Collectors.toMap(Faculty::getName, f -> (double) f.getSubjectsCount() / f.getTeachersCount(), (e1, e2) -> e1, LinkedHashMap::new));
    }

    /**
     * Used for sorting by average load
     */
    private static int facultyComparator(Faculty f1, Faculty f2) {
        double d1 = (double) f1.getSubjectsCount() / f1.getTeachersCount();
        double d2 = (double) f2.getSubjectsCount() / f2.getTeachersCount();
        if (Math.abs(d1 - d2) < 0.000001)
            return 0;
        else if (d1 < d2)
            return -1;
        return 1;
    }

    /**
     * @param conn connection to database. It will be closed
     * @return all departments in db, or empty collection if there was error
     */
    public static Collection<Department> getDepartments(Connection conn) {
        List<Department> departments = new LinkedList<>();
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM java.departments");
            while (rs.next()) {
                TreeSet<Faculty> f = getFaculties(conn, rs.getInt("id"));
                Department d = new DepartmentBuilder().setId(rs.getInt("id"))
                        .setName(rs.getString("name")).setPhoneNumber(rs.getString("phone_number"))
                        .setCreationDate(rs.getDate("creation_date").toLocalDate())
                        .setFaculties(f).build();
            }
            conn.close();
        } catch (Exception ignored) {

        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return departments;
    }

    private static TreeSet<Faculty> getFaculties(Connection conn, int id) {
        //todo
        throw new NotImplementedException();
    }
}
