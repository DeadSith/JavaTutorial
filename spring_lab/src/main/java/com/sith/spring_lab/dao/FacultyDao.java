package com.sith.spring_lab.dao;

import com.sith.spring_lab.models.Faculty;

import java.util.List;

public interface FacultyDao {
    void persist(Faculty f);
    void update(Faculty f);
    void delete(Faculty f);
    List<Faculty> getAll();
    Faculty getById(int id);
    List<Faculty> getByName(String name);
}
