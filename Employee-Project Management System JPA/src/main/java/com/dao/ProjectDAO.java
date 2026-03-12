package com.dao;


import com.model.Project;
import com.util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.util.List;

public class ProjectDAO {

    public void save(Project project) {

        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            em.persist(project);
            tx.commit();
        } finally {
            em.close();
        }
    }

    public Project findById(Long id) {

        EntityManager em = JPAUtil.getEntityManager();
        Project project = em.find(Project.class, id);
        em.close();

        return project;
    }

    public List<Project> findAll() {

        EntityManager em = JPAUtil.getEntityManager();

        List<Project> list = em.createQuery(
                "SELECT p FROM Project p", Project.class
        ).getResultList();

        em.close();

        return list;
    }

    public void update(Project project) {

        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            em.merge(project);
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

            Project project = em.find(Project.class, id);

            if (project != null) {
                em.remove(project);
            }

            tx.commit();
        } finally {
            em.close();
        }
    }
}
