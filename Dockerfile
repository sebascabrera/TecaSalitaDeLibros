FROM gradle:8.2.1-alpine

COPY . .

RUN gradle build

EXPOSE 8080

ENTRYPOINT ["java", "-jar","build/libs/salita-0.0.1-SNAPSHOT.jar"]