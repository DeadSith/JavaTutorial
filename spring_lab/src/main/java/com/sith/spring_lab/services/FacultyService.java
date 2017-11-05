package com.sith.spring_lab.services;

import com.sith.spring_lab.models.Faculty;

import java.util.List;

public interface FacultyService {
    void save(Faculty f);
    void update(Faculty f);
    void deleteById(int id);
    Faculty findById(int id);
    List<Faculty> getAll();
    List<Faculty> findByName(String name);
}
