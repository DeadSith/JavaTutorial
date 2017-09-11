package lab12;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.util.Set;
import java.util.TreeSet;

import static org.testng.Assert.assertEquals;

public class FacultyTest {
    Department department;
    Set<String> subjects;
    Set<String> teachers;
    Faculty faculty;

    @BeforeTest
    void setup() {
        department = new Department("Test", LocalDate.of(1984, 1, 1), "+300000245");
        subjects = new TreeSet<>();
        subjects.add("Subject 1");
        subjects.add("Subject 2");
        teachers = new TreeSet<>();
        teachers.add("Teacher 1");
        teachers.add("Teacher 2");
        faculty = new Faculty("Test", LocalDate.of(1992, 1, 1), department, teachers, subjects);

    }

    @DataProvider
    public Object[][] addTeacherProvider() {
        return new Object[][]{{"Teacher 1", false},
                {"Teacher 3", true}};
    }

    @Test(dataProvider = "addTeacherProvider")
    public void addTeacherTest(String name, boolean check) {
        assertEquals(faculty.addTeacher(name), check);
    }

    @DataProvider
    public Object[][] removeTeacherProvider() {
        return new Object[][]{{"Teacher 1", true},
                {"Teacher 4", false}};
    }


    @Test(dataProvider = "removeTeacherProvider")
    void removeTeacherTest(String name, boolean check) {
        assertEquals(faculty.removeTeacher(name), check);
    }


    @DataProvider
    public Object[][] addSubjectProvider() {
        return new Object[][]{{"Subject 1", false},
                {"Subject 3", true}};
    }

    @Test(dataProvider = "addSubjectProvider")
    public void addSubjectTest(String name, boolean check) {
        assertEquals(faculty.addSubject(name), check);
    }

    @DataProvider
    public Object[][] removeSubjectProvider() {
        return new Object[][]{{"Subject 1", true},
                {"Subject 4", false}};
    }


    @Test(dataProvider = "removeSubjectProvider")
    void removeSubjectTest(String name, boolean check) {
        assertEquals(faculty.removeSubject(name), check);
    }


    @Test
    void toStringTest() {
        Faculty faculty = new Faculty("Test", LocalDate.of(1992, 1, 1), department, teachers, subjects);
        assertEquals(faculty.toString(), "This is Test faculty created on 1992-01-01.");
    }

    @Test
    void hashTest() {
        Faculty faculty1 = new Faculty("Test", LocalDate.of(1992, 1, 1), department, null, null);
        Faculty faculty2 = new Faculty("Test", LocalDate.of(1994, 1, 1), department, null, null);
        assertEquals(faculty1.hashCode(), faculty2.hashCode());
    }

}