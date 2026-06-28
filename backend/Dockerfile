FROM eclipse-temurin:21-jdk-jammy AS builder

WORKDIR /build

COPY gradlew build.gradle.kts settings.gradle.kts ./
COPY gradle ./gradle

RUN chmod +x gradlew

RUN ./gradlew build -x test --no-daemon || true

COPY src ./src

RUN ./gradlew build --no-daemon

FROM eclipse-temurin:21-jre-jammy

RUN apt-get update && \
    apt-get install -y --no-install-recommends curl && \
    rm -rf /var/lib/apt/lists/*
RUN groupadd -r appuser && \
    useradd -r -g appuser -d /app appuser

WORKDIR /app

COPY --from=builder /build/build/libs/*.jar ./app.jar

USER appuser

EXPOSE 8080

HEALTHCHECK CMD curl -f http://localhost:8080/actuator/health || exit 1

ENV JAVA_OPTS="-XX:+UseContainerSupport \
-XX:MaxRAMPercentage=75.0 \
-XX:+UseG1GC \
-XX:+ExitOnOutOfMemoryError"

ENTRYPOINT ["sh", "-c", "exec java $JAVA_OPTS -jar app.jar"]