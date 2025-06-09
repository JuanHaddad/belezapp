# Etapa 1 - build da aplicação
FROM maven:3.9.6-eclipse-temurin-17 AS build

WORKDIR /app

# Copia pom.xml e resolve dependências primeiro (melhora cache de build)
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copia o restante do código
COPY src ./src

# Build do JAR (pula testes para ser mais rápido)
RUN mvn clean package -DskipTests

# Etapa 2 - imagem final
FROM openjdk:17-jdk-slim

WORKDIR /app

# Copia o JAR gerado da etapa de build
COPY --from=build /app/target/belezapp-0.0.1-SNAPSHOT.jar app.jar

# Expõe a porta do Spring Boot
EXPOSE 8080

# Comando de inicialização
ENTRYPOINT ["java", "-jar", "app.jar"]
