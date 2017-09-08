package lab12;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Department implements Comparable<Department> {

    private List<Faculty> faculties;
    private String name;
    private LocalDate creationDate;
    private String phoneNumber;

    public Department(String name, LocalDate creationDate, String phoneNumber, List<Faculty> faculties) {
        this.faculties = faculties;
        this.name = name;
        this.creationDate = creationDate;
        this.phoneNumber = phoneNumber;
    }

    public Department(String name, LocalDate creationDate, String phoneNumber) {
        this.faculties = new ArrayList<>();
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
        return this.name.equals(d.name) &&
                this.creationDate.equals(d.creationDate);
    }

    @Override
    public int hashCode() {
        return this.name.hashCode() ^ this.creationDate.hashCode();
    }

    /**
     * @return sorted immutable list of faculties
     */
    public List<Faculty> getSortedFaculties() {
        Collections.sort(faculties);
        return Collections.unmodifiableList(faculties);
    }

    public boolean removeFaculty(Faculty faculty) {
        return this.faculties.remove(faculty);
    }

    public boolean addFaculty(Faculty faculty) {
        if (!faculties.contains(faculty)) {
            faculties.add(faculty);
            return true;
        }
        return false;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

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
    public int getTeachersCount() {
        return faculties
                .stream()
                .mapToInt(faculty -> faculty.getTeachersCount())
                .sum();
    }

    /**
     * @return number of subjects on all faculties
     */
    public int getSubjectsCount() {
        return faculties
                .stream()
                .mapToInt(faculty -> faculty.getSubjectsCount())
                .sum();
    }

    public int getFacultiesCount() {
        return faculties.size();
    }

    @Override
    public int compareTo(Department department) {
        return name.compareTo(department.name);
    }

    /**
     * For testing only
     *
     * @return list of faculties
     */
    List<Faculty> getFaculties() {
        return faculties;
    }
}
