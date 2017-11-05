package com.sith.spring_lab.dao;

import com.sith.spring_lab.models.Department;

import java.util.List;

public interface DepartmentDao {
    void persist(Department d);
    void update(Department d);
    void delete(Department d);
    List<Department> getAll();
    Department getById(int id);
    List<Department> getByName(String name);
}
