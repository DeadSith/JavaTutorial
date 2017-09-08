package lab12;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Faculty implements Comparable<Faculty> {
    private String name;
    private LocalDate creationDate;
    private List<String> teachers;
    private List<String> subjects;
    private Department department;

    public Faculty(String name, LocalDate creationDate, Department department, List<String> teachers, List<String> subjects) {
        this.name = name;
        this.creationDate = creationDate;
        this.department = department;
        this.teachers = teachers;
        this.subjects = subjects;
    }

    public Faculty(String name, LocalDate creationDate, Department department) {
        this.name = name;
        this.department = department;
        this.creationDate = creationDate;
        this.subjects = new ArrayList<>();
        this.teachers = new ArrayList<>();
    }

    @Override
    public int compareTo(Faculty faculty) {
        return this.name.compareTo(faculty.name);
    }

    /**
     * Only name and department are compared
     */
    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Faculty)) {
            return false;
        }
        Faculty f = (Faculty) o;
        return this.name.equals(f.name) &&
                this.department.equals(f.department);
    }

    @Override
    public int hashCode() {
        return this.name.hashCode() ^ this.department.hashCode();
    }

    @Override
    public String toString() {
        return String.format("This is %s faculty created on %s.", name, creationDate.toString());
    }


    /**
     * @return immutable list of subjects
     */
    public List<String> getSubjects() {
        return Collections.unmodifiableList(subjects);
    }

    /**
     * @return immutable list of teachers
     */
    public List<String> getTeachers() {
        return Collections.unmodifiableList(teachers);
    }

    public boolean addTeacher(String name) {
        if (!teachers.contains(name)) {
            teachers.add(name);
            return true;
        }
        return false;
    }

    public boolean addSubject(String name) {
        if (!subjects.contains(name)) {
            subjects.add(name);
            return true;
        }
        return false;
    }

    public boolean removeTeacher(String name) {
        return teachers.remove(name);
    }

    public boolean removeSubject(String name) {
        return subjects.remove(name);
    }

    public int getTeachersCount() {
        return teachers.size();
    }

    public int getSubjectsCount() {
        return subjects.size();
    }
}

