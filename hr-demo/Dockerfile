FROM maven:4.0.0-rc-5-eclipse-temurin-25-alpine AS build
WORKDIR /app

COPY pom.xml .

RUN --mount=type=cache,target=/root/.m2 \
	mvn dependency:go-offline

COPY src ./src

RUN --mount=type=cache,target=/root/.m2 \
	mvn package -DskipTests

RUN jar xf target/app.jar

RUN jdeps --ignore-missing-deps -q  \
	--recursive  \
	--multi-release 25  \
	--print-module-deps  \
	--class-path 'BOOT-INF/lib/*'  \
	target/app.jar > deps.info

RUN jlink \
	--add-modules $(cat deps.info) \
	--strip-debug \
	--compress 2 \
	--no-header-files \
	--no-man-pages \
	--output /jre

FROM alpine:3.24
WORKDIR /app
ENV JAVA_HOME=/user/java/jdk25
ENV PATH=$JAVA_HOME/bin:$PATH
COPY --from=build /jre $JAVA_HOME
COPY --from=build /app/target/app.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
