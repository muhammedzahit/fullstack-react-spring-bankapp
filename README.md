# Section 2 : Initializing Relationships in Database

![relationships](./drawio/relationships.png)

In this section, we will initialize the relationships in the database. I will use postgreSQL as the database for this project. You can use any database of your choice. 

```yml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/postgres
    username: postgres
    password: 1597532000
  jpa:
    hibernate:
      ddl-auto: create-drop
    show-sql: false
    properties:
      hibernate:
        format_sql: true
    database-platform: org.hibernate.dialect.PostgreSQLDialect
```

In the above code, I have configured the database connection. I have used the `create-drop` option for the `ddl-auto` property. This will create the database tables when the application starts and drop the tables when the application stops. You can use `update` option if you want to update the tables when the application starts. 

If change value to `create` then it will create the tables when the application starts and will not drop the tables when the application stops. You can use this option if you initialize the database automatically rather than manually in pgAdmin. And then you can remove this option.

We created two folders in the `com.example.demo` package. One is `entities` and the other is `repositories`. In the `entities` folder, we will create the model classes and in the `repositories` folder, we will create the repository interfaces. 

```java
public class User{
    ........

    @ManyToOne
    @JoinColumn(name = "responsible_id")
    private Employee responsible;

    @OneToMany(mappedBy = "user")
    private List<SavingAccount> savingAccounts;

    @OneToMany(mappedBy = "user")
    private List<CreditAccount> creditAccounts;

}
```

In the above code, we have created two relationships. One is `ManyToOne` and the other is `OneToMany`. The `ManyToOne` relationship is between the `User` and `Employee` entities. The `OneToMany` relationship is between the `User` and `SavingAccount` entities and between the `User` and `CreditAccount` entities. And notice that we give Employee object name as `responsible`. We will use that name in the `Employee` entity. 

```java

public class Employee{
    ......

    @ManyToOne
    @JoinColumn(name = "director_id")
    private Director director;

    @OneToMany(mappedBy = "responsible")
    private List<User> users;


}
```

If you look OneToMany relationship in this class. You will notice we used the tag `mappedBy = "responsible"`. This means that the `responsible` is the name of Employee object in the `User` entity. 

And the other classes are as follows.
