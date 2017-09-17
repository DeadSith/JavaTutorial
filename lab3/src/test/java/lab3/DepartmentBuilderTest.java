package lab3;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.time.LocalDate;

import static org.testng.Assert.*;

public class DepartmentBuilderTest {


    @DataProvider
    public Object[][] stringProvider() {
        return new Object[][]{{"Name: aqweqwe;Created: 2005-02-02;Number: +12345678910;",new DepartmentBuilder().setName("aqweqwe").setCreationDate(LocalDate.of(2005,02,02)).build(),0},
                {"Name: aqweqwe;Created: 2005-02-02;Number: +12345678910;Faculties: Name: ASD, dsbc;Created: 2005-02-02;Subjects: 123, 456/Name: ASD, dsc;Created: 2005-02-02;Subjects: 123, 456",new DepartmentBuilder().setName("aqweqwe").setCreationDate(LocalDate.of(2005,02,02)).build(),2}};
    }

    @Test(dataProvider = "stringProvider")
    public void testFromString(String input, Department check, int facultiesCount) throws Exception {
        Department d = new DepartmentBuilder().fromString(input).build();
        assertEquals(d,check);
        assertEquals(d.getFacultiesCount(), facultiesCount);
    }

}