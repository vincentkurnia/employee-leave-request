FROM eclipse-temurin:21

USER 0

WORKDIR /app

COPY build/libs/employee-leave-request-0.1.jar ./app.jar

RUN chown -R 1000:0 .

USER 1000

ENTRYPOINT ["java", "-jar", "app.jar"]