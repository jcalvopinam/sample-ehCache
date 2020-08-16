ehCache Sample
---

This is a sample of ehCache, I used the following technologies:
* Spring Boot 2.3.2.RELEASE
* Spring JPA
* H2
* ehCache
*jUnit5

How to run?
---

1. Compile the project with the following command:

   ```mvn clean install```

2. The project is a Spring Boot Application, so you can run inside of your ide or from terminal with the following command:

   ```mvn spring-boot:run```

3. When starting the application, it will create the `task` table defined in the entity class and also it will insert 
5000 rows of dummy data.

* The first time, when invoking the `/sample-ehcache/task/all` endpoint, the application will take approximately 800ms.
* If you run again the `/sample-ehcache/task/all` endpoint, it will take little time because the data was cached.


Rest endpoints
---

* **Request:** Get all Tasks
  
  ```
  url    : http://localhost:8080/sample-ehcache/tasks/all
  method : GET
  ```

* **Request:** Add new Task
  
  ```
  url    : http://localhost:8080/sample-ehcache/tasks/save
  method : PUT
  header : application/json
  body   : {
              "taskName":"example",
              "status":"To do"
           }
  ```

* **Request:** Modify a Task
  
  ```
  url    : http://localhost:8080/sample-ehcache/tasks/update
  method : POST
  header : application/json
  body   : {
              "id":5001,
              "taskName":"example",
              "status":"In progress"
           }
  ```

* **Request:** Delete a Task
  
  ```
  url    : http://localhost:8080/sample-ehcache/tasks/{taskId}/delete
  method : DELETE
  ```

* **Request:** Delete the Cache
  
  ```
  url    : http://localhost:8080/sample-ehcache/tasks/clear-cache
  method : DELETE
  ```
  
Or you can import the collection file on _"postman"_ client to test the endpoints, the file is in:

  ```/resources/endpoints/collection.json```

How to connect to H2 database?
---

1. Go to `http://localhost:8080/sample-ehcache/h2-console/`
2. Setting the following parameters:
```
Driver class : org.h2.Driver
JDBC URL     : jdbc:h2:mem:test
User Name    : sa
Password     :
```
3. Click on `Test Connection` button, this should return `Test successful`
4. Click on `Connect` button.
5. Now you can see the `TASK` table.