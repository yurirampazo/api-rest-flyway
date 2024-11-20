# Aplicação Java Spring Boot com MySQL, Flyway, Docker e Docker Compose

Este repositório contém uma aplicação Java Spring Boot integrada com um banco de dados MySQL e usando Flyway para migrações de banco de dados. A aplicação foi configurada para ser executada facilmente com Docker e Docker Compose.

## Requisitos

Antes de começar, verifique se você tem os seguintes programas instalados na sua máquina:

- [Docker](https://www.docker.com/get-started)
- [Docker Compose](https://docs.docker.com/compose/install/)
- [Java 17+](https://adoptopenjdk.net/) (para rodar localmente sem Docker, se necessário)
- [Maven](https://maven.apache.org/) (se for compilar o projeto localmente)

## Estrutura do Projeto

- **src/**: Contém o código-fonte da aplicação Spring Boot.
- **Dockerfile**: Arquivo de configuração para construção da imagem Docker da aplicação.
- **docker-compose.yml**: Arquivo de configuração para executar a aplicação e o banco de dados MySQL em contêineres.
- **.dockerignore**: Arquivo que lista os arquivos e pastas a serem ignorados pelo Docker ao construir a imagem.
- **flyway.conf**: Configuração do Flyway (caso use uma configuração personalizada).

## Como Rodar o Projeto

### 1. Rodar com Docker e Docker Compose

Este é o método mais simples para rodar a aplicação com todas as dependências necessárias (Spring Boot, MySQL e Flyway) em contêineres.

#### Passo 1: Certifique-se de que o Docker está instalado e em execução.

#### Passo 2: Verifique se o `docker-compose.yml` está configurado corretamente.

O arquivo `docker-compose.yml` define os serviços para o Spring Boot (`spring-app`) e MySQL (`mysql`). Também especifica as variáveis de ambiente para o banco de dados e as portas de acesso.

#### Passo 3: Build e execução

No terminal, no diretório raiz do projeto, execute o seguinte comando para construir e iniciar os contêineres:

```No bash execute:
./start.sh
````

Para compilar e rodar a aplicação localmente, use os seguintes comandos:
mvn clean install  # Para construir o projeto
mvn spring-boot:run  # Para rodar a aplicação

Se preferir pode configurar um profile diferente e executar o comando:

mvn spring-boot:run -Dspring.active.profile=seuprofile
