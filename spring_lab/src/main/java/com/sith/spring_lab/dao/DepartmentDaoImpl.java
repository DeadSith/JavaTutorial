package com.sith.spring_lab.dao;

import com.sith.spring_lab.models.Department;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.*;
import java.util.List;

@Repository("departmentDao")
public class DepartmentDaoImpl implements DepartmentDao {
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
        Root<Department> root = criteria.from(Department.class);
        criteria.select(root);
        return s.createQuery(criteria).getResultList();
    }

    public Department getById(int id) {
        Session s = sessionFactory.openSession();
        return s.get(Department.class, id);
    }

    @Override
    public List<Department> getByName(String name) {
        Session s = sessionFactory.openSession();
        CriteriaBuilder builder = s.getCriteriaBuilder();
        CriteriaQuery<Department> criteria = builder.createQuery(Department.class);
        Root<Department> root = criteria.from(Department.class);
        Expression<String> literal = builder.literal("%"
                + name.toLowerCase() + "%");
        Predicate p = builder.like(builder.lower(root.get("name")),literal);
        criteria.where(p);
        criteria.select(root);
        return s.createQuery(criteria).getResultList();
    }
}
