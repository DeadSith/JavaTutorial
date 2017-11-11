package com.sith.spring_lab.services;

import com.sith.spring_lab.dao.DepartmentDao;
import com.sith.spring_lab.dao.FacultyDao;
import com.sith.spring_lab.models.Department;
import com.sith.spring_lab.models.Faculty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service("facultyService")
@Transactional
public class FacultyServiceImpl implements FacultyService {
    @Autowired
    FacultyDao dao;

    @Autowired
    DepartmentDao departmentDao;

    @Override
    public void save(Faculty f, Department d) {
        Faculty f1 = new Faculty();
        f1.setTeachers(this.convertStringForDb(f.getTeachers()));
        f1.setSubjects(this.convertStringForDb(f.getSubjects()));
        f1.setName(f.getName());
        d.addFaculty(f);
        departmentDao.merge(d);
    }

    @Override
    public void update(Faculty f) {
        dao.update(f);
    }

    @Override
    public void deleteById(int id) {
        Faculty entity = dao.getById(id);
        if (entity != null) {
            Department d = entity.getDepartment();
            d.removeFaculty(entity);
            departmentDao.merge(d);
        }
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

    @Override
    public String convertStringForDb(String input) {
        Pattern pattern = Pattern.compile("[A-Za-z \n\r]*");
        Matcher matcher = pattern.matcher(input);
        if (!matcher.matches())
            throw new IllegalArgumentException("Wrong input");
        String[] values = input.split("\n");
        StringBuilder sb = new StringBuilder();
        for (String value : values) {
            value = value.trim();
            value = value.replaceAll("(\\r|\\n)", "");
            if (!value.isEmpty()) {
                sb.append(value);
                sb.append(',');
            }
        }
        return sb.toString();
    }
}
