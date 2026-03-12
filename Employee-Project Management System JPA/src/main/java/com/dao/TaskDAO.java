package com.dao;

import com.model.Task;
import com.util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.util.List;

public class TaskDAO {

    public void save(Task task) {

        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            em.persist(task);
            tx.commit();
        } finally {
            em.close();
        }
    }

    public Task findById(Long id) {

        EntityManager em = JPAUtil.getEntityManager();
        Task task = em.find(Task.class, id);
        em.close();

        return task;
    }

    public List<Task> findAll() {

        EntityManager em = JPAUtil.getEntityManager();

        List<Task> list = em.createQuery(
                "SELECT t FROM Task t", Task.class
        ).getResultList();

        em.close();

        return list;
    }

    public void update(Task task) {

        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            em.merge(task);
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

            Task task = em.find(Task.class, id);

            if (task != null) {
                em.remove(task);
            }

            tx.commit();
        } finally {
            em.close();
        }
    }
}
