FROM maven:4.0.0-rc-5-eclipse-temurin-25-alpine AS build
WORKDIR /app

COPY pom.xml .

RUN --mount=type=cache,id=maven-repo,target=/root/.m2 \
	mvn dependency:go-offline

COPY src ./src

RUN --mount=type=cache,id=maven-repo,target=/root/.m2 \
	mvn package -DskipTests

RUN java -Djarmode=tools -jar target/app.jar extract --layers --launcher --destination extracted

RUN jdeps --ignore-missing-deps -q \
	--recursive \
	--multi-release 25 \
	--print-module-deps \
	--class-path 'extracted/dependencies/BOOT-INF/lib/*' \
	extracted/application/BOOT-INF/classes > deps.info

RUN jlink \
	--add-modules $(cat deps.info) \
	--strip-debug \
	--compress 2 \
	--no-header-files \
	--no-man-pages \
	--output /jre

FROM alpine:3.24
WORKDIR /app

ENV JAVA_HOME=/usr/java/jdk25
ENV PATH=$JAVA_HOME/bin:$PATH

RUN addgroup -g 1000 -S app && adduser -u 1000 -S app -G app

COPY --from=build --chown=app:app /jre $JAVA_HOME
COPY --from=build --chown=app:app /app/extracted/dependencies/ ./
COPY --from=build --chown=app:app /app/extracted/spring-boot-loader/ ./
COPY --from=build --chown=app:app /app/extracted/snapshot-dependencies/ ./
COPY --from=build --chown=app:app /app/extracted/application/ ./
RUN mkdir -p /app/logs && chown -R app:app /app/logs

USER app
EXPOSE 8080
EXPOSE 9090
HEALTHCHECK --interval=30s --timeout=3s --start-period=20s \
	CMD wget -qO- http://localhost:9090/actuator/health || exit 1

ENTRYPOINT ["java", "org.springframework.boot.loader.launch.JarLauncher"]
