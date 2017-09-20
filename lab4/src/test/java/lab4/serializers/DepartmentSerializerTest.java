package lab4.serializers;

import lab4.Department;
import lab4.DepartmentBuilder;
import lab4.Faculty;
import lab4.FacultyBuilder;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;

import static org.testng.Assert.*;

public class DepartmentSerializerTest {
    Department d1,d2;
    ArrayList<Department> departments;

    @BeforeTest
    public void setup(){
        d1 = new DepartmentBuilder().setName("asd").setCreationDate(LocalDate.now()).setPhoneNumber("+1234567890").build();
        d2 = new DepartmentBuilder().setName("asdqwe").setCreationDate(LocalDate.now()).setPhoneNumber("+1234567890").build();
        Faculty f1 = new FacultyBuilder().setName("asdwqeqe").setCreationDate(LocalDate.of(1984,10,10)).setDepartment(d1).build();
        Faculty f2 = new FacultyBuilder().setName("qweqwe").setCreationDate(LocalDate.of(1984,1,10)).setDepartment(d2).build();
        d2.addFaculty(f2);
        d1.addFaculty(f1);
        departments = new ArrayList<>();
        departments.add(d1);
        departments.add(d2);
    }


    @Test(priority = 1)
    public void testSerialize() throws Exception {
        DepartmentXMLSerializer s = new DepartmentXMLSerializer();
        s.serialize(d1,new File("test.json"));
    }

    @Test(priority = 2)
    public void testDeserialize() throws Exception {
        DepartmentXMLSerializer s = new DepartmentXMLSerializer();
        assertEquals(s.deserialize(new File("test.json")),d1);
    }

    @Test(priority = 3)
    public void testSerializeCollection() throws Exception {
        DepartmentJSONSerializer s = new DepartmentJSONSerializer();
        s.serializeCollection(departments,new File("test.json"));
    }

    @Test(priority = 4)
    public void testDeserializeCollection() throws Exception {
        DepartmentJSONSerializer s = new DepartmentJSONSerializer();
        assertEquals(s.deserializeCollection(new File("test.json")),departments);
    }

}