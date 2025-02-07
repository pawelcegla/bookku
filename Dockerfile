FROM eclipse-temurin:23-jre-alpine
RUN mkdir /opt/app
COPY target/bookku-webapp-0.5.0.jar /opt/app/bookku.jar
CMD ["java", "-jar", "/opt/app/bookku.jar"]
EXPOSE 8080
