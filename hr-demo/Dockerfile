FROM maven:4.0.0-rc-5-eclipse-temurin-25 AS build
WORKDIR /app

COPY pom.xml .

RUN --mount=type=cache,target=/root/.m2 \
	mvn dependency:go-offline

COPY src ./src

RUN --mount=type=cache,target=/root/.m2 \
	mvn package -DskipTests

FROM eclipse-temurin:25-jre-alpine
WORKDIR /app
COPY --from=build /app/target/app.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
