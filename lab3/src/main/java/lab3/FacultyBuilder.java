package lab3;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.*;

public class FacultyBuilder {
    private String name;
    private LocalDate creationDate;
    private Department department;
    private Set<String> teachers;
    private Set<String> subjects;

    public FacultyBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public FacultyBuilder setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    public FacultyBuilder setDepartment(Department department) {
        this.department = department;
        return this;
    }

    public FacultyBuilder setTeachers(Set<String> teachers) {
        this.teachers = teachers;
        return this;
    }

    public FacultyBuilder setSubjects(Set<String> subjects) {
        this.subjects = subjects;
        return this;
    }

    public Faculty build() {
        if (subjects == null)
            subjects = new TreeSet<>();
        if (teachers == null)
            teachers = new TreeSet<>();
        return new Faculty(name, creationDate, department, teachers, subjects);
    }

    /**
     * @param input String in form Name _;Created _;(Teachers: _;)(Subjects _). Teachers and subjects should be separated with ,
     * @return changes current builder according to input and returns builder
     */
    public FacultyBuilder fromString(String input) {
        Pattern p = Pattern.compile("Name: *([a-zA-z, ]+);Created: *(\\d{4}-\\d{2}-\\d{2});(?:Teachers: *(?<teachers>.*);)?(?:Subjects: *(?<subjects>.*))?");
        Matcher match = p.matcher(input);
        assert (match.find());
        this.name = match.group(1);
        this.creationDate = LocalDate.parse(match.group(2));
        try {
            this.teachers = new TreeSet<>(Arrays.asList(match.group("teachers").split(",")));
        }
        catch (Exception e){
            this.teachers = new TreeSet<>();
        }
        try {
            this.subjects = new TreeSet<>(Arrays.asList(match.group("subjects").split(",")));
        }
        catch (Exception e){
            this.subjects = new TreeSet<>();
        }
        return this;
    }
}