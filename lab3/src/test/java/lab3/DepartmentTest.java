package lab3;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.util.Set;
import java.util.TreeSet;

import static org.testng.Assert.assertEquals;

public class DepartmentTest {
    Department department;


    @BeforeMethod
    void setup() {
        Faculty f1 = new FacultyBuilder().setName("Test3").setCreationDate(LocalDate.of(1992, 2, 2)).setDepartment(null).createFaculty();
        Faculty f2 = new FacultyBuilder().setName("Test1").setCreationDate(LocalDate.of(1992, 2, 2)).setDepartment(null).createFaculty();
        Faculty f3 = new FacultyBuilder().setName("Test2").setCreationDate(LocalDate.of(1992, 2, 2)).setDepartment(null).createFaculty();
        fillTeachersAndSubjects(f1);
        fillTeachersAndSubjects(f2);
        fillTeachersAndSubjects(f3);
        TreeSet<Faculty> faculties = new TreeSet<>();
        faculties.add(f1);
        faculties.add(f2);
        faculties.add(f3);
        department = new DepartmentBuilder().setName("Test").setCreationDate(LocalDate.of(1984, 1, 1)).setPhoneNumber("+300000245").setFaculties(faculties).createDepartment();

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
        Set<Faculty> sorted = department.getSortedFaculties();
        assertEquals(sorted.toArray(), new Faculty[]{new FacultyBuilder().setName("Test1").setCreationDate(LocalDate.of(1992, 2, 2)).setDepartment(department).createFaculty(),
                new FacultyBuilder().setName("Test2").setCreationDate(LocalDate.of(1992, 2, 2)).setDepartment(department).createFaculty(),
                new FacultyBuilder().setName("Test3").setCreationDate(LocalDate.of(1992, 2, 2)).setDepartment(department).createFaculty()});
    }

    @DataProvider
    public Object[][] addFacultyProvider() {
        return new Object[][]{{new FacultyBuilder().setName("Test1").setCreationDate(LocalDate.of(1992, 2, 2)).setDepartment(department).createFaculty(), false},
                {new FacultyBuilder().setName("Test4").setCreationDate(LocalDate.of(1992, 2, 2)).setDepartment(department).createFaculty(), true}};
    }

    @Test(dataProvider = "addFacultyProvider")
    void addFacultyTest(Faculty f, boolean check) {
        assertEquals(department.addFaculty(f), check);
    }

    @DataProvider
    public Object[][] removeFacultyProvider() {
        return new Object[][]{{new FacultyBuilder().setName("Test1").setCreationDate(LocalDate.of(1992, 2, 2)).setDepartment(department).createFaculty(), true},
                {new FacultyBuilder().setName("Test5").setCreationDate(LocalDate.of(1992, 2, 2)).setDepartment(department).createFaculty(), false}};
    }

    @Test(dataProvider = "removeFacultyProvider")
    void removeFacultyTest(Faculty f, boolean check) {
        assertEquals(department.removeFaculty(f), check);
    }

    @Test
    void toStringTest() {
        assertEquals(department.toString(), "This is Test department created on 1984-01-01.");
    }

    @Test
    void hashTest() {
        assertEquals(department.hashCode(), new DepartmentBuilder().setName("Test").setCreationDate(LocalDate.of(1984, 1, 1)).setPhoneNumber("").createDepartment().hashCode());
    }
}