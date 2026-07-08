# ---- Stage 1: build the jar ----
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app

# Copy only the pom first: dependency downloads get their own cache layer,
# so editing source code doesn't force re-downloading every dependency.
COPY pom.xml .
RUN mvn dependency:go-offline -B

COPY src ./src
RUN mvn package -DskipTests -B

# ---- Stage 2: minimal runtime image ----
FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
