# ---------- build stage ----------
FROM eclipse-temurin:17-jdk-jammy AS build
WORKDIR /workspace

# 1) Копируем gradle wrapper и файлы сборки (для кэша зависимостей)
COPY gradlew .
COPY gradle gradle
COPY build.gradle settings.gradle ./
RUN chmod +x gradlew
RUN ./gradlew --no-daemon dependencies

# 2) Копируем исходники и собираем bootJar
COPY src src
RUN ./gradlew --no-daemon clean bootJar

# ---------- runtime stage ----------
FROM eclipse-temurin:17-jre-jammy AS runtime
WORKDIR /app

# curl для healthcheck (минимально)
RUN apt-get update \
  && apt-get install -y --no-install-recommends curl \
  && rm -rf /var/lib/apt/lists/*

# non-root user
RUN useradd -r -u 1001 appuser
USER appuser

# По ТЗ: JAR из build/libs
COPY --from=build /workspace/build/libs/*.jar /app/app.jar

EXPOSE 9889

ENV JAVA_OPTS="-XX:MaxRAMPercentage=75 -Djava.security.egd=file:/dev/./urandom"

HEALTHCHECK --interval=10s --timeout=3s --start-period=20s --retries=3 \
  CMD curl -fsS http://localhost:9889/actuator/health || exit 1

ENTRYPOINT ["sh","-c","java $JAVA_OPTS -jar /app/app.jar"]