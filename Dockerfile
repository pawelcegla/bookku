FROM eclipse-temurin:23-alpine
RUN mkdir /opt/app
COPY target/bookku-webapp-0.2.0-SNAPSHOT.jar /opt/app/bookku.jar
CMD ["java", "-jar", "/opt/app/bookku.jar"]
EXPOSE 8080
