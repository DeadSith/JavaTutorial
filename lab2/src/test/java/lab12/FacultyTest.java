package lab12;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.testng.Assert.assertEquals;

public class FacultyTest {
    Department department;
    ArrayList<String> subjects;
    ArrayList<String> teachers;

    @BeforeTest
    void setup() {
        department = new Department("Test", LocalDate.of(1984, 1, 1), "+300000245");
        subjects = new ArrayList<>();
        subjects.add("Subject 1");
        subjects.add("Subject 2");
        teachers = new ArrayList<>();
        teachers.add("Teacher 1");
        teachers.add("Teacher 2");
    }

    @Test
    void teacherTest() {
        Faculty faculty = new Faculty("Test", LocalDate.of(1992, 1, 1), department, teachers, subjects);
        assertEquals(faculty.addTeacher("Teacher 1"), false);
        assertEquals(faculty.getTeachersCount(), 2);
        assertEquals(faculty.addTeacher("Teacher 3"), true);
        assertEquals(faculty.getTeachersCount(), 3);
        assertEquals(faculty.removeTeacher("Teacher 4"), false);
        assertEquals(faculty.getTeachersCount(), 3);
        assertEquals(faculty.removeTeacher("Teacher 2"), true);
        assertEquals(faculty.removeTeacher("Teacher 1"), true);
        assertEquals(faculty.removeTeacher("Teacher 3"), true);
        assertEquals(faculty.removeTeacher("Teacher 1"), false);
        assertEquals(faculty.getTeachersCount(), 0);
        faculty.getTeachers();
    }

    @Test
    void subjectsTest() {
        Faculty faculty = new Faculty("Test", LocalDate.of(1992, 1, 1), department, teachers, subjects);
        assertEquals(faculty.addSubject("Subject 1"), false);
        assertEquals(faculty.getSubjectsCount(), 2);
        assertEquals(faculty.addSubject("Subject 3"), true);
        assertEquals(faculty.getSubjectsCount(), 3);
        assertEquals(faculty.removeSubject("Subject 4"), false);
        assertEquals(faculty.getSubjectsCount(), 3);
        assertEquals(faculty.removeSubject("Subject 2"), true);
        assertEquals(faculty.removeSubject("Subject 1"), true);
        assertEquals(faculty.removeSubject("Subject 3"), true);
        assertEquals(faculty.removeSubject("Subject 1"), false);
        assertEquals(faculty.getSubjectsCount(), 0);
        faculty.getSubjects();
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