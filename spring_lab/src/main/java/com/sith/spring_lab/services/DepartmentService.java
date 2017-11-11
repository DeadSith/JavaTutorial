package com.sith.spring_lab.services;

import com.sith.spring_lab.models.Department;

import java.io.IOException;
import java.io.Writer;
import java.util.Collection;
import java.util.List;

public interface DepartmentService {
    void save(Department d);
    void update(Department d);
    void deleteById(int id);
    Department findById(int id);
    List<Department> getAll();
    List<Department> findByName(String name);

    void merge(Department d);

    void serializeCollection(Collection<Department> objects, Writer output) throws IOException;
}
