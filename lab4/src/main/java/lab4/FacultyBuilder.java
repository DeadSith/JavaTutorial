package lab4;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lab4.serializers.LocalDateDeserializer;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.TreeSet;

@JsonPOJOBuilder(buildMethodName = "build", withPrefix = "set")
public class FacultyBuilder {
    private final static String TEACHERS_REGEX = "Teachers: *(.*?);";
    private final static String SUBJECTS_REGEX = "Subjects: *(.*?);";
    private String name;
    private LocalDate creationDate;
    private Department department;
    private TreeSet<String> teachers;
    private TreeSet<String> subjects;
    private int id;

    public FacultyBuilder setName(String name) {
        this.name = name;
        return this;
    }


    @JsonDeserialize(using = LocalDateDeserializer.class)
    public FacultyBuilder setCreationDate(LocalDate creationDate) {
        if (creationDate.isAfter(LocalDate.now()))
            creationDate = LocalDate.now();
        this.creationDate = creationDate;
        return this;
    }

    public FacultyBuilder setDepartment(Department department) {
        this.department = department;
        return this;
    }

    public FacultyBuilder setId(int id) {
        this.id = id;
        return this;
    }

    public FacultyBuilder setTeachers(TreeSet<String> teachers) {
        this.teachers = teachers;
        return this;
    }

    public FacultyBuilder setSubjects(TreeSet<String> subjects) {
        this.subjects = subjects;
        return this;
    }

    public Faculty build() {
        if (subjects == null)
            subjects = new TreeSet<>();
        if (teachers == null)
            teachers = new TreeSet<>();
        return new Faculty(name, creationDate, department, teachers, subjects, id);
    }

    /**
     * @param input String in form Name _;Created _;(Teachers: _;)(Subjects _). Teachers and subjects should be separated with ,
     * @return changes current builder according to input and returns builder
     */
    public FacultyBuilder fromString(String input) {
        this.name = RegexHelper.getRegexGroup(input, RegexHelper.NAME_REGEX);
        this.setCreationDate(LocalDate.parse(RegexHelper.getRegexGroup(input, RegexHelper.CREATION_DATE_REGEX)));
        try {
            List<String> teachers = Arrays.asList(RegexHelper.getRegexGroup(input, TEACHERS_REGEX).split(","));
            teachers.removeAll(Arrays.asList("", null));
            this.teachers = new TreeSet<>(teachers);
        }
        catch (Exception e){
            this.teachers = new TreeSet<>();
        }
        try {
            List<String> subjects = Arrays.asList(RegexHelper.getRegexGroup(input, SUBJECTS_REGEX).split(","));
            subjects.removeAll(Arrays.asList("", null));
            this.subjects = new TreeSet<>(subjects);
        }
        catch (Exception e){
            this.subjects = new TreeSet<>();
        }
        return this;
    }
}
