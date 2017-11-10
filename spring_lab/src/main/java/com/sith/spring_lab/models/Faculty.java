package com.sith.spring_lab.models;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Entity
@Table(name = "java.faculties")
@Transactional
public class Faculty {
    final static Pattern namePattern = Pattern.compile("[A-Za-z ,]{5,}");

    private int id;
    private String name;
    private LocalDate creationDate;
    private String teachers;
    private String subjects;
    private Department department;

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
            throw new IllegalArgumentException();
        this.name = name;
    }

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

    @Column(name = "teachers")
    public String getTeachers() {
        return teachers;
    }

    public void setTeachers(String teachers) {
        this.teachers = teachers;
    }

    @Column(name = "subjects")
    public String getSubjects() {
        return subjects;
    }

    public void setSubjects(String subjects) {
        this.subjects = subjects;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Faculty that = (Faculty) o;

        if (id != that.id) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (creationDate != null ? !creationDate.equals(that.creationDate) : that.creationDate != null) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (creationDate != null ? creationDate.hashCode() : 0);
        return result;
    }

    @ManyToOne
    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    @Transient
    public String[] getSplitTeachers() {
        if (!teachers.isEmpty())
            return teachers.split(",");
        return new String[0];
    }

    @Transient
    public String[] getSplitSubjects() {
        if (!subjects.isEmpty())
            return subjects.split(",");
        return new String[0];
    }
}
