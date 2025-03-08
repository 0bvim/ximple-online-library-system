## Architecture Overview
The application is divided into three layers: API, Service, and Data Access. 
The API layer handles HTTP requests and responses, the Service layer contains the business logic,
and the Data Access layer interacts with the database.
```mermaid
flowchart TD
    classDef api fill:#92D050,color:#000,stroke:#92D050
    classDef service fill:#0072C6,color:#fff,stroke:#0072C6
    classDef data fill:#B4A0FF,color:#000,stroke:#B4A0FF
    classDef infra fill:#FFC000,color:#000,stroke:#FFC000

    subgraph API["API Layer"]
        Controller[BookController<br/><i>Handles HTTP requests</i>]:::api
        Error[GlobalErrorController<br/><i>Manages exceptions</i>]:::api
    end

    subgraph Service["Service Layer"]
        BookSvc[BookService<br/><i>Business logic</i>]:::service
        ReservationSvc[ReservationService<br/><i>Booking management</i>]:::service
        ReviewSvc[ReviewService<br/><i>Rating system</i>]:::service
        UserSvc[UserService<br/><i>User management</i>]:::service
    end

    subgraph Data["Data Access Layer"]
        Repo[BookRepository<br/><i>JDBC operations</i>]:::data
        DB[(PostgreSQL<br/>Database)]:::infra
    end

    Controller --> BookSvc
    Controller --> ReservationSvc
    Controller --> ReviewSvc
    Controller --> UserSvc
    Error -.->|Handles Errors| Controller
    BookSvc --> Repo
    ReservationSvc --> Repo
    ReviewSvc --> Repo
    UserSvc --> Repo
    Repo --> DB
```

## Infrastructure
```mermaid
flowchart TD
    classDef infra fill:#FFC000,color:#000,stroke:#FFC000

    subgraph Infrastructure["Infrastructure Layer"]
        APIC[API Container<br/><i>Runs API services</i>]:::infra
        DBContainer[PostgreSQL Container<br/><i>Runs database</i>]:::infra
    end

    APIC --> DBContainer
    DBContainer -->|Persistent Storage| Volume[(Database Volume)]:::infra

```

## Database Schema
```mermaid
erDiagram
    BOOK ||--o{ RESERVATION : "has"
    BOOK ||--o{ REVIEW : "has"
    USER ||--o{ RESERVATION : "makes"
    USER ||--o{ REVIEW : "writes"
    BOOK {
        string id PK
        string title
        string author
        string isbn
        string genre
    }
    USER {
        string id PK
        string username
        string email
    }
    RESERVATION {
        string id PK
        string book_id FK
        string user_id FK
        date reservation_date
    }
    REVIEW {
        string id PK
        string book_id FK
        string user_id FK
        int rating
        date created_at
        date updated_at
    }
```

## Swagger API Documentation
- http://localhost:8080/swagger-ui/index.html


## References

- [Spring and Postgres Docker](https://www.youtube.com/watch?v=_Gdb-jK3Sr4)
- [Response Entity](https://www.baeldung.com/spring-response-entity)
- [Exception Handling](https://www.baeldung.com/exception-handling-for-rest-with-spring) 
- [Enumerated Types](https://stackoverflow.com/questions/67825729/using-enums-in-a-spring-entity/67826028#67826028)
- [Working With Records](https://www.youtube.com/watch?v=gJ9DYC-jswo)
- [Jason Young](https://www.youtube.com/watch?v=eC5X0NEZ8hE)
- [Pessimistic Locking](https://www.youtube.com/watch?v=0xHdv7LKu1Q)
- [Building Web Application](https://www.youtube.com/watch?v=31KTdfRH6nY)
- [OpenApi Documentation](https://www.youtube.com/watch?v=wtYAqS1GcHE)
- [OpenApi Video](https://youtu.be/2o_3hjUPAfQ?si=pyaFNUmky3oaKJ_5)
- [Devtiro Event-drive Arch with Java](https://youtu.be/HYBtWRPikgo?si=A5nDv7Mby5C96-MD) || I tried... ||
- [Alex Hyett](https://youtu.be/gOuAqRaDdHA?si=2CsFYplJ3Ejfoxe-)
- [Maven vs Gradle](https://youtu.be/5P9cb0xWyO0?si=hxN-yvt9NNcC91tT)
- [Maven Docs](https://maven.apache.org/guides/)
- [Springboot Guide](https://spring.io/guides/gs/spring-boot)
- [Springboot Initalizr](https://start.spring.io/)
- [Github Kafka tutorial](https://github.com/devtiro/microservices-kafka-tutorial) || A...gain ||
- [Github Book Management](https://github.com/beatrizdile/ximple-bookservice)

## Used AI:
- [Claude](https://claude.ai/login)
- [ChatGPT](https://chatgpt.com/)
- [Gemini](https://gemini.google.com/)