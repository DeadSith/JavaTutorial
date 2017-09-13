package lab3;

import java.time.LocalDate;
import java.util.Set;
import java.util.TreeSet;

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

    public Faculty createFaculty() {
        if (subjects == null)
            subjects = new TreeSet<>();
        if (teachers == null)
            teachers = new TreeSet<>();
        return new Faculty(name, creationDate, department, teachers, subjects);
    }
}