package lab3;

import java.time.LocalDate;
import java.util.Set;
import java.util.TreeSet;

public class DepartmentBuilder {
    private String name;
    private LocalDate creationDate;
    private String phoneNumber;
    private Set<Faculty> faculties;

    public DepartmentBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public DepartmentBuilder setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    public DepartmentBuilder setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public DepartmentBuilder setFaculties(Set<Faculty> faculties) {
        this.faculties = faculties;
        return this;
    }

    public Department createDepartment() {
        if (faculties == null)
            faculties = new TreeSet<>();
        return new Department(name, creationDate, phoneNumber, faculties);
    }
}