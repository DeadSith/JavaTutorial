package lab4;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lab4.serializers.LocalDateDeserializer;

import java.time.LocalDate;
import java.util.TreeSet;

@JsonPOJOBuilder(buildMethodName = "build", withPrefix = "set")
public class DepartmentBuilder {
    private final static String PHONE_NUMBER_REGEX = "Number: *(\\+\\d{10,15});";
    private final static String FACULTIES_REGEX = "Faculties: (.+)\\/";

    private String name;
    private LocalDate creationDate;
    private String phoneNumber;
    private TreeSet<Faculty> faculties;

    public DepartmentBuilder setName(String name) {
        this.name = name;
        return this;
    }

    @JsonDeserialize(using = LocalDateDeserializer.class)
    public DepartmentBuilder setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    public DepartmentBuilder setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public DepartmentBuilder setFaculties(TreeSet<Faculty> faculties) {
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
        this.name = RegexHelper.getRegexGroup(input, RegexHelper.NAME_REGEX);
        this.creationDate = LocalDate.parse(RegexHelper.getRegexGroup(input, RegexHelper.CREATION_DATE_REGEX));
        this.phoneNumber = RegexHelper.getRegexGroup(input, PHONE_NUMBER_REGEX);
        this.faculties = new TreeSet<>();
        try {
            String[] facultiesArray;
            facultiesArray = RegexHelper.getRegexGroup(input, FACULTIES_REGEX).split("/");
            for (String faculty: facultiesArray){
                faculties.add(new FacultyBuilder().fromString(faculty).build());
            }
        }
        catch (Exception e){

        }
        return this;
    }


}
