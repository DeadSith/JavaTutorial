package models;

import java.time.LocalDate;
import java.util.TreeSet;

public class DepartmentBuilder {
    private String name;
    private LocalDate creationDate;
    private String phoneNumber;
    private TreeSet<Faculty> faculties;
    private int id;

    public DepartmentBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public DepartmentBuilder setCreationDate(LocalDate creationDate) {
        if (creationDate.isAfter(LocalDate.now()))
            creationDate = LocalDate.now();
        this.creationDate = creationDate;
        return this;
    }

    public DepartmentBuilder setId(int id) {
        this.id = id;
        return this;
    }

    public DepartmentBuilder setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public DepartmentBuilder setFaculties(TreeSet<Faculty> faculties) {
        this.faculties = faculties;
        return this;
    }

    public Department build() {
        if (faculties == null)
            faculties = new TreeSet<>();
        return new Department(name, creationDate, phoneNumber, faculties, id);
    }
}
