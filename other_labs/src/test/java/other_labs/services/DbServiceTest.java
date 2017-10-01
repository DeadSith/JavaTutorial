package other_labs.services;

import other_labs.*;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.util.Collection;
import java.util.TreeSet;

import static org.testng.Assert.assertEquals;

public class DbServiceTest {
    Department department;
    Faculty f1;

    @BeforeClass
    void setup() {
        f1 = new FacultyBuilder().setName("Test3").setCreationDate(LocalDate.of(1992, 2, 2)).setDepartment(null).build();
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

    @Test(priority = 1)
    public void testAddDepartment() throws Exception {
        DbService.addDepartment(department);
    }

    @Test(priority = 2)
    public void testGetDepartments() throws Exception {
        Collection<Department> departments = DbService.getDepartments();
        assertEquals(departments.size(), 1);
    }

    @Test(priority = 2)
    public void testUpdateFaculty() throws Exception {
        DbService.updateFaculty("creation_date", LocalDate.of(1994, 1, 1), f1.getId());
        assertEquals(DbService.getFaculty(f1.getId()).getCreationDate(), LocalDate.of(1994, 1, 1));
    }

    @Test(priority = 2)
    public void testUpdateDepartment() throws Exception {
        DbService.updateDepartment("phone_number", "+38000078456", department.getId());
        assertEquals(DbService.getDepartment(department.getId()).getPhoneNumber(), "+38000078456");
    }

    @Test(priority = 3)
    public void testDeleteFaculty() throws Exception {
        DbService.deleteFaculty(f1.getId());
        assertEquals(DbService.getFaculty(f1.getId()), null);
    }

    @Test(priority = 4)
    public void testDeleteDepartment() throws Exception {
        DbService.deleteDepartment(department.getId());
        assertEquals(DbService.getFaculty(department.getId()), null);
        for (Faculty f : department.getSortedFaculties()) {
            assertEquals(DbService.getFaculty(f.getId()), null);
        }
    }

    @Test
    public void testGetNewConnection() throws Exception {
        DbService.getNewConnection();
    }

}