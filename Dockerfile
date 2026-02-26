FROM eclipse-temurin:25-jre-alpine
RUN mkdir /opt/app
COPY target/zamszyk-0.15.0-SNAPSHOT.jar /opt/app/zamszyk.jar
CMD ["java", "-jar", "/opt/app/zamszyk.jar"]
EXPOSE 8080
