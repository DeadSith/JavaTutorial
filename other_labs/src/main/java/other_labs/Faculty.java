package other_labs;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import other_labs.serializers.LocalDateSerializer;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;

@JsonDeserialize(builder = FacultyBuilder.class)
public class Faculty implements Comparable<Faculty> {
    private String name;
    private LocalDate creationDate;
    private Set<String> teachers;
    private Set<String> subjects;
    private Department department;
    private int id;

    Faculty(String name, LocalDate creationDate, Department department, TreeSet<String> teachers, TreeSet<String> subjects, int id) {
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
        return teachers.add(name);
    }

    public boolean addSubject(String name) {
        return subjects.add(name);
    }

    public boolean removeTeacher(String name) {
        return teachers.remove(name);
    }

    public boolean removeSubject(String name) {
        return subjects.remove(name);
    }

    @JsonIgnore
    public int getTeachersCount() {
        return teachers.size();
    }

    @JsonIgnore
    public int getSubjectsCount() {
        return subjects.size();
    }

    @JsonIgnore
    public Department getDepartment() {
        return department;
    }

    public String getName() {
        return name;
    }

    @JsonIgnore
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @JsonSerialize(using = LocalDateSerializer.class)
    public LocalDate getCreationDate() {
        return creationDate;
    }
}

