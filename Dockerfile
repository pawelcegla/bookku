FROM eclipse-temurin:24-jre-alpine
RUN mkdir /opt/app
COPY target/bookku-webapp-0.12.0-SNAPSHOT.jar /opt/app/bookku.jar
CMD ["java", "-jar", "/opt/app/bookku.jar"]
EXPOSE 8080
