# Usa imagem oficial OpenJDK 17
FROM openjdk:17-jdk-slim

# Cria diretório de trabalho
WORKDIR /app

# Copia o jar para dentro do container
COPY target/belezapp-0.0.1-SNAPSHOT.jar app.jar

# Expõe a porta padrão do Spring Boot
EXPOSE 8080

# Define o comando de inicialização
ENTRYPOINT ["java", "-jar", "app.jar"]
