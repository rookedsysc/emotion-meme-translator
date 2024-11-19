# 빌드 스테이지
FROM gradle:7.2-jdk17 AS build
WORKDIR /app
COPY . .
RUN ./gradlew build --no-daemon -x test

# 실행 스테이지
FROM openjdk:17-jdk-alpine
WORKDIR /app
COPY --from=build /app/build/libs/emotion-meme-translator-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
