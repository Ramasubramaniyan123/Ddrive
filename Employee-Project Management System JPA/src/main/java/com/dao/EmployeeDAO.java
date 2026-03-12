package com.dao;


import com.model.Employee;
import com.util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.util.List;

public class EmployeeDAO {

    public void save(Employee employee) {

        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            em.persist(employee);
            tx.commit();
        } finally {
            em.close();
        }
    }

    public Employee findById(Long id) {

        EntityManager em = JPAUtil.getEntityManager();
        Employee employee = em.find(Employee.class, id);
        em.close();

        return employee;
    }

    public List<Employee> findAll() {

        EntityManager em = JPAUtil.getEntityManager();

        List<Employee> employees = em.createQuery(
                "SELECT e FROM Employee e", Employee.class
        ).getResultList();

        em.close();

        return employees;
    }

    public void update(Employee employee) {

        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            em.merge(employee);
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

            Employee emp = em.find(Employee.class, id);

            if (emp != null) {
                em.remove(emp);
            }

            tx.commit();
        } finally {
            em.close();
        }
    }
}