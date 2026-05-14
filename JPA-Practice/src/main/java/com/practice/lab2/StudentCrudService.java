package com.practice.lab2;

import com.practice.lab1.Student;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

import java.util.List;

public class StudentCrudService {
    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("labPU");


    public void shutdown() {
        if (emf != null && emf.isOpen()) {
            emf.close();
            System.out.println("EntityManagerFactory Closed Successfully");
        }
    }

    public Student createStudent(String first, String last, String email) {
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            Student student = new Student(first, last, email);
            entityManager.persist(student);
            transaction.commit();
            System.out.println("Student created Successfully");
            return student;
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        } finally {
            entityManager.close();
        }
    }

    public Student findStudentById(Long id) {
        EntityManager entityManager = emf.createEntityManager();
        try {
            return entityManager.find(Student.class, id);
        } finally {
            entityManager.close();
        }
    }

    public void updateStudentEmail(Long id, String newEmail) {
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            Student student = entityManager.find(Student.class, id);
            if (student != null) {
                student.setEmail(newEmail);
                System.out.println("Student Email updated Successfully");
            } else {
                System.out.println("Student not found with the id : " + id);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        } finally {
            entityManager.close();
        }
    }

    public void deleteStudent(Long id) {
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            Student student = entityManager.find(Student.class, id);
            if (student != null) {
                entityManager.remove(student);
                System.out.println("Student removed successfully");
            } else {
                System.out.println("Student not found with the id: " + id);
            }
            transaction.commit();

        } catch ( Exception exception) {
            if(transaction.isActive()){
                transaction.rollback();
            }
        } finally {
            entityManager.close();
        }
    }

    public List<Student> getAllStudent() {
        EntityManager entityManager = emf.createEntityManager();
        try {
            return entityManager.createQuery("select s from Student s", Student.class)
                    .getResultList();
        } finally {
            entityManager.close();
        }
    }

    public List<Student> findByLastName(String lastname){
        EntityManager entityManager = emf.createEntityManager();
        try{
            return entityManager.createQuery("SELECT s FROM Student s WHERE s.lastName = :ln", Student.class)
                    .setParameter("ln", lastname)
                    .getResultList();
        }
        finally {
            entityManager.close();
        }
    }

    public long countStudents(){
        EntityManager entityManager = emf.createEntityManager();
        try{
            return entityManager.createQuery(" select count(s) from Student s", Long.class)
                    .getSingleResult();
        }
        finally {
            entityManager.close();
        }
    }
}
