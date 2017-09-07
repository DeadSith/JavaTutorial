package lab12;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Faculty implements Comparable<Faculty> {
    private String name;
    private Date creationDate;
    private List<String> teachers;
    private List<String> subjects;
    private Department department;

    public Faculty(String name, Date creationDate, Department department, List<String> teachers, List<String> subjects) {
        this.name = name;
        this.creationDate = creationDate;
        this.department = department;
        this.teachers = teachers;
        this.subjects = subjects;
    }

    public Faculty(String name, Date creationDate, Department department) {
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
        return String.format("This is %s faculty created on %1$te %1$tm %1$tY.", name, creationDate);
    }


    public List<String> getSubjects() {
        return subjects;
    }

    public List<String> getTeachers() {
        return teachers;
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
        return teachers.size();
    }
}

