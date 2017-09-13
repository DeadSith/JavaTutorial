package lab3;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Set;

public class Faculty implements Comparable<Faculty> {
    private String name;
    private LocalDate creationDate;
    private Set<String> teachers;
    private Set<String> subjects;
    private Department department;

    Faculty(String name, LocalDate creationDate, Department department, Set<String> teachers, Set<String> subjects) {
        this.name = name;
        this.creationDate = creationDate;
        this.department = department;
        this.teachers = teachers;
        this.subjects = subjects;
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

    void setDepartment(Department department) {
        this.department = department;
    }

    /**
     * @return immutable list of subjects
     */
    public Set<String> getSubjects() {
        return Collections.unmodifiableSet(subjects);
    }

    /**
     * @return immutable list of teachers
     */
    public Set<String> getTeachers() {
        return Collections.unmodifiableSet(teachers);
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

