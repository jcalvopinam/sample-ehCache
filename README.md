ehCache Sample
---

This is a sample of ehCache, I used the following technologies:
* Spring Boot 1.5.2.RELEASE
* Spring JPA
* H2
* ehCache

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
  url    : http://localhost:8080/sample-ehcache/task/all
  method : GET
  ```

* **Request:** Add new Task
  
  ```
  url    : http://localhost:8080/sample-ehcache/task/save
  method : PUT
  header : application/json
  body   : {
              "taskName":"example",
              "status":"To do"
           }
  ```

* **Request:** Modify a Task
  
  ```
  url    : http://localhost:8080/sample-ehcache/task/update
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
  url    : http://localhost:8080/sample-ehcache/task/{taskId}/delete
  method : DELETE
  ```

* **Request:** Delete the Cache
  
  ```
  url    : http://localhost:8080/sample-ehcache/task/clear-cache
  method : DELETE
  ```
  
Or you can import the collection file on _"postman"_ client to test the endpoints, the file is in:

  ```/resources/endpoints/collection.json```
