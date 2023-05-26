<h2>Template Service 3.0 APIs</h2>
<br>created date: Oct 2022, by Hoang N.V hoangnv01@gmail.com

********************************************************************************************************* 
### Specs
    > Use camel-case for requests and responses 
    > Provide CRUD functions
    > Provide common components: Log, API doc, Error handling, Response structure/format
    > Typical database interaction, DTO/Entity mapping, Auto-gen Immutation objects

### Technology
	> Spring Web 6.0
    > Spring Boot 3.0
	> PostgreSQL
	> Spring AOP
	> Spring Data with e
	> OpenAPI (former SwaggerUI) - current not work 24 Oct 2022
	> Spring Cloud OpenFeign & OkHttp3
    > Gradle 7.5 + JDK 18 

### Features
	* Provide simple crud 

### Configuration & data
	+ [configuration] use K8s ConfigMap

### K8s Deployment
    1. Deploy postgres:14.1-alpine as a service
    2. Deploy template service:
        + docker build -t template-service .
        + docker push local
    
### Unit Test
	> How to test CRUD API docs:  
	    1. Start Postgres: 
           docker run --name local-postgres --rm -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=Devdev123 -e PGDATA=/var/lib/postgresql/data/pgdata -v /tmp:/var/lib/postgresql/data -p 5432:5432 -it postgres:14.1-alpine
        2. Browse APIs via swagger: http://localhost:8080/api-docs
    > How to test CIF APIs (present use OpenFeign):
        1. Use Mountebank
                docker run -p 2525:2525 -d jkris/mountebank --configfile "$PWD/data/cif_apis.ejs" --allowInjection
                or start from separated project  https://github.com/rvbb/mountebank.git
        2. Start Postgres
                 docker run --name local-postgres --rm -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=Devdev123 -e PGDATA=/var/lib/postgresql/data/pgdata -v /tmp:/var/lib/postgresql/data -p 5432:5432 -it postgres:14.1-alpine
        3. Test
                a, use IDEA + Gradle tool
                b, user command ./gradlew test                        
    > Open API: http://localhost:8080/swagger-ui/index.html | http://localhost:8080/api-docs
    > How to Dockerize?
        1. docker build -t template-service .
        2. docker compose up
    
    
