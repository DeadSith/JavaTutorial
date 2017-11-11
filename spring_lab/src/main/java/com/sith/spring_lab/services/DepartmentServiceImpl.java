package com.sith.spring_lab.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.sith.spring_lab.dao.DepartmentDao;
import com.sith.spring_lab.models.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.Writer;
import java.util.Collection;
import java.util.List;

@Service("departmentService")
@Transactional
public class DepartmentServiceImpl implements DepartmentService{

    @Autowired
    DepartmentDao dao;

    public void save(Department d){
        dao.persist(d);
    }

    @Override
    public void update(Department d) {
        dao.update(d);
    }

    @Override
    public void deleteById(int id) {
        Department entity = dao.getById(id);
        if(entity!=null)
            dao.delete(entity);
    }


    @Override
    public Department findById(int id) {
        return dao.getById(id);
    }

    @Override
    public List<Department> getAll() {
        return dao.getAll();
    }

    @Override
    public List<Department> findByName(String name) {
        return dao.getByName(name);
    }

    @Override
    public void merge(Department d) {
        dao.merge(d);
    }

    @Override
    public void serializeCollection(Collection<Department> objects, Writer output) throws IOException {
        ObjectMapper mapper = new ObjectMapper()
                .registerModule(new JavaTimeModule());
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        mapper.writeValue(output, objects);
        output.flush();
    }
}
