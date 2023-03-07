FROM openjdk:11 as TEMP_BUILD_IMAGE

# 실행시 필수 파일 복사
COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .
COPY src src

RUN chmod +x ./gradlew

RUN ./gradlew bootJar

FROM openjdk:11

COPY --from=TEMP_BUILD_IMAGE build/libs/*.jar ghbt_app.jar

EXPOSE 5000

ENTRYPOINT ["java","-jar","/ghbt_app.jar"]