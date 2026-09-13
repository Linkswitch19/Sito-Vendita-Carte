FROM arm64v8/maven as builder
ENTRYPOINT ["/usr/bin/mvn"]
RUN mkdir -p /usr/src/app
WORKDIR /usr/src/app

COPY pom.xml /usr/src/app
COPY src /usr/src/app/src
RUN mvn -T 1C install -DskipTests=true


FROM arm64v8/amazoncorretto:21 as runner
COPY --from=builder /usr/src/app/target/*.jar /app.jar
ENTRYPOINT ["java","-jar", "/app.jar"]
