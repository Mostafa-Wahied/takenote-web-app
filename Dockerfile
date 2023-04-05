############### this version was working fine with render! ###############
# Build stage
#
FROM maven:3.8.7 AS build
COPY . .
RUN mvn clean package -Pprod -DskipTests

#
# Package stage
#
FROM openjdk:18
COPY --from=build /target/takenote-web-app.jar takenote-docker.jar
# ENV PORT=8080
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "takenote-docker.jar"]

# we have to edit the application.properties file to use the connect docker with the database like so:
# spring.datasource.url=jdbc:mysql://host.docker.internal:3306/takenotecopy?useSSL=false&serverTimezone=UTC&useLegacyDatetimeCode=false
# then we need to build the docker image
# run in terminal in the root folder 'docker build -t takenote-docker:latest .'
# then we can run the docker image
# run in terminal 'docker run -p 9090:8080 -v D:\M\'My files'\Java\takenoteCOPY\src\main\resources\application.properties:/application.properties takenote-docker .'
# then we can go to localhost:9090 and see the app running

# to run the docker image in the background
# run in terminal 'docker run -d -p 9090:8080 -v D:\M\'My files'\Java\takenoteCOPY\src\main\resources\application.properties:/application.properties takenote-docker .'
# to stop the docker image
# run in terminal 'docker stop <container id>'
# to see the logs of the docker image