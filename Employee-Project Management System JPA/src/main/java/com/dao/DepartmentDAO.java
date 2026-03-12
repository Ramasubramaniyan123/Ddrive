package com.dao;

import com.model.Department;
import com.util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.util.List;

public class DepartmentDAO {

    public void save(Department department) {

        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            em.persist(department);
            tx.commit();
        } finally {
            em.close();
        }
    }

    public Department findById(Long id) {

        EntityManager em = JPAUtil.getEntityManager();
        Department department = em.find(Department.class, id);
        em.close();

        return department;
    }

    public List<Department> findAll() {

        EntityManager em = JPAUtil.getEntityManager();

        List<Department> list = em.createQuery(
                "SELECT d FROM Department d", Department.class
        ).getResultList();

        em.close();

        return list;
    }

    public void update(Department department) {

        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            em.merge(department);
            tx.commit();
        } finally {
            em.close();
        }
    }

    public void delete(Long id) {

        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            Department dept = em.find(Department.class, id);

            if (dept != null) {
                em.remove(dept);
            }

            tx.commit();
        } finally {
            em.close();
        }
    }
}
