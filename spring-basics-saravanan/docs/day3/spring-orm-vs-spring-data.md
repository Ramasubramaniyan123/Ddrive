# Spring ORM vs Spring Data

While they are often confused, **Spring ORM** and **Spring Data** operate at different levels of the application stack. One is a foundational integration layer, while the other is a high-level productivity tool.

## The Core Difference

| Feature            | Spring ORM                                            | Spring Data (JPA)                                                      |
|:-------------------|:------------------------------------------------------|:-----------------------------------------------------------------------|
| **Level**          | **Low-Level Integration**                             | **High-Level Abstraction**                                             |
| **What you write** | You write the DAO classes and queries manually.       | You write Interfaces; implementation is generated at runtime.          |
| **Flexibility**    | Maximum control over the `EntityManager`.             | High convenience; follows "Convention over Configuration."             |
| **Dependency**     | Part of the core Spring Framework (`spring-orm.jar`). | A separate project (`spring-data-jpa.jar`) that depends on Spring ORM. |

---

## 1. Spring ORM (The "Plumbing")
**Goal:** To manage the lifecycle of database resources (Sessions, Connections) and integrate them with Spring's transaction management.

* **How it works:** It wraps the standard JPA `EntityManager` or Hibernate `SessionFactory` so they can be injected as Spring Beans.
* **The Workflow:** You must manually write a class (DAO), inject the entity manager, and write the specific methods to `persist`, `merge`, or `select` data.

## 2. Spring Data (The "Magic")
**Goal:** To eliminate the need to write data access code entirely.

* **How it works:** It sits *on top* of Spring ORM. It scans your code for specific interfaces (Repositories) and automatically generates the implementation classes in memory when the application starts.
* **The Workflow:** You define an interface extending `JpaRepository`. You declare a method like `findByEmail()`, and Spring Data automatically generates the SQL/JPQL for you.

---

## Code Comparison

### Approach A: Spring ORM (Manual Implementation)
*You have to write the class, inject the manager, and write the query.*

```java
@Repository
public class UserDaoImpl implements UserDao {

    @PersistenceContext
    private EntityManager em; // Injected by Spring ORM

    public User findByName(String name) {
        // You manually control the query execution
        TypedQuery<User> query = em.createQuery(
            "SELECT u FROM User u WHERE u.name = :name", User.class);
        query.setParameter("name", name);
        return query.getSingleResult();
    }
}
```

### Approach B: Spring Data (Automated)

*You define the interface, and the code above is generated automatically.*

```java
// You extend a Spring Data interface
public interface UserRepository extends JpaRepository<User, Long> {

    // Spring Data parses this name and creates the query logic automatically
    User findByName(String name);

}
```