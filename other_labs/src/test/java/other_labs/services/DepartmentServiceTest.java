package other_labs.services;

import other_labs.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.TreeSet;

import static org.testng.Assert.assertEquals;

public class DepartmentServiceTest {
    Department department;

    @BeforeMethod
    void setup() {
        Faculty f1 = new FacultyBuilder().setName("Test3").setCreationDate(LocalDate.of(1992, 2, 2)).setDepartment(null).build();
        Faculty f2 = new FacultyBuilder().setName("Test1").setCreationDate(LocalDate.of(1992, 2, 2)).setDepartment(null).build();
        Faculty f3 = new FacultyBuilder().setName("Test2").setCreationDate(LocalDate.of(1992, 2, 2)).setDepartment(null).build();
        DepartmentTest.fillTeachersAndSubjects(f1);
        DepartmentTest.fillTeachersAndSubjects(f2);
        DepartmentTest.fillTeachersAndSubjects(f3);
        TreeSet<Faculty> faculties = new TreeSet<>();
        faculties.add(f1);
        faculties.add(f2);
        faculties.add(f3);
        department = new DepartmentBuilder().setName("Test").setCreationDate(LocalDate.of(1984, 1, 1)).setPhoneNumber("+300000245").setFaculties(faculties).build();
    }

    @Test
    public void testGetAverageTeacherLoad() throws Exception {
        Faculty f3 = new FacultyBuilder().setName("Test5").setCreationDate(LocalDate.of(1992, 2, 2)).setDepartment(null).build();
        DepartmentTest.fillTeachersAndSubjects(f3);
        f3.addTeacher("qwerty");
        department.addFaculty(f3);
        HashMap<String, Double> map = new LinkedHashMap<>();
        map.put("Test5", 1.0);
        map.put("Test1", 4.0 / 3);
        map.put("Test2", 4.0 / 3);
        map.put("Test3", 4.0 / 3);
        assertEquals(map, DepartmentService.getAverageTeacherLoad(department));
    }

}