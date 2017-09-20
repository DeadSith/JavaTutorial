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
import java.util.Collection;

import static org.testng.Assert.*;

public class FacultyJSONSerializerTest {
    Faculty f1,f2;
    Department d = new DepartmentBuilder().setName("a").setCreationDate(LocalDate.now()).build();
    ArrayList<Faculty> faculties;

    @BeforeTest
    public void setup()
    {
        f1 = new FacultyBuilder().setName("asdwqeqe").setCreationDate(LocalDate.of(1984,10,10)).setDepartment(d).build();
        f2 = new FacultyBuilder().setName("dsfasdfasdfa").setCreationDate(LocalDate.of(1984,10,10)).setDepartment(d).build();
        faculties = new ArrayList<>();
        faculties.add(f1);
        faculties.add(f2);
    }

    @Test(priority = 1)
    public void testSerialize() throws Exception {
        FacultyJSONSerializer s = new FacultyJSONSerializer();
        s.serialize(f1,new File("test.json"));
    }

    @Test(priority = 2)
    public void testDeserialize() throws Exception {
        FacultyJSONSerializer s = new FacultyJSONSerializer();
        Faculty f = s.deserialize(new File("test.json"));
        d.addFaculty(f);
        assertEquals(f,f1);
    }

    @Test(priority = 3)
    public void testSerializeCollection() throws Exception {
        FacultyJSONSerializer s = new FacultyJSONSerializer();
        s.serializeCollection(faculties,new File("test.json"));
    }



    @Test(priority = 4)
    public void testDeserializeCollection() throws Exception {
        FacultyJSONSerializer s = new FacultyJSONSerializer();
        Collection<Faculty> faculties = s.deserializeCollection(new File("test.json"));
        for (Faculty f:faculties )
            d.addFaculty(f);
        assertEquals(faculties,this.faculties);
    }
}