# ---- Étape 1 : build avec Maven ----
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app

# Copie du pom.xml d'abord pour profiter du cache Docker sur les dépendances
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copie du reste du code source et build
COPY src ./src
RUN mvn clean package -DskipTests -B

# ---- Étape 2 : image finale légère (uniquement le JRE) ----
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Copie uniquement le .jar généré à l'étape précédente
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]