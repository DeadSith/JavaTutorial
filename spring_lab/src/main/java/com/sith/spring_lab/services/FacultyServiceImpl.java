package com.sith.spring_lab.services;

import com.sith.spring_lab.dao.FacultyDao;
import com.sith.spring_lab.models.Faculty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("facultyService")
@Transactional
public class FacultyServiceImpl implements FacultyService {
    @Autowired
    FacultyDao dao;

    @Override
    public void save(Faculty f) {
        dao.persist(f);
    }

    @Override
    public void update(Faculty f) {
        dao.update(f);
    }

    @Override
    public void deleteById(int id) {
        Faculty entity = dao.getById(id);
        if (entity != null)
            dao.delete(entity);
    }

    @Override
    public Faculty findById(int id) {
        return dao.getById(id);

    }

    @Override
    public List<Faculty> getAll() {
        return dao.getAll();
    }

    @Override
    public List<Faculty> findByName(String name) {
        return dao.getByName(name);
    }
}
