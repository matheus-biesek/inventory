# Etapa de compilação usando Maven
FROM maven:3.8.4-openjdk-17 AS build

WORKDIR /app

# Copia apenas o arquivo pom.xml para baixar as dependências
COPY pom.xml .

# Baixa as dependências de compilação
RUN mvn dependency:go-offline

# Copia o código fonte
COPY src ./src

# Compila o projeto e gera o JAR
RUN mvn package -DskipTests

# Etapa de criação da imagem final
FROM openjdk:17-jdk-alpine

WORKDIR /app

# Copia o JAR construído na etapa anterior para a imagem final
COPY --from=build /app/target/inventory-0.0.1-SNAPSHOT.jar ./inventory.jar

# Expor a porta que a aplicação usa
EXPOSE 8080

# Definir a variável de ambiente para o perfil docker
ENV SPRING_PROFILES_ACTIVE=docker

# Comando para iniciar a aplicação
CMD ["java", "-jar", "inventory.jar"]
