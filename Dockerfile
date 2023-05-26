FROM openjdk:18-jdk-alpine3.14
ENV DB_HOST=pgdb
ENV DB_PWD=Devdev123
EXPOSE $PORT
ENV APP_HOME=/usr/app/
WORKDIR $APP_HOME
COPY ./build/libs/*.jar ./app.jar
CMD ["java","-jar","app.jar"]