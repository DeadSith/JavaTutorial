package com.sith.spring_lab.models;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Entity
@Table(name = "java.departments")
@Transactional
public class Department {
    final static Pattern namePattern = Pattern.compile("[A-Za-z ,]{5,}");
    final static Pattern phonePattern = Pattern.compile("\\+\\d{10,15}");

    private LocalDate creationDate;
    private int id;
    private String name;
    private String phoneNumber;
    private Set<Faculty> faculties;

    @Column(name = "creation_date")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        if (creationDate.isAfter(LocalDate.now())) {
            this.creationDate = LocalDate.now();
        } else {
            this.creationDate = creationDate;
        }
    }

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        Matcher matcher = namePattern.matcher(name);
        if (!matcher.matches())
            throw new IllegalArgumentException("Wrong input");
        this.name = name;
    }

    @Column(name = "phone_number")
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        Matcher matcher = phonePattern.matcher(phoneNumber);
        if (!matcher.matches())
            throw new IllegalArgumentException("Wrong input");
        this.phoneNumber = phoneNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Department that = (Department) o;

        if (id != that.id) return false;
        if (creationDate != null ? !creationDate.equals(that.creationDate) : that.creationDate != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        return phoneNumber != null ? phoneNumber.equals(that.phoneNumber) : that.phoneNumber == null;
    }

    @Override
    public int hashCode() {
        int result = creationDate != null ? creationDate.hashCode() : 0;
        result = 31 * result + id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (phoneNumber != null ? phoneNumber.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    public Set<Faculty> getFaculties() {
        return faculties;
    }

    public void setFaculties(Set<Faculty> faculties) {
        this.faculties = faculties;
    }

    @Transient
    public int getFacultiesCount() {
        return faculties.size();
    }

    public void addFaculty(Faculty f) {
        f.setDepartment(this);
        faculties.add(f);
    }

    public void removeFaculty(Faculty f) {
        faculties.remove(f);
        f.setDepartment(null);
    }
}
