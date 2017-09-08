package lab12;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.testng.Assert.assertEquals;

public class DepartmentTest {
    Department department;


    @BeforeMethod
    void setup() {
        department = new Department("Test", LocalDate.of(1984, 1, 1), "+300000245");
        department.addFaculty(new Faculty("Test3", LocalDate.of(1992, 2, 2), department));
        department.addFaculty(new Faculty("Test1", LocalDate.of(1992, 2, 2), department));
        department.addFaculty(new Faculty("Test2", LocalDate.of(1992, 2, 2), department));
        fillTeachersAndSubjects(department.getFaculties().get(0));
        fillTeachersAndSubjects(department.getFaculties().get(1));
        fillTeachersAndSubjects(department.getFaculties().get(2));
    }

    private void fillTeachersAndSubjects(Faculty faculty) {
        faculty.addTeacher("t1");
        faculty.addTeacher("t2");
        faculty.addTeacher("t3");
        faculty.addSubject("s1");
        faculty.addSubject("s2");
        faculty.addSubject("s3");
        faculty.addSubject("s4");
    }

    @Test
    void getTeachersCountTest() {
        assertEquals(department.getTeachersCount(), 9);
    }

    @Test
    void getSubjectsCountTest() {
        assertEquals(department.getSubjectsCount(), 12);
    }

    @Test
    void getSortedFacultiesTest() {
        List<Faculty> sorted = department.getSortedFaculties();
        assertEquals(sorted.get(0), new Faculty("Test1", LocalDate.of(1992, 2, 2), department));
        assertEquals(sorted.get(2), new Faculty("Test3", LocalDate.of(1992, 2, 2), department));
    }

    @Test
    void facultiesTest() {
        assertEquals(department.addFaculty(new Faculty("Test1", LocalDate.of(1992, 2, 2), department)), false);
        assertEquals(department.addFaculty(new Faculty("Test4", LocalDate.of(1992, 2, 2), department)), true);
        assertEquals(department.getFacultiesCount(), 4);
        assertEquals(department.removeFaculty(new Faculty("Test5", LocalDate.of(1992, 2, 2), department)), false);
        assertEquals(department.removeFaculty(new Faculty("Test1", LocalDate.of(1992, 2, 2), department)), true);
        assertEquals(department.getFacultiesCount(), 3);
    }

    @Test
    void sortTest() {
        ArrayList<Department> departments = new ArrayList<>();
        departments.add(new Department("D2", LocalDate.of(1992, 2, 2), ""));
        departments.add(new Department("C4", LocalDate.of(1992, 2, 2), ""));
        departments.add(new Department("F12", LocalDate.of(1992, 2, 2), ""));
        departments.add(new Department("A13123123", LocalDate.of(1992, 2, 2), ""));
        Collections.sort(departments);
        assertEquals(departments.get(0), new Department("A13123123", LocalDate.of(1992, 2, 2), ""));
        assertEquals(departments.get(3), new Department("F12", LocalDate.of(1992, 2, 2), ""));
    }

    @Test
    void toStringTest() {
        assertEquals(department.toString(), "This is Test department created on 1984-01-01.");
    }

    @Test
    void hashTest() {
        assertEquals(department.hashCode(), new Department("Test", LocalDate.of(1984, 1, 1), "").hashCode());
    }
}