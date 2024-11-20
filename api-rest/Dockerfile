# Usar uma imagem base com Java
FROM openjdk:17-jdk-slim

# Definir o diretório de trabalho no contêiner
WORKDIR /app

# Instalar dependências necessárias no contêiner
RUN apt-get update && apt-get install -y \
    git \
    bash \
    && rm -rf /var/lib/apt/lists/*

# Copiar o arquivo pom.xml
COPY pom.xml .

# Copiar o Maven Wrapper e a pasta .mvn
COPY mvnw .
COPY .mvn/ .mvn/

# Tornar o Maven Wrapper executável
RUN chmod +x mvnw

# Copiar o código-fonte do projeto
COPY src/ src/

# Construir o projeto sem rodar testes
RUN bash ./mvnw clean install -DskipTests

# Copiar o artefato gerado (após o build)
COPY target/api-rest-1.0.0.jar .

# Comando para executar o JAR gerado
CMD ["java", "-jar", "api-rest-1.0.0.jar"]
