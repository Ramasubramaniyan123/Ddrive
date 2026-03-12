package com.util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JPAUtil {
    public static final EntityManagerFactory factory = Persistence.createEntityManagerFactory("employee-management");
    public static EntityManager getEntityManager(){
        return factory.createEntityManager();
    }
    public static void close(){
        factory.close();
    }
}
