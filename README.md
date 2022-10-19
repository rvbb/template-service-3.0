<h2>Template Service 3.0 APIs</h2>
<br>created date: Oct 2022, by Hoang N.V hoangnv01@gmail.com

********************************************************************************************************* 
# Technology
	> Spring Web 6.0
    > Spring Boot 3.0
	> PostgreSQL
	> Spring AOP
	> Spring Data with 
	> Swagger 3
	> Openfeign & OkHttp3
    > Gradle 7.5 + JDK 18 

### Features
	* Provide simple crud 

### Configuration & data
	+ [configuration] use K8s ConfigMap

### Unit Test
	API docs:  
	    Browse APIs via swagger: http://localhost:8080/api-docs
	PostgreSQL: 
        1. Start docker: 
        ```
        docker run --name basic-postgres --rm -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=Devdev123 -e PGDATA=/var/lib/postgresql/data/pgdata -v /tmp:/var/lib/postgresql/data -p 5432:5432 -it postgres:14.1-alpine
        ```
	    [SQL DDL](resources/static/finance_info.sql)
