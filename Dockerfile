FROM openjdk:11.0.3-jdk
EXPOSE 8080
VOLUME /tmp
ARG JAR_FILE
COPY target/${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar", "-Denvironment=dev", "/app.jar"]