package com.sith.spring_lab.dao;

import com.sith.spring_lab.models.Department;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

public class DepartmentDao {
    @Autowired
    SessionFactory sessionFactory;

    public void persist(Department d) {
        Session s = sessionFactory.openSession();
        s.persist(d);
    }

    public void update(Department d) {
        Session s = sessionFactory.openSession();
        s.update(d);
    }

    public void delete(Department d) {
        Session s = sessionFactory.openSession();
        s.delete(d);
    }

    public List<Department> getAll() {
        Session s = sessionFactory.openSession();
        CriteriaBuilder builder = s.getCriteriaBuilder();
        CriteriaQuery<Department> criteria = builder.createQuery(Department.class);
        return s.createQuery(criteria).getResultList();
    }

    public Department getById(int id) {
        Session s = sessionFactory.openSession();
        return s.get(Department.class, id);
    }
}
