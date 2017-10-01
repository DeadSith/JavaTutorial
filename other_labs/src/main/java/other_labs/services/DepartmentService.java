package other_labs.services;

import other_labs.Department;
import other_labs.Faculty;

import java.util.LinkedHashMap;
import java.util.Map;
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


}
