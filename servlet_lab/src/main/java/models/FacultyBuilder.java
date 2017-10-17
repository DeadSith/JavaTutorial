package models;

import java.time.LocalDate;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FacultyBuilder {
    final static Pattern namePattern = Pattern.compile("[A-Za-z ]{5,}");
    private String name;
    private LocalDate creationDate;
    private Department department;
    private TreeSet<String> teachers;
    private TreeSet<String> subjects;
    private int id;

    public FacultyBuilder setName(String name) {
        Matcher matcher = namePattern.matcher(name);
        if (!matcher.matches())
            throw new IllegalArgumentException();
        this.name = name;
        return this;
    }


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
}
