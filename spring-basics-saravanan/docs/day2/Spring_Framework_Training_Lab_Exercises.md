# Spring Framework Training Lab Exercises


## Table of Contents

1. [Lab Exercise 1: Spring XML-Based Dependency Injection - Constructor Injection](#lab-exercise-1)
2. [Lab Exercise 2: Spring XML-Based Dependency Injection - Setter Injection](#lab-exercise-2)
3. [Lab Exercise 3: Spring Java-Based Configuration](#lab-exercise-3)
4. [Lab Exercise 4: Spring Annotation-Based Configuration](#lab-exercise-4)
5. [Lab Exercise 5: Spring Bean Scopes - Singleton](#lab-exercise-5)
6. [Lab Exercise 6: Spring Bean Scopes - Prototype](#lab-exercise-6)
7. [Lab Exercise 7: Spring Bean Scopes - Comparison](#lab-exercise-7)
8. [Lab Exercise 8: Spring JDBC with MySQL](#lab-exercise-8)
9. [Lab Exercise 9: Spring ORM with Hibernate](#lab-exercise-9)
10. [Lab Exercise 10: Spring Data JPA](#lab-exercise-10)
11. [Lab Exercise 11: Spring Bean Lifecycle - InitializingBean and DisposableBean](#lab-exercise-11)
12. [Lab Exercise 12: Spring Bean Lifecycle - Custom Init and Destroy Methods](#lab-exercise-12)
13. [Lab Exercise 13: Spring Bean Lifecycle - Complete Lifecycle](#lab-exercise-13)
14. [Lab Exercise 14: Spring AOP for Logging (spring-aspects)](#lab-exercise-14)
15. [Lab Exercise 15: Spring AOP with AspectJ](#lab-exercise-15)

---

## Maven Project Setup

### pom.xml

```xml
<project xmlns="http://maven.apache.org/POM/4.0.0">
    <modelVersion>4.0.0</modelVersion>
    <groupId>com.xcelevate</groupId>
    <artifactId>spring-training-labs</artifactId>
    <version>1.0</version>
    <properties>
        <maven.compiler.source>17</maven.compiler.source>
        <maven.compiler.target>17</maven.compiler.target>
        <spring.version>7.0.3</spring.version>
    </properties>
    <dependencies>
        <!-- Spring Core -->
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-context</artifactId>
            <version>${spring.version}</version>
        </dependency>

        <!-- Spring JDBC -->
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-jdbc</artifactId>
            <version>${spring.version}</version>
        </dependency>

        <!-- Spring ORM -->
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-orm</artifactId>
            <version>${spring.version}</version>
        </dependency>

        <!-- Spring AOP -->
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-aop</artifactId>
            <version>${spring.version}</version>
        </dependency>

        <!-- Spring Aspects (for Lab 14) -->
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-aspects</artifactId>
            <version>${spring.version}</version>
        </dependency>

        <!-- AspectJ (for Lab 15) -->
        <dependency>
            <groupId>org.aspectj</groupId>
            <artifactId>aspectjweaver</artifactId>
            <version>1.9.22</version>
        </dependency>
        <dependency>
            <groupId>org.aspectj</groupId>
            <artifactId>aspectjrt</artifactId>
            <version>1.9.22</version>
        </dependency>

        <!-- MySQL Connector -->
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-j</artifactId>
            <version>9.1.0</version>
        </dependency>

        <!-- HikariCP -->
        <dependency>
            <groupId>com.zaxxer</groupId>
            <artifactId>HikariCP</artifactId>
            <version>6.2.1</version>
        </dependency>

        <!-- Hibernate -->
        <dependency>
            <groupId>org.hibernate.orm</groupId>
            <artifactId>hibernate-core</artifactId>
            <version>6.6.4.Final</version>
        </dependency>

        <!-- JPA API -->
        <dependency>
            <groupId>jakarta.persistence</groupId>
            <artifactId>jakarta.persistence-api</artifactId>
            <version>3.2.0</version>
        </dependency>

        <!-- Spring Data JPA -->
        <dependency>
            <groupId>org.springframework.data</groupId>
            <artifactId>spring-data-jpa</artifactId>
            <version>3.4.1</version>
        </dependency>

        <!-- Jakarta Annotation API -->
        <dependency>
            <groupId>jakarta.annotation</groupId>
            <artifactId>jakarta.annotation-api</artifactId>
            <version>3.0.0</version>
        </dependency>
    </dependencies>
</project>
```

---

## Lab Exercise 1

### Spring XML-Based Dependency Injection - Constructor Injection

**Objective:** Learn XML configuration with constructor-based dependency injection.

**Step 1:** Create Maven project `spring-training-labs` with the pom.xml shown above.

**Step 2:** Create `src/main/java/com/xcelevate/ex1/EmailService.java`:

```java
package com.xcelevate.ex1;

public class EmailService {
    private String smtpServer;

    public EmailService(String smtpServer) {
        this.smtpServer = smtpServer;
    }

    public void sendEmail(String to, String message) {
        System.out.println("Sending email to: " + to);
        System.out.println("Message: " + message);
        System.out.println("Via SMTP Server: " + smtpServer);
    }
}
```

**Step 3:** Create `src/main/java/com/xcelevate/ex1/NotificationService.java`:

```java
package com.xcelevate.ex1;

public class NotificationService {
    private EmailService emailService;

    public NotificationService(EmailService emailService) {
        this.emailService = emailService;
    }

    public void notifyUser(String email, String notification) {
        System.out.println("NotificationService: Processing notification...");
        emailService.sendEmail(email, notification);
    }
}
```

**Step 4:** Create `src/main/resources/ex1-beans.xml`:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
       http://www.springframework.org/schema/beans/spring-beans.xsd">

    <bean id="emailService" class="com.xcelevate.ex1.EmailService">
        <constructor-arg value="smtp.xcelevate.com"/>
    </bean>

    <bean id="notificationService" class="com.xcelevate.ex1.NotificationService">
        <constructor-arg ref="emailService"/>
    </bean>
</beans>
```

**Step 5:** Create `src/main/java/com/xcelevate/ex1/MainApp.java`:

```java
package com.xcelevate.ex1;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MainApp {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("ex1-beans.xml");

        NotificationService service = context.getBean("notificationService", NotificationService.class);
        service.notifyUser("user@xcelevate.com", "Welcome to Spring Training!");

        ((ClassPathXmlApplicationContext) context).close();
    }
}
```

**Step 6:** Run `MainApp.java` and verify output shows email being sent through EmailService.

---

## Lab Exercise 2

### Spring XML-Based Dependency Injection - Setter Injection

**Objective:** Learn XML configuration with setter-based dependency injection.

**Step 1:** Create `src/main/java/com/xcelevate/ex2/DatabaseConfig.java`:

```java
package com.xcelevate.ex2;

public class DatabaseConfig {
    private String url;
    private String username;
    private String password;

    public void setUrl(String url) { this.url = url; }
    public void setUsername(String username) { this.username = username; }
    public void setPassword(String password) { this.password = password; }

    public void printConfig() {
        System.out.println("Database URL: " + url);
        System.out.println("Username: " + username);
        System.out.println("Password: " + password);
    }
}
```

**Step 2:** Create `src/main/java/com/xcelevate/ex2/DatabaseService.java`:

```java
package com.xcelevate.ex2;

public class DatabaseService {
    private DatabaseConfig config;

    public void setConfig(DatabaseConfig config) {
        this.config = config;
    }

    public void connect() {
        System.out.println("Connecting to database...");
        config.printConfig();
        System.out.println("Connection established!");
    }
}
```

**Step 3:** Create `src/main/resources/ex2-beans.xml`:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
       http://www.springframework.org/schema/beans/spring-beans.xsd">

    <bean id="databaseConfig" class="com.xcelevate.ex2.DatabaseConfig">
        <property name="url" value="jdbc:mysql://localhost:3306/testdb"/>
        <property name="username" value="root"/>
        <property name="password" value="admin123"/>
    </bean>

    <bean id="databaseService" class="com.xcelevate.ex2.DatabaseService">
        <property name="config" ref="databaseConfig"/>
    </bean>
</beans>
```

**Step 4:** Create `src/main/java/com/xcelevate/ex2/MainApp.java`:

```java
package com.xcelevate.ex2;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MainApp {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("ex2-beans.xml");

        DatabaseService service = context.getBean("databaseService", DatabaseService.class);
        service.connect();

        ((ClassPathXmlApplicationContext) context).close();
    }
}
```

**Step 5:** Run and verify database configuration is properly injected via setters.

---

## Lab Exercise 3

### Spring Java-Based Configuration

**Objective:** Learn Java-based configuration with @Configuration and @Bean annotations.

**Step 1:** Create `src/main/java/com/xcelevate/ex3/MessageService.java`:

```java
package com.xcelevate.ex3;

public interface MessageService {
    String getMessage();
}
```

**Step 2:** Create `src/main/java/com/xcelevate/ex3/EmailMessageService.java`:

```java
package com.xcelevate.ex3;

public class EmailMessageService implements MessageService {
    @Override
    public String getMessage() {
        return "Email Message Service";
    }
}
```

**Step 3:** Create `src/main/java/com/xcelevate/ex3/SmsMessageService.java`:

```java
package com.xcelevate.ex3;

public class SmsMessageService implements MessageService {
    @Override
    public String getMessage() {
        return "SMS Message Service";
    }
}
```

**Step 4:** Create `src/main/java/com/xcelevate/ex3/MessageProcessor.java`:

```java
package com.xcelevate.ex3;

public class MessageProcessor {
    private MessageService messageService;

    public MessageProcessor(MessageService messageService) {
        this.messageService = messageService;
    }

    public void processMessage() {
        System.out.println("Processing with: " + messageService.getMessage());
    }
}
```

**Step 5:** Create `src/main/java/com/xcelevate/ex3/AppConfig.java`:

```java
package com.xcelevate.ex3;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public MessageService emailMessageService() {
        return new EmailMessageService();
    }

    @Bean
    public MessageService smsMessageService() {
        return new SmsMessageService();
    }

    @Bean
    public MessageProcessor messageProcessor() {
        return new MessageProcessor(emailMessageService());
    }
}
```

**Step 6:** Create `src/main/java/com/xcelevate/ex3/MainApp.java`:

```java
package com.xcelevate.ex3;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MainApp {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        MessageProcessor processor = context.getBean(MessageProcessor.class);
        processor.processMessage();

        ((AnnotationConfigApplicationContext) context).close();
    }
}
```

**Step 7:** Run and observe Java configuration working. Try changing `emailMessageService()` to `smsMessageService()` in `messageProcessor()` bean.

---

## Lab Exercise 4

### Spring Annotation-Based Configuration with @Component and @Autowired

**Objective:** Learn component scanning and autowiring with annotations.

**Step 1:** Create `src/main/java/com/xcelevate/ex4/Logger.java`:

```java
package com.xcelevate.ex4;

import org.springframework.stereotype.Component;

@Component
public class Logger {
    public void log(String message) {
        System.out.println("[LOG] " + message);
    }
}
```

**Step 2:** Create `src/main/java/com/xcelevate/ex4/UserRepository.java`:

```java
package com.xcelevate.ex4;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {

    @Autowired
    private Logger logger;

    public void save(String username) {
        logger.log("Saving user: " + username);
        System.out.println("User " + username + " saved to database");
    }

    public String findById(int id) {
        logger.log("Finding user with ID: " + id);
        return "User_" + id;
    }
}
```

**Step 3:** Create `src/main/java/com/xcelevate/ex4/UserService.java`:

```java
package com.xcelevate.ex4;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void createUser(String username) {
        System.out.println("UserService: Creating user...");
        userRepository.save(username);
    }

    public void getUser(int id) {
        System.out.println("UserService: Retrieving user...");
        String user = userRepository.findById(id);
        System.out.println("Retrieved: " + user);
    }
}
```

**Step 4:** Create `src/main/java/com/xcelevate/ex4/AppConfig.java`:

```java
package com.xcelevate.ex4;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.xcelevate.ex4")
public class AppConfig {
}
```

**Step 5:** Create `src/main/java/com/xcelevate/ex4/MainApp.java`:

```java
package com.xcelevate.ex4;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MainApp {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        UserService userService = context.getBean(UserService.class);
        userService.createUser("john_doe");
        userService.getUser(101);

        ((AnnotationConfigApplicationContext) context).close();
    }
}
```

**Step 6:** Run and verify @Autowired dependencies are automatically injected through component scanning.

---

## Lab Exercise 5

### Spring Bean Scopes - Singleton

**Objective:** Understand singleton scope (default) where Spring creates only one instance.

**Step 1:** Create `src/main/java/com/xcelevate/ex5/SingletonBean.java`:

```java
package com.xcelevate.ex5;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import java.util.UUID;

@Component
@Scope("singleton")
public class SingletonBean {
    private String id;

    public SingletonBean() {
        this.id = UUID.randomUUID().toString();
        System.out.println("SingletonBean created with ID: " + id);
    }

    public String getId() {
        return id;
    }
}
```

**Step 2:** Create `src/main/java/com/xcelevate/ex5/AppConfig.java`:

```java
package com.xcelevate.ex5;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.xcelevate.ex5")
public class AppConfig {
}
```

**Step 3:** Create `src/main/java/com/xcelevate/ex5/MainApp.java`:

```java
package com.xcelevate.ex5;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MainApp {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        SingletonBean bean1 = context.getBean(SingletonBean.class);
        SingletonBean bean2 = context.getBean(SingletonBean.class);
        SingletonBean bean3 = context.getBean(SingletonBean.class);

        System.out.println("Bean1 ID: " + bean1.getId());
        System.out.println("Bean2 ID: " + bean2.getId());
        System.out.println("Bean3 ID: " + bean3.getId());
        System.out.println("Are bean1 and bean2 same? " + (bean1 == bean2));
        System.out.println("Are bean2 and bean3 same? " + (bean2 == bean3));

        ((AnnotationConfigApplicationContext) context).close();
    }
}
```

**Step 4:** Run and observe all three beans have the same ID and reference - only one instance is created.

---

## Lab Exercise 6

### Spring Bean Scopes - Prototype

**Objective:** Understand prototype scope where Spring creates a new instance for each request.

**Step 1:** Create `src/main/java/com/xcelevate/ex6/PrototypeBean.java`:

```java
package com.xcelevate.ex6;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import java.util.UUID;

@Component
@Scope("prototype")
public class PrototypeBean {
    private String id;

    public PrototypeBean() {
        this.id = UUID.randomUUID().toString();
        System.out.println("PrototypeBean created with ID: " + id);
    }

    public String getId() {
        return id;
    }
}
```

**Step 2:** Create `src/main/java/com/xcelevate/ex6/AppConfig.java`:

```java
package com.xcelevate.ex6;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.xcelevate.ex6")
public class AppConfig {
}
```

**Step 3:** Create `src/main/java/com/xcelevate/ex6/MainApp.java`:

```java
package com.xcelevate.ex6;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MainApp {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        PrototypeBean bean1 = context.getBean(PrototypeBean.class);
        PrototypeBean bean2 = context.getBean(PrototypeBean.class);
        PrototypeBean bean3 = context.getBean(PrototypeBean.class);

        System.out.println("Bean1 ID: " + bean1.getId());
        System.out.println("Bean2 ID: " + bean2.getId());
        System.out.println("Bean3 ID: " + bean3.getId());
        System.out.println("Are bean1 and bean2 same? " + (bean1 == bean2));
        System.out.println("Are bean2 and bean3 same? " + (bean2 == bean3));

        ((AnnotationConfigApplicationContext) context).close();
    }
}
```

**Step 4:** Run and observe each bean has a different ID - new instance created for each `getBean()` call.

---

## Lab Exercise 7

### Spring Bean Scopes - Comparing Singleton and Prototype

**Objective:** Compare both scopes in a single application.

**Step 1:** Copy both `SingletonBean.java` and `PrototypeBean.java` from exercises 5 and 6 to package `com.xcelevate.ex7`, changing package declarations to `com.xcelevate.ex7`.

**Step 2:** Create `src/main/java/com/xcelevate/ex7/ScopeComparator.java`:

```java
package com.xcelevate.ex7;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class ScopeComparator {

    @Autowired
    private ApplicationContext context;

    public void compareScopes() {
        System.out.println("\n=== Testing Singleton Scope ===");
        SingletonBean s1 = context.getBean(SingletonBean.class);
        SingletonBean s2 = context.getBean(SingletonBean.class);
        System.out.println("Singleton bean1 ID: " + s1.getId());
        System.out.println("Singleton bean2 ID: " + s2.getId());
        System.out.println("Same instance? " + (s1 == s2));

        System.out.println("\n=== Testing Prototype Scope ===");
        PrototypeBean p1 = context.getBean(PrototypeBean.class);
        PrototypeBean p2 = context.getBean(PrototypeBean.class);
        System.out.println("Prototype bean1 ID: " + p1.getId());
        System.out.println("Prototype bean2 ID: " + p2.getId());
        System.out.println("Same instance? " + (p1 == p2));
    }
}
```

**Step 3:** Create `src/main/java/com/xcelevate/ex7/AppConfig.java`:

```java
package com.xcelevate.ex7;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.xcelevate.ex7")
public class AppConfig {
}
```

**Step 4:** Create `src/main/java/com/xcelevate/ex7/MainApp.java`:

```java
package com.xcelevate.ex7;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MainApp {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        ScopeComparator comparator = context.getBean(ScopeComparator.class);
        comparator.compareScopes();

        ((AnnotationConfigApplicationContext) context).close();
    }
}
```

**Step 5:** Run and compare outputs to understand the difference clearly.

---

## Lab Exercise 8

### Spring JDBC - Basic Setup with MySQL

**Objective:** Set up Spring JDBC with MySQL database and perform basic operations.

**Step 1:** The required dependencies are already in the pom.xml (spring-jdbc, mysql-connector-j, HikariCP).

**Step 2:** Create database and table in MySQL:

```sql
CREATE DATABASE accountsdb;
USE accountsdb;

CREATE TABLE accounts (
    accountId INT PRIMARY KEY AUTO_INCREMENT,
    accountName VARCHAR(50) NOT NULL,
    balance DECIMAL(10,2) NOT NULL
);

INSERT INTO accounts (accountName, balance) VALUES 
('Savings Account', 5000.00),
('Checking Account', 3000.00),
('Investment Account', 10000.00);
```

**Step 3:** Create `src/main/java/com/xcelevate/ex8/Account.java`:

```java
package com.xcelevate.ex8;

public class Account {
    private int accountId;
    private String accountName;
    private double balance;

    // Constructors
    public Account() {}

    public Account(int accountId, String accountName, double balance) {
        this.accountId = accountId;
        this.accountName = accountName;
        this.balance = balance;
    }

    // Getters and Setters
    public int getAccountId() { return accountId; }
    public void setAccountId(int accountId) { this.accountId = accountId; }

    public String getAccountName() { return accountName; }
    public void setAccountName(String accountName) { this.accountName = accountName; }

    public double getBalance() { return balance; }
    public void setBalance(double balance) { this.balance = balance; }

    @Override
    public String toString() {
        return "Account{" +
                "accountId=" + accountId +
                ", accountName='" + accountName + '\'' +
                ", balance=" + balance +
                '}';
    }
}
```

**Step 4:** Create `src/main/java/com/xcelevate/ex8/AccountDao.java`:

```java
package com.xcelevate.ex8;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class AccountDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private RowMapper<Account> rowMapper = new RowMapper<Account>() {
        @Override
        public Account mapRow(ResultSet rs, int rowNum) throws SQLException {
            Account account = new Account();
            account.setAccountId(rs.getInt("accountId"));
            account.setAccountName(rs.getString("accountName"));
            account.setBalance(rs.getDouble("balance"));
            return account;
        }
    };

    public int save(Account account) {
        String sql = "INSERT INTO accounts (accountName, balance) VALUES (?, ?)";
        return jdbcTemplate.update(sql, account.getAccountName(), account.getBalance());
    }

    public Account findById(int id) {
        String sql = "SELECT * FROM accounts WHERE accountId = ?";
        return jdbcTemplate.queryForObject(sql, rowMapper, id);
    }

    public List<Account> findAll() {
        String sql = "SELECT * FROM accounts";
        return jdbcTemplate.query(sql, rowMapper);
    }

    public int update(Account account) {
        String sql = "UPDATE accounts SET accountName = ?, balance = ? WHERE accountId = ?";
        return jdbcTemplate.update(sql, account.getAccountName(), account.getBalance(), account.getAccountId());
    }

    public int delete(int id) {
        String sql = "DELETE FROM accounts WHERE accountId = ?";
        return jdbcTemplate.update(sql, id);
    }
}
```

**Step 5:** Create `src/main/java/com/xcelevate/ex8/AppConfig.java`:

```java
package com.xcelevate.ex8;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import javax.sql.DataSource;

@Configuration
@ComponentScan("com.xcelevate.ex8")
public class AppConfig {

    @Bean
    public DataSource dataSource() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:mysql://localhost:3306/accountsdb");
        config.setUsername("root");
        config.setPassword("your_password");
        config.setDriverClassName("com.mysql.cj.jdbc.Driver");
        return new HikariDataSource(config);
    }

    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(dataSource());
    }
}
```

**Step 6:** Create `src/main/java/com/xcelevate/ex8/MainApp.java`:

```java
package com.xcelevate.ex8;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import java.util.List;

public class MainApp {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        AccountDao accountDao = context.getBean(AccountDao.class);

        // Find all accounts
        System.out.println("=== All Accounts ===");
        List<Account> accounts = accountDao.findAll();
        accounts.forEach(System.out::println);

        // Find by ID
        System.out.println("\n=== Find Account by ID ===");
        Account account = accountDao.findById(1);
        System.out.println(account);

        // Insert new account
        System.out.println("\n=== Insert New Account ===");
        Account newAccount = new Account(0, "Emergency Fund", 2000.00);
        accountDao.save(newAccount);
        System.out.println("Account saved!");

        // Update account
        System.out.println("\n=== Update Account ===");
        account.setBalance(5500.00);
        accountDao.update(account);
        System.out.println("Account updated: " + accountDao.findById(1));

        ((AnnotationConfigApplicationContext) context).close();
    }
}
```

**Step 7:** Update MySQL password in `AppConfig.java` and run the application. Observe CRUD operations.

---

## Lab Exercise 9

### Spring ORM with Hibernate

**Objective:** Use Spring ORM with Hibernate for the same accounts table.

**Step 1:** The required dependencies are already in pom.xml (spring-orm, hibernate-core, jakarta.persistence-api).

**Step 2:** Create `src/main/java/com/xcelevate/ex9/Account.java`:

```java
package com.xcelevate.ex9;

import jakarta.persistence.*;

@Entity
@Table(name = "accounts")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int accountId;

    @Column(name = "accountName", nullable = false, length = 50)
    private String accountName;

    @Column(name = "balance", nullable = false)
    private double balance;

    // Constructors
    public Account() {}

    public Account(String accountName, double balance) {
        this.accountName = accountName;
        this.balance = balance;
    }

    // Getters and Setters
    public int getAccountId() { return accountId; }
    public void setAccountId(int accountId) { this.accountId = accountId; }

    public String getAccountName() { return accountName; }
    public void setAccountName(String accountName) { this.accountName = accountName; }

    public double getBalance() { return balance; }
    public void setBalance(double balance) { this.balance = balance; }

    @Override
    public String toString() {
        return "Account{" +
                "accountId=" + accountId +
                ", accountName='" + accountName + '\'' +
                ", balance=" + balance +
                '}';
    }
}
```

**Step 3:** Create `src/main/java/com/xcelevate/ex9/AccountDao.java`:

```java
package com.xcelevate.ex9;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class AccountDao {

    @Autowired
    private SessionFactory sessionFactory;

    public void save(Account account) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        session.persist(account);
        tx.commit();
        session.close();
    }

    public Account findById(int id) {
        Session session = sessionFactory.openSession();
        Account account = session.get(Account.class, id);
        session.close();
        return account;
    }

    public List<Account> findAll() {
        Session session = sessionFactory.openSession();
        List<Account> accounts = session.createQuery("FROM Account", Account.class).list();
        session.close();
        return accounts;
    }

    public void update(Account account) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        session.merge(account);
        tx.commit();
        session.close();
    }

    public void delete(int id) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        Account account = session.get(Account.class, id);
        if (account != null) {
            session.remove(account);
        }
        tx.commit();
        session.close();
    }
}
```

**Step 4:** Create `src/main/java/com/xcelevate/ex9/AppConfig.java`:

```java
package com.xcelevate.ex9;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@ComponentScan("com.xcelevate.ex9")
public class AppConfig {

    @Bean
    public DataSource dataSource() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:mysql://localhost:3306/accountsdb");
        config.setUsername("root");
        config.setPassword("your_password");
        config.setDriverClassName("com.mysql.cj.jdbc.Driver");
        return new HikariDataSource(config);
    }

    @Bean
    public LocalSessionFactoryBean sessionFactory() {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource());
        sessionFactory.setPackagesToScan("com.xcelevate.ex9");
        sessionFactory.setHibernateProperties(hibernateProperties());
        return sessionFactory;
    }

    private Properties hibernateProperties() {
        Properties properties = new Properties();
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        properties.setProperty("hibernate.show_sql", "true");
        properties.setProperty("hibernate.format_sql", "true");
        properties.setProperty("hibernate.hbm2ddl.auto", "update");
        return properties;
    }
}
```

**Step 5:** Create `src/main/java/com/xcelevate/ex9/MainApp.java`:

```java
package com.xcelevate.ex9;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import java.util.List;

public class MainApp {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        AccountDao accountDao = context.getBean(AccountDao.class);

        // Find all accounts
        System.out.println("=== All Accounts (Hibernate ORM) ===");
        List<Account> accounts = accountDao.findAll();
        accounts.forEach(System.out::println);

        // Find by ID
        System.out.println("\n=== Find Account by ID ===");
        Account account = accountDao.findById(1);
        System.out.println(account);

        // Insert new account
        System.out.println("\n=== Insert New Account ===");
        Account newAccount = new Account("Retirement Fund", 15000.00);
        accountDao.save(newAccount);
        System.out.println("Account saved with Hibernate!");

        // Update account
        System.out.println("\n=== Update Account ===");
        account.setBalance(6000.00);
        accountDao.update(account);
        System.out.println("Account updated: " + accountDao.findById(1));

        ((AnnotationConfigApplicationContext) context).close();
    }
}
```

**Step 6:** Run and observe Hibernate SQL logs showing ORM operations on the same database.

---

## Lab Exercise 10

### Spring Data JPA

**Objective:** Use Spring Data JPA for simplified data access.

**Step 1:** The required dependencies are already in pom.xml (spring-data-jpa).

**Step 2:** Copy `Account.java` from Exercise 9 to `com.xcelevate.ex10` package (change package declaration).

**Step 3:** Create `src/main/java/com/xcelevate/ex10/AccountRepository.java`:

```java
package com.xcelevate.ex10;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {

    // Custom query methods
    List<Account> findByAccountName(String accountName);

    List<Account> findByBalanceGreaterThan(double balance);

    @Query("SELECT a FROM Account a WHERE a.balance < ?1")
    List<Account> findLowBalanceAccounts(double threshold);
}
```

**Step 4:** Create `src/main/java/com/xcelevate/ex10/AppConfig.java`:

```java
package com.xcelevate.ex10;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableJpaRepositories(basePackages = "com.xcelevate.ex10")
public class AppConfig {

    @Bean
    public DataSource dataSource() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:mysql://localhost:3306/accountsdb");
        config.setUsername("root");
        config.setPassword("your_password");
        config.setDriverClassName("com.mysql.cj.jdbc.Driver");
        return new HikariDataSource(config);
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource());
        em.setPackagesToScan("com.xcelevate.ex10");
        em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        em.setJpaProperties(hibernateProperties());
        return em;
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
        return transactionManager;
    }

    private Properties hibernateProperties() {
        Properties properties = new Properties();
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        properties.setProperty("hibernate.show_sql", "true");
        properties.setProperty("hibernate.format_sql", "true");
        properties.setProperty("hibernate.hbm2ddl.auto", "update");
        return properties;
    }
}
```

**Step 5:** Create `src/main/java/com/xcelevate/ex10/MainApp.java`:

```java
package com.xcelevate.ex10;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import java.util.List;
import java.util.Optional;

public class MainApp {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        AccountRepository repository = context.getBean(AccountRepository.class);

        // Find all
        System.out.println("=== All Accounts (Spring Data JPA) ===");
        List<Account> accounts = repository.findAll();
        accounts.forEach(System.out::println);

        // Find by ID
        System.out.println("\n=== Find by ID ===");
        Optional<Account> accountOpt = repository.findById(1);
        accountOpt.ifPresent(System.out::println);

        // Save new account
        System.out.println("\n=== Save New Account ===");
        Account newAccount = new Account("College Fund", 8000.00);
        repository.save(newAccount);
        System.out.println("Account saved!");

        // Custom queries
        System.out.println("\n=== Accounts with Balance > 4000 ===");
        List<Account> highBalance = repository.findByBalanceGreaterThan(4000.00);
        highBalance.forEach(System.out::println);

        System.out.println("\n=== Low Balance Accounts (< 3500) ===");
        List<Account> lowBalance = repository.findLowBalanceAccounts(3500.00);
        lowBalance.forEach(System.out::println);

        ((AnnotationConfigApplicationContext) context).close();
    }
}
```

**Step 6:** Run and observe Spring Data JPA's automatic query generation and simplified CRUD.

---

## Lab Exercise 11

### Spring Bean Lifecycle - InitializingBean and DisposableBean

**Objective:** Learn Spring bean lifecycle callbacks using interfaces.

**Step 1:** Create `src/main/java/com/xcelevate/ex11/DatabaseConnection.java`:

```java
package com.xcelevate.ex11;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

@Component
public class DatabaseConnection implements InitializingBean, DisposableBean {

    private String connectionUrl;
    private boolean connected = false;

    public DatabaseConnection() {
        System.out.println("1. Constructor called - DatabaseConnection object created");
        this.connectionUrl = "jdbc:mysql://localhost:3306/accountsdb";
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("2. afterPropertiesSet() called - Initializing connection...");
        connect();
    }

    private void connect() {
        System.out.println("3. Connecting to: " + connectionUrl);
        connected = true;
        System.out.println("4. Connection established!");
    }

    public void executeQuery(String query) {
        if (connected) {
            System.out.println("Executing query: " + query);
        } else {
            System.out.println("Not connected!");
        }
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("5. destroy() called - Closing connection...");
        if (connected) {
            connected = false;
            System.out.println("6. Connection closed!");
        }
    }
}
```

**Step 2:** Create `src/main/java/com/xcelevate/ex11/AppConfig.java`:

```java
package com.xcelevate.ex11;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.xcelevate.ex11")
public class AppConfig {
}
```

**Step 3:** Create `src/main/java/com/xcelevate/ex11/MainApp.java`:

```java
package com.xcelevate.ex11;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MainApp {
    public static void main(String[] args) {
        System.out.println("=== Starting Application Context ===\n");

        AnnotationConfigApplicationContext context = 
            new AnnotationConfigApplicationContext(AppConfig.class);

        System.out.println("\n=== Context Started - Bean is Ready ===\n");

        DatabaseConnection dbConnection = context.getBean(DatabaseConnection.class);
        dbConnection.executeQuery("SELECT * FROM accounts");

        System.out.println("\n=== Closing Application Context ===\n");
        context.close();

        System.out.println("\n=== Application Context Closed ===");
    }
}
```

**Step 4:** Run and observe the complete lifecycle: Constructor → afterPropertiesSet() → Bean usage → destroy().

---

## Lab Exercise 12

### Spring Bean Lifecycle - Custom Init and Destroy Methods

**Objective:** Learn custom init and destroy methods using annotations.

**Step 1:** Create `src/main/java/com/xcelevate/ex12/CacheService.java`:

```java
package com.xcelevate.ex12;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.stereotype.Component;
import java.util.HashMap;
import java.util.Map;

@Component
public class CacheService {

    private Map<String, Object> cache;

    public CacheService() {
        System.out.println("1. Constructor: CacheService object created");
    }

    @PostConstruct
    public void init() {
        System.out.println("2. @PostConstruct: Initializing cache...");
        cache = new HashMap<>();
        cache.put("config1", "value1");
        cache.put("config2", "value2");
        System.out.println("3. Cache initialized with default values");
    }

    public void put(String key, Object value) {
        System.out.println("Adding to cache: " + key + " = " + value);
        cache.put(key, value);
    }

    public Object get(String key) {
        return cache.get(key);
    }

    public void displayCache() {
        System.out.println("Cache contents: " + cache);
    }

    @PreDestroy
    public void cleanup() {
        System.out.println("4. @PreDestroy: Cleaning up cache...");
        if (cache != null) {
            cache.clear();
            System.out.println("5. Cache cleared!");
        }
    }
}
```

**Step 2:** Create `src/main/java/com/xcelevate/ex12/AppConfig.java`:

```java
package com.xcelevate.ex12;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.xcelevate.ex12")
public class AppConfig {
}
```

**Step 3:** Create `src/main/java/com/xcelevate/ex12/MainApp.java`:

```java
package com.xcelevate.ex12;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MainApp {
    public static void main(String[] args) {
        System.out.println("=== Creating Application Context ===\n");

        AnnotationConfigApplicationContext context = 
            new AnnotationConfigApplicationContext(AppConfig.class);

        System.out.println("\n=== Using CacheService Bean ===\n");

        CacheService cacheService = context.getBean(CacheService.class);
        cacheService.displayCache();
        cacheService.put("user1", "John Doe");
        cacheService.put("user2", "Jane Smith");
        cacheService.displayCache();

        System.out.println("\n=== Shutting Down Context ===\n");
        context.close();

        System.out.println("\n=== Application Ended ===");
    }
}
```

**Step 4:** Run and observe @PostConstruct and @PreDestroy lifecycle callbacks.

---

## Lab Exercise 13

### Spring Bean Lifecycle - Complete Lifecycle with All Callbacks

**Objective:** Demonstrate all lifecycle phases together.

**Step 1:** Create `src/main/java/com/xcelevate/ex13/LifecycleBean.java`:

```java
package com.xcelevate.ex13;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component("lifecycleBean")
public class LifecycleBean implements BeanNameAware, BeanFactoryAware, 
        ApplicationContextAware, InitializingBean, DisposableBean {

    private String beanName;

    public LifecycleBean() {
        System.out.println("1. Constructor called");
    }

    @Override
    public void setBeanName(String name) {
        System.out.println("2. setBeanName() called, bean name: " + name);
        this.beanName = name;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        System.out.println("3. setBeanFactory() called");
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        System.out.println("4. setApplicationContext() called");
    }

    @PostConstruct
    public void postConstruct() {
        System.out.println("5. @PostConstruct method called");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("6. afterPropertiesSet() called");
    }

    public void doWork() {
        System.out.println("\n*** Bean is ready for use: " + beanName + " ***\n");
    }

    @PreDestroy
    public void preDestroy() {
        System.out.println("7. @PreDestroy method called");
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("8. destroy() method called");
    }
}
```

**Step 2:** Create `src/main/java/com/xcelevate/ex13/AppConfig.java`:

```java
package com.xcelevate.ex13;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.xcelevate.ex13")
public class AppConfig {
}
```

**Step 3:** Create `src/main/java/com/xcelevate/ex13/MainApp.java`:

```java
package com.xcelevate.ex13;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MainApp {
    public static void main(String[] args) {
        System.out.println("=== Creating Application Context ===\n");

        AnnotationConfigApplicationContext context = 
            new AnnotationConfigApplicationContext(AppConfig.class);

        System.out.println("\n=== Bean Initialized ===");

        LifecycleBean bean = context.getBean(LifecycleBean.class);
        bean.doWork();

        System.out.println("=== Closing Context ===\n");
        context.close();

        System.out.println("\n=== Application Ended ===");
    }
}
```

**Step 4:** Run and observe the complete bean lifecycle sequence from creation to destruction.

---

## Lab Exercise 14

### Spring AOP for Logging (Using spring-aspects)

**Objective:** Implement cross-cutting logging concerns using Spring's spring-aspects module.

**Step 1:** The required dependencies are in pom.xml (spring-aop, spring-aspects).

**Step 2:** Create `src/main/java/com/xcelevate/ex14/AccountService.java`:

```java
package com.xcelevate.ex14;

import org.springframework.stereotype.Service;

@Service
public class AccountService {

    public void createAccount(String accountName, double balance) {
        System.out.println("  [SERVICE] Creating account: " + accountName + " with balance: $" + balance);
    }

    public double getBalance(String accountName) {
        System.out.println("  [SERVICE] Fetching balance for account: " + accountName);
        return 5000.00;
    }

    public void transferFunds(String from, String to, double amount) {
        System.out.println("  [SERVICE] Transferring $" + amount + " from " + from + " to " + to);
    }

    public void deleteAccount(String accountName) {
        System.out.println("  [SERVICE] Deleting account: " + accountName);
    }

    public void riskyOperation(String accountName) {
        System.out.println("  [SERVICE] Executing risky operation for: " + accountName);
        if (accountName.equals("INVALID")) {
            throw new RuntimeException("Invalid account name!");
        }
    }
}
```

**Step 3:** Create `src/main/java/com/xcelevate/ex14/TransactionService.java`:

```java
package com.xcelevate.ex14;

import org.springframework.stereotype.Service;

@Service
public class TransactionService {

    public void processTransaction(int transactionId, double amount) {
        System.out.println("  [TRANSACTION] Processing transaction ID: " + transactionId + ", Amount: $" + amount);
    }

    public void rollbackTransaction(int transactionId) {
        System.out.println("  [TRANSACTION] Rolling back transaction ID: " + transactionId);
    }
}
```

**Step 4:** Create `src/main/java/com/xcelevate/ex14/LoggingAspect.java`:

```java
package com.xcelevate.ex14;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import java.util.Arrays;

@Aspect
@Component
public class LoggingAspect {

    // Pointcut for all methods in services
    @Pointcut("execution(* com.xcelevate.ex14.*Service.*(..))")
    public void serviceMethods() {}

    // Before advice
    @Before("serviceMethods()")
    public void logBefore(JoinPoint joinPoint) {
        System.out.println("\n[BEFORE ADVICE - spring-aspects]");
        System.out.println("  Method: " + joinPoint.getSignature().getName());
        System.out.println("  Target: " + joinPoint.getTarget().getClass().getSimpleName());
        System.out.println("  Arguments: " + Arrays.toString(joinPoint.getArgs()));
    }

    // After returning advice
    @AfterReturning(pointcut = "serviceMethods()", returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        System.out.println("[AFTER RETURNING ADVICE - spring-aspects]");
        System.out.println("  Method: " + joinPoint.getSignature().getName());
        System.out.println("  Return Value: " + result);
        System.out.println("  Status: ✓ SUCCESS\n");
    }

    // After throwing advice
    @AfterThrowing(pointcut = "serviceMethods()", throwing = "error")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable error) {
        System.out.println("[AFTER THROWING ADVICE - spring-aspects]");
        System.out.println("  Method: " + joinPoint.getSignature().getName());
        System.out.println("  Exception: " + error.getClass().getSimpleName());
        System.out.println("  Message: " + error.getMessage());
        System.out.println("  Status: ✗ FAILED\n");
    }

    // After (finally) advice
    @After("serviceMethods()")
    public void logAfter(JoinPoint joinPoint) {
        System.out.println("  [AFTER ADVICE] Cleanup completed for: " + joinPoint.getSignature().getName());
    }

    // Around advice for specific methods
    @Around("execution(* com.xcelevate.ex14.AccountService.transferFunds(..))")
    public Object logAroundTransfer(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("\n╔════════════════════════════════════════════╗");
        System.out.println("║ [AROUND ADVICE - BEFORE] spring-aspects   ║");
        System.out.println("╚════════════════════════════════════════════╝");
        System.out.println("  Method: " + joinPoint.getSignature().getName());
        System.out.println("  Arguments: " + Arrays.toString(joinPoint.getArgs()));

        long startTime = System.currentTimeMillis();

        Object result = null;
        try {
            result = joinPoint.proceed();

            long executionTime = System.currentTimeMillis() - startTime;

            System.out.println("╔════════════════════════════════════════════╗");
            System.out.println("║ [AROUND ADVICE - AFTER] spring-aspects    ║");
            System.out.println("╚════════════════════════════════════════════╝");
            System.out.println("  Execution Time: " + executionTime + "ms");
            System.out.println("  Status: ✓ Completed successfully\n");

        } catch (Exception e) {
            long executionTime = System.currentTimeMillis() - startTime;
            System.out.println("╔════════════════════════════════════════════╗");
            System.out.println("║ [AROUND ADVICE - ERROR] spring-aspects    ║");
            System.out.println("╚════════════════════════════════════════════╝");
            System.out.println("  Execution Time: " + executionTime + "ms");
            System.out.println("  Error: " + e.getMessage() + "\n");
            throw e;
        }

        return result;
    }
}
```

**Step 5:** Create `src/main/java/com/xcelevate/ex14/SecurityAspect.java`:

```java
package com.xcelevate.ex14;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class SecurityAspect {

    @Pointcut("execution(* com.xcelevate.ex14.AccountService.delete*(..))")
    public void deleteMethods() {}

    @Before("deleteMethods()")
    public void checkSecurity(JoinPoint joinPoint) {
        System.out.println("\n[SECURITY ASPECT - spring-aspects]");
        System.out.println("  ⚠ Security check for: " + joinPoint.getSignature().getName());
        System.out.println("  ✓ User authorized to delete accounts");
        System.out.println("  ✓ Audit log created");
    }
}
```

**Step 6:** Create `src/main/java/com/xcelevate/ex14/AppConfig.java`:

```java
package com.xcelevate.ex14;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy
@ComponentScan("com.xcelevate.ex14")
public class AppConfig {
}
```

**Step 7:** Create `src/main/java/com/xcelevate/ex14/MainApp.java`:

```java
package com.xcelevate.ex14;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MainApp {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        AccountService accountService = context.getBean(AccountService.class);
        TransactionService transactionService = context.getBean(TransactionService.class);

        System.out.println("═══════════════════════════════════════════════════════");
        System.out.println("  SPRING 7.0.3 AOP WITH spring-aspects DEMO");
        System.out.println("═══════════════════════════════════════════════════════");

        System.out.println("\n>>> TEST 1: Create Account");
        accountService.createAccount("Savings Account", 10000.00);

        System.out.println("\n>>> TEST 2: Get Balance");
        double balance = accountService.getBalance("Savings Account");
        System.out.println("  Returned balance: $" + balance);

        System.out.println("\n>>> TEST 3: Transfer Funds");
        accountService.transferFunds("Savings", "Checking", 2000.00);

        System.out.println("\n>>> TEST 4: Delete Account");
        accountService.deleteAccount("Old Account");

        System.out.println("\n>>> TEST 5: Process Transaction");
        transactionService.processTransaction(12345, 500.00);

        System.out.println("\n>>> TEST 6: Risky Operation (Exception)");
        try {
            accountService.riskyOperation("INVALID");
        } catch (RuntimeException e) {
            System.out.println("  Exception caught in main: " + e.getMessage());
        }

        System.out.println("\n═══════════════════════════════════════════════════════");

        ((AnnotationConfigApplicationContext) context).close();
    }
}
```

**Step 8:** Run and observe Spring 7.0.3 with spring-aspects module working with multiple aspects.

---

## Lab Exercise 15

### Spring AOP with AspectJ for Logging

**Objective:** Implement comprehensive logging using AspectJ-powered Spring AOP.

**Step 1:** The required dependencies are in pom.xml (aspectjweaver, aspectjrt).

**Step 2:** Create `src/main/java/com/xcelevate/ex15/AccountService.java`:

```java
package com.xcelevate.ex15;

import org.springframework.stereotype.Service;

@Service
public class AccountService {

    public void createAccount(String accountName, double balance) {
        System.out.println("  [SERVICE] Creating account: " + accountName + " with balance: $" + balance);
        try { Thread.sleep(50); } catch (InterruptedException e) {}
    }

    public double getBalance(String accountName) {
        System.out.println("  [SERVICE] Fetching balance for account: " + accountName);
        try { Thread.sleep(30); } catch (InterruptedException e) {}
        return 5000.00;
    }

    public void transferFunds(String from, String to, double amount) {
        System.out.println("  [SERVICE] Transferring $" + amount + " from " + from + " to " + to);
        try { Thread.sleep(100); } catch (InterruptedException e) {}
    }

    public void deleteAccount(String accountName) {
        System.out.println("  [SERVICE] Deleting account: " + accountName);
        try { Thread.sleep(40); } catch (InterruptedException e) {}
    }

    public void riskyOperation(String accountName) {
        System.out.println("  [SERVICE] Executing risky operation for: " + accountName);
        if (accountName.equals("INVALID")) {
            throw new RuntimeException("Invalid account name!");
        }
    }
}
```

**Step 3:** Create `src/main/java/com/xcelevate/ex15/TransactionService.java`:

```java
package com.xcelevate.ex15;

import org.springframework.stereotype.Service;

@Service
public class TransactionService {

    public void processTransaction(int transactionId, double amount) {
        System.out.println("  [TRANSACTION] Processing transaction ID: " + transactionId + ", Amount: $" + amount);
        try { Thread.sleep(80); } catch (InterruptedException e) {}
    }

    public void rollbackTransaction(int transactionId) {
        System.out.println("  [TRANSACTION] Rolling back transaction ID: " + transactionId);
    }
}
```

**Step 4:** Create `src/main/java/com/xcelevate/ex15/LoggingAspect.java`:

```java
package com.xcelevate.ex15;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import java.util.Arrays;

@Aspect
@Component
public class LoggingAspect {

    @Pointcut("execution(* com.xcelevate.ex15.AccountService.*(..))")
    public void accountServiceMethods() {}

    @Pointcut("execution(* com.xcelevate.ex15.TransactionService.*(..))")
    public void transactionServiceMethods() {}

    @Pointcut("accountServiceMethods() || transactionServiceMethods()")
    public void allServiceMethods() {}

    @Before("allServiceMethods()")
    public void logBefore(JoinPoint joinPoint) {
        System.out.println("\n╔═══════════════════════════════════════════╗");
        System.out.println("║ [BEFORE ADVICE]                           ║");
        System.out.println("╠═══════════════════════════════════════════╣");
        System.out.println("  Method: " + joinPoint.getSignature().getName());
        System.out.println("  Class: " + joinPoint.getTarget().getClass().getSimpleName());
        System.out.println("  Arguments: " + Arrays.toString(joinPoint.getArgs()));
        System.out.println("╚═══════════════════════════════════════════╝");
    }

    @AfterReturning(pointcut = "allServiceMethods()", returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        System.out.println("╔═══════════════════════════════════════════╗");
        System.out.println("║ [AFTER RETURNING ADVICE]                  ║");
        System.out.println("╠═══════════════════════════════════════════╣");
        System.out.println("  Method: " + joinPoint.getSignature().getName());
        System.out.println("  Return Value: " + result);
        System.out.println("  Status: ✓ SUCCESS");
        System.out.println("╚═══════════════════════════════════════════╝\n");
    }

    @AfterThrowing(pointcut = "allServiceMethods()", throwing = "error")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable error) {
        System.out.println("╔═══════════════════════════════════════════╗");
        System.out.println("║ [AFTER THROWING ADVICE]                   ║");
        System.out.println("╠═══════════════════════════════════════════╣");
        System.out.println("  Method: " + joinPoint.getSignature().getName());
        System.out.println("  Exception: " + error.getClass().getSimpleName());
        System.out.println("  Message: " + error.getMessage());
        System.out.println("  Status: ✗ FAILED");
        System.out.println("╚═══════════════════════════════════════════╝\n");
    }

    @After("allServiceMethods()")
    public void logAfter(JoinPoint joinPoint) {
        System.out.println("  [AFTER ADVICE] Cleanup for method: " + joinPoint.getSignature().getName());
    }

    @Around("execution(* com.xcelevate.ex15.AccountService.transferFunds(..))")
    public Object logAroundTransfer(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("\n╔═══════════════════════════════════════════╗");
        System.out.println("║ [AROUND ADVICE - BEFORE]                  ║");
        System.out.println("╠═══════════════════════════════════════════╣");
        System.out.println("  Method: " + joinPoint.getSignature().getName());
        System.out.println("  Starting transfer operation...");
        System.out.println("╚═══════════════════════════════════════════╝");

        long startTime = System.currentTimeMillis();

        Object result = null;
        try {
            result = joinPoint.proceed();

            long executionTime = System.currentTimeMillis() - startTime;

            System.out.println("╔═══════════════════════════════════════════╗");
            System.out.println("║ [AROUND ADVICE - AFTER]                   ║");
            System.out.println("╠═══════════════════════════════════════════╣");
            System.out.println("  Method: " + joinPoint.getSignature().getName());
            System.out.println("  Execution Time: " + executionTime + "ms");

            if (executionTime > 80) {
                System.out.println("  ⚠ WARNING: Slow operation detected!");
            } else {
                System.out.println("  ✓ Performance: Good");
            }
            System.out.println("╚═══════════════════════════════════════════╝\n");

        } catch (Exception e) {
            long executionTime = System.currentTimeMillis() - startTime;
            System.out.println("╔═══════════════════════════════════════════╗");
            System.out.println("║ [AROUND ADVICE - ERROR]                   ║");
            System.out.println("╠═══════════════════════════════════════════╣");
            System.out.println("  Method: " + joinPoint.getSignature().getName());
            System.out.println("  Execution Time: " + executionTime + "ms");
            System.out.println("  Error: " + e.getMessage());
            System.out.println("╚═══════════════════════════════════════════╝\n");
            throw e;
        }

        return result;
    }
}
```

**Step 5:** Create `src/main/java/com/xcelevate/ex15/PerformanceAspect.java`:

```java
package com.xcelevate.ex15;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class PerformanceAspect {

    @Around("execution(* com.xcelevate.ex15.*Service.*(..))")
    public Object measurePerformance(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();

        Object result = joinPoint.proceed();

        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;

        System.out.println("  [PERFORMANCE] " + joinPoint.getSignature().toShortString() + 
                          " executed in " + executionTime + "ms");

        return result;
    }
}
```

**Step 6:** Create `src/main/java/com/xcelevate/ex15/AppConfig.java`:

```java
package com.xcelevate.ex15;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy
@ComponentScan("com.xcelevate.ex15")
public class AppConfig {
}
```

**Step 7:** Create `src/main/java/com/xcelevate/ex15/MainApp.java`:

```java
package com.xcelevate.ex15;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MainApp {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        AccountService accountService = context.getBean(AccountService.class);
        TransactionService transactionService = context.getBean(TransactionService.class);

        System.out.println("═══════════════════════════════════════════════════════");
        System.out.println("    SPRING AOP WITH ASPECTJ - COMPREHENSIVE LOGGING");
        System.out.println("═══════════════════════════════════════════════════════");

        System.out.println("\n>>> TEST 1: Create Account");
        accountService.createAccount("Savings Account", 10000.00);

        System.out.println("\n>>> TEST 2: Get Balance");
        double balance = accountService.getBalance("Savings Account");
        System.out.println("  Result: Balance = $" + balance);

        System.out.println("\n>>> TEST 3: Transfer Funds (with Around Advice)");
        accountService.transferFunds("Savings", "Checking", 2000.00);

        System.out.println("\n>>> TEST 4: Delete Account");
        accountService.deleteAccount("Old Account");

        System.out.println("\n>>> TEST 5: Process Transaction");
        transactionService.processTransaction(12345, 500.00);

        System.out.println("\n>>> TEST 6: Rollback Transaction");
        transactionService.rollbackTransaction(12345);

        System.out.println("\n>>> TEST 7: Risky Operation (will throw exception)");
        try {
            accountService.riskyOperation("INVALID");
        } catch (RuntimeException e) {
            System.out.println("  Exception caught in main: " + e.getMessage());
        }

        ((AnnotationConfigApplicationContext) context).close();
    }
}
```

**Step 8:** Run and observe AspectJ-powered AOP with multiple aspects, pointcut expressions, and all advice types working together.

---

## Summary

These 15 lab exercises cover:

1. **Dependency Injection:** XML, Java Config, Annotations
2. **Bean Scopes:** Singleton, Prototype, Comparison
3. **Data Access:** JDBC, ORM (Hibernate), Spring Data JPA
4. **Bean Lifecycle:** InitializingBean, @PostConstruct, Complete lifecycle
5. **AOP:** spring-aspects and AspectJ for cross-cutting concerns

All exercises use **Spring Framework 7.0.3** with modern practices and annotations.

---

**End of Lab Exercises**
