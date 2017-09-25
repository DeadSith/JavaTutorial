package lab4;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lab4.serializers.LocalDateSerializer;

import javax.xml.bind.annotation.XmlRootElement;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;

@JsonDeserialize(builder = DepartmentBuilder.class)
@XmlRootElement
public class Department implements Comparable<Department> {

    private TreeSet<Faculty> faculties;
    private String name;
    private LocalDate creationDate;
    private String phoneNumber;

    Department(String name, LocalDate creationDate, String phoneNumber, TreeSet<Faculty> faculties) {
        this.faculties = faculties;
        for (Faculty f : faculties) {
            f.setDepartment(this);
        }
        this.name = name;
        this.creationDate = creationDate;
        this.phoneNumber = phoneNumber;
    }

    /**
     * only name and creation date are compared
     */
    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Department)) {
            return false;
        }
        Department d = (Department) o;
        return this.name.equals(d.name);
    }

    @Override
    public int hashCode() {
        return this.name.hashCode() ^ this.creationDate.hashCode();
    }

    /**
     * @return sorted immutable list of faculties
     */

    @JsonProperty("faculties")
    public Set<Faculty> getSortedFaculties() {
        return Collections.unmodifiableSet(faculties);
    }

    public boolean removeFaculty(Faculty faculty) {
        return faculties.remove(faculty);
    }

    public boolean addFaculty(Faculty faculty) {
        faculty.setDepartment(this);
        return faculties.add(faculty);
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonSerialize(using = LocalDateSerializer.class)
    public LocalDate getCreationDate() {
        return this.creationDate;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return String.format("This is %s department created on %s.", name, creationDate.toString());
    }

    /**
     * @return number of teachers on all faculties
     */
    @JsonIgnore
    public int getTeachersCount() {
        return faculties
                .stream()
                .mapToInt(Faculty::getTeachersCount)
                .sum();
    }

    /**
     * @return number of subjects on all faculties
     */
    @JsonIgnore
    public int getSubjectsCount() {
        return faculties
                .stream()
                .mapToInt(Faculty::getSubjectsCount)
                .sum();
    }

    @JsonIgnore
    public int getFacultiesCount() {
        return faculties.size();
    }

    @Override
    public int compareTo(Department department) {
        return name.compareTo(department.name);
    }
}
