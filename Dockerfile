# --- ETAPA 1: BUILD (Compilação) ---
# Usamos uma imagem que já tem o Maven instalado
FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app

COPY . .

# Roda o comando do Maven para gerar o .jar (pula os testes para ser mais rápido)
RUN mvn clean package -DskipTests

# --- ETAPA 2: RUN (Execução) ---
# Usamos uma imagem leve apenas com o Java para rodar
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app

# Copia o .jar gerado na Etapa 1 para cá
COPY --from=build /app/target/*.jar app.jar

# Expõe a porta e roda
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]