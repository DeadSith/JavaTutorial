package lab3;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DepartmentBuilder {
    private String name;
    private LocalDate creationDate;
    private String phoneNumber;
    private Set<Faculty> faculties;

    public DepartmentBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public DepartmentBuilder setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    public DepartmentBuilder setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public DepartmentBuilder setFaculties(Set<Faculty> faculties) {
        this.faculties = faculties;
        return this;
    }

    public Department build() {
        if (faculties == null)
            faculties = new TreeSet<>();
        return new Department(name, creationDate, phoneNumber, faculties);
    }

    /**
     * @param input string in form Name: _;Created: _;Number _;(Faculties: _) Faculties should be divided with /
     * @return changes current builder according to input and returns builder
     */
    public DepartmentBuilder fromString(String input){
        Pattern p = Pattern.compile("Name: *([a-zA-z, ]+);Created: *(\\d{4}-\\d{2}-\\d{2});Number: *(\\+\\d{10,15});(?:Faculties: (.+))?");
        Matcher match = p.matcher(input);
        assert (match.find());
        this.name = match.group(1);
        this.creationDate = LocalDate.parse(match.group(2));
        this.phoneNumber = match.group(3);
        this.faculties = new TreeSet<>();
        try {

            String[] facultiesArray;
            facultiesArray = match.group(4).split("/");
            for (String faculty: facultiesArray){
                faculties.add(new FacultyBuilder().fromString(faculty).build());
            }
        }
        catch (Exception e){

        }
        return this;
    }
}