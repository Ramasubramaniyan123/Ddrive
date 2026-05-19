# Notes for My Bank Project

## Create a new MyBank App 
- New repo with name <yourName>-mybank-spring
- Followed Layered Code ex: App > Service > Repository 
- Structure of the Project
  - some package 
        - MainApp.java
          - AppConfig.java
          - service
            - ABCService.java
          - repositories
            - UserRepository.java
            - AccountRepository.java
          - entities
            - User.java
            - Account.java
            - Transaction.java
          - exception
            - InvalidAccountException.java
            - xyz
  - resources
    - application.properties
      - db.jdbc.url
      - db.user.name
      - db.user.password
      - db.connection.maxSize
      - db.connection.minIdle
      - db.driver.classname
      - ...
    
      
  

- Use Dependency Inject for all objects service


## Properites Loading Option
@PropertySource("file:C:/MyConfigs/application.properties")
@PropertySource("file:${config.location}/application.properties")

java -Dconfig.location="C:\Users\User\Desktop\My Configs" -jar my-app.jar


## Spring Initializer
https://start.spring.io/

