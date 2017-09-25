package lab4.serializers;

import lab4.Department;
import lab4.DepartmentBuilder;
import lab4.Faculty;
import lab4.FacultyBuilder;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.ArrayList;

import static org.testng.Assert.assertEquals;

public class DepartmentSerializerTest {
    Department d1, d2;
    ArrayList<Department> departments;

    @BeforeTest
    public void setup() {
        d1 = new DepartmentBuilder().setName("asd").setCreationDate(LocalDate.now()).setPhoneNumber("+1234567890").build();
        d2 = new DepartmentBuilder().setName("asdqwe").setCreationDate(LocalDate.now()).setPhoneNumber("+1234567890").build();
        Faculty f1 = new FacultyBuilder().setName("asdwqeqe").setCreationDate(LocalDate.of(1984, 10, 10)).setDepartment(d1).build();
        Faculty f2 = new FacultyBuilder().setName("qweqwe").setCreationDate(LocalDate.of(1984, 1, 10)).setDepartment(d2).build();
        d2.addFaculty(f2);
        d1.addFaculty(f1);
        departments = new ArrayList<>();
        departments.add(d1);
        departments.add(d2);
    }

    @DataProvider
    public Object[][] serializationProvider() {
        return new Object[][]{{new DepartmentJSONSerializer(), "test1.json"},
                {new DepartmentXMLSerializer(), "test1.xml"},
                {new DepartmentTextSerializer(), "test1.txt"}};
    }

    @Test(priority = 1, dataProvider = "serializationProvider")
    public void testSerialize(Serializer<Department> s, String fileName) throws Exception {
        s.serialize(d1, new PrintWriter(new File(fileName)));
    }

    @Test(priority = 2, dataProvider = "serializationProvider")
    public void testDeserialize(Serializer<Department> s, String fileName) throws Exception {
        assertEquals(s.deserialize(new FileInputStream(new File(fileName))), d1);
    }

    @Test(priority = 3, dataProvider = "serializationProvider")
    public void testSerializeCollection(Serializer<Department> s, String fileName) throws Exception {
        s.serializeCollection(departments, new PrintWriter(new File(fileName)));
    }

    @Test(priority = 4, dataProvider = "serializationProvider")
    public void testDeserializeCollection(Serializer<Department> s, String fileName) throws Exception {
        assertEquals(s.deserializeCollection(new FileInputStream(new File(fileName))), departments);
    }

}