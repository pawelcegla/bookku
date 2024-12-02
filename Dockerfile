FROM eclipse-temurin:23-alpine
RUN mkdir /opt/app
COPY target/bookku-webapp-0.3.0.jar /opt/app/bookku.jar
CMD ["java", "-jar", "/opt/app/bookku.jar"]
EXPOSE 8080
