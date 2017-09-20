package lab4.serializers;

import lab4.Department;
import lab4.DepartmentBuilder;
import lab4.Faculty;
import lab4.FacultyBuilder;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

import static org.testng.Assert.assertEquals;

public class FacultySerializerTest {
    Faculty f1, f2;
    Department d = new DepartmentBuilder().setName("a").setCreationDate(LocalDate.now()).build();
    ArrayList<Faculty> faculties;

    @DataProvider
    public Object[][] serializationProvider() {
        return new Object[][]{{new FacultyJSONSerializer(), "test.json"},
                {new FacultyXMLSerizalizer(), "test.xml"}};
    }

    @BeforeTest
    public void setup() {
        f1 = new FacultyBuilder().setName("asdwqeqe").setCreationDate(LocalDate.of(1984, 10, 10)).setDepartment(d).build();
        f2 = new FacultyBuilder().setName("dsfasdfasdfa").setCreationDate(LocalDate.of(1984, 10, 10)).setDepartment(d).build();
        faculties = new ArrayList<>();
        faculties.add(f1);
        faculties.add(f2);
    }

    @Test(priority = 1, dataProvider = "serializationProvider")
    public void testSerialize(Serializer<Faculty> s, String fileName) throws Exception {
        s.serialize(f1, new File(fileName));
    }

    @Test(priority = 2, dataProvider = "serializationProvider")
    public void testDeserialize(Serializer<Faculty> s, String fileName) throws Exception {
        Faculty f = s.deserialize(new File(fileName));
        d.addFaculty(f);
        assertEquals(f, f1);
    }

    @Test(priority = 3, dataProvider = "serializationProvider")
    public void testSerializeCollection(Serializer<Faculty> s, String fileName) throws Exception {
        s.serializeCollection(faculties, new File(fileName));
    }


    @Test(priority = 4, dataProvider = "serializationProvider")
    public void testDeserializeCollection(Serializer<Faculty> s, String fileName) throws Exception {
        Collection<Faculty> faculties = s.deserializeCollection(new File(fileName));
        for (Faculty f : faculties)
            d.addFaculty(f);
        assertEquals(faculties, this.faculties);
    }
}