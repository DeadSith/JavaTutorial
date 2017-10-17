package models;

import java.time.LocalDate;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DepartmentBuilder {
    final static Pattern namePattern = Pattern.compile("[A-Za-z ]{5,}");
    final static Pattern phonePattern = Pattern.compile("\\+\\d{10,15}");
    private String name;
    private LocalDate creationDate;
    private String phoneNumber;
    private TreeSet<Faculty> faculties;
    private int id;

    public DepartmentBuilder setName(String name) {
        Matcher matcher = namePattern.matcher(name);
        if (!matcher.matches())
            throw new IllegalArgumentException();
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
        Matcher matcher = phonePattern.matcher(phoneNumber);
        if (!matcher.matches())
            throw new IllegalArgumentException();
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
