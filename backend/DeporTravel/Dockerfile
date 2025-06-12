#Configuración Dockerfile para compilación y despliegue en Render
FROM maven:3.8.5-openjdk-17 AS build
COPY . .
RUN mvn clean package -DskipTests


FROM amazoncorretto:17-alpine
COPY --from=build /target/Proyecto-0.0.1-SNAPSHOT.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar","Proyecto-0.0.1-SNAPSHOT.jar"]