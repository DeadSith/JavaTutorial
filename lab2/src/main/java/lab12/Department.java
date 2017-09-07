package lab12;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class Department implements Comparable<Department> {

    private List<Faculty> faculties;
    private String name;
    private Date creationDate;
    private String phoneNumber;

    public Department(String name, Date creationDate, String phoneNumber, List<Faculty> faculties) {
        this.faculties = faculties;
        this.name = name;
        this.creationDate = creationDate;
        this.phoneNumber = phoneNumber;
    }

    public Department(String name, Date creationDate, String phoneNumber) {
        this.faculties = new ArrayList<Faculty>();
        this.name = name;
        this.creationDate = creationDate;
        this.phoneNumber = phoneNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Faculty)) {
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

    public List<Faculty> getSortedFaculties() {
        Collections.sort(faculties);
        return faculties;
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

    public Date getCreationDate() {
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
        return String.format("This is %s department created on %1$te %1$tm %1$tY.", name, creationDate);
    }

    public int getTeachersCount() {
        return faculties
                .stream()
                .mapToInt(faculty -> faculty.getTeachersCount())
                .sum();
    }

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
}
