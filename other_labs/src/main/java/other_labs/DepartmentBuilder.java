package other_labs;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import other_labs.serializers.LocalDateDeserializer;

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
    private int id;

    public DepartmentBuilder setName(String name) {
        this.name = name;
        return this;
    }

    @JsonDeserialize(using = LocalDateDeserializer.class)
    public DepartmentBuilder setCreationDate(LocalDate creationDate) {
        if (creationDate.isAfter(LocalDate.now()))
            creationDate = LocalDate.now();
        this.creationDate = creationDate;
        return this;
    }

    public DepartmentBuilder setId(int id) {
        this.id = id;
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
        return new Department(name, creationDate, phoneNumber, faculties, id);
    }

    /**
     * @param input string in form Name: _;Created: _;Number _;(Faculties: _) Faculties should be divided with /
     * @return changes current builder according to input and returns builder
     */
    public DepartmentBuilder fromString(String input) {
        this.name = RegexHelper.getRegexGroup(input, RegexHelper.NAME_REGEX);
        this.setCreationDate(LocalDate.parse(RegexHelper.getRegexGroup(input, RegexHelper.CREATION_DATE_REGEX)));
        this.phoneNumber = RegexHelper.getRegexGroup(input, PHONE_NUMBER_REGEX);
        this.faculties = new TreeSet<>();
        try {
            String[] facultiesArray = RegexHelper.getRegexGroup(input, FACULTIES_REGEX).split("/");
            for (String faculty : facultiesArray) {
                if (!faculty.isEmpty())
                    faculties.add(new FacultyBuilder().fromString(faculty).build());
            }
        } catch (Exception e) {

        }
        return this;
    }


}
