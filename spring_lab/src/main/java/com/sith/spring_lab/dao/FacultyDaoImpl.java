package com.sith.spring_lab.dao;

import com.sith.spring_lab.models.Faculty;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.*;
import java.util.List;

@Repository("facultyDao")
public class FacultyDaoImpl implements FacultyDao{
    @Autowired
    SessionFactory sessionFactory;

    public void persist(Faculty f) {
        Session s = sessionFactory.getCurrentSession();
        s.persist(f);
    }

    public void update(Faculty f) {
        Session s = sessionFactory.getCurrentSession();
        s.update(f);
    }

    public void delete(Faculty f) {
        Session s = sessionFactory.getCurrentSession();
        s.delete(f);
    }

    public List<Faculty> getAll() {
        Session s = sessionFactory.getCurrentSession();
        CriteriaBuilder builder = s.getCriteriaBuilder();
        CriteriaQuery<Faculty> criteria = builder.createQuery(Faculty.class);
        Root<Faculty> root = criteria.from(Faculty.class);
        criteria.select(root);
        return s.createQuery(criteria).getResultList();
    }

    public Faculty getById(int id) {
        Session s = sessionFactory.getCurrentSession();
        return s.get(Faculty.class, id);
    }

    @Override
    public List<Faculty> getByName(String name) {
        Session s = sessionFactory.getCurrentSession();
        CriteriaBuilder builder = s.getCriteriaBuilder();
        CriteriaQuery<Faculty> criteria = builder.createQuery(Faculty.class);
        Root<Faculty> root = criteria.from(Faculty.class);
        Expression<String> literal = builder.literal("%"
                + name.toLowerCase() + "%");
        Predicate p = builder.like(builder.lower(root.get("name")),literal);
        criteria.where(p);
        criteria.select(root);
        return s.createQuery(criteria).getResultList();
    }
}
