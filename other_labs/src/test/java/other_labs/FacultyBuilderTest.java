package other_labs;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.time.LocalDate;

import static org.testng.Assert.assertEquals;

public class FacultyBuilderTest {

    @DataProvider
    public Object[][] stringProvider() {
        return new Object[][]{{"Name: ASD, dsbc;Created: 2005-02-02;Subjects: qwe, qwer;", new FacultyBuilder().setName("ASD, dsbc").setCreationDate(LocalDate.of(2005, 02, 02)).setDepartment(new DepartmentBuilder().setName("a").build()).build(), 2, 0},
                {"Name: ASD, dsbc;Created: 2005-02-02;Teachers: bvbv;Subjects: rty, adsd;", new FacultyBuilder().setName("ASD, dsbc").setCreationDate(LocalDate.of(2005, 02, 02)).setDepartment(new DepartmentBuilder().setName("a").build()).build(), 2, 1}};
    }

    @Test(dataProvider = "stringProvider")
    public void testFromString(String input, Faculty check, int subjectsCount, int teachersCount) throws Exception {
        Faculty f = new FacultyBuilder().fromString(input).setDepartment(check.getDepartment()).build();
        assertEquals(f, check);
        assertEquals(f.getSubjectsCount(), subjectsCount);
        assertEquals(f.getTeachersCount(), teachersCount);
    }

}
