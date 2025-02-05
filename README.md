# Microsserviços com Java, Spring Boot, Spring Cloud e Docker

Este projeto foi desenvolvido com o objetivo de demonstrar a construção de uma arquitetura de microsserviços utilizando Java, Spring e Docker.

## Objetivo do Projeto
O sistema é composto por diversos microsserviços que simulam um ecossistema de gestão de trabalhadores e pagamentos. Cada serviço desempenha uma função específica e comunica-se com outros serviços por meio de tecnologias como Feign e Eureka, exemplificando boas práticas de desenvolvimento e integração em microsserviços.

Utilizei o Docker na implantação e execução dos microsserviços, garantindo isolamento, padronização e escalabilidade. Com ele, cada serviço é conteinerizado com todas as suas dependências, permitindo execução consistente em qualquer ambiente. O Docker Compose facilita a inicialização dos serviços com um único comando, enquanto a rede interna do Docker melhora a comunicação entre os microsserviços. O banco de dados PostgreSQL também é executado via container, eliminando configurações manuais.


## Tecnologias Utilizadas

- Java 11
- Spring Boot 2.3.4
- Spring Cloud
- Spring Security e JWT
- Feign Client para comunicação entre microsserviços
- Ribbon para balanceamento de carga
- Eureka Server para registro e descoberta de serviços
- Hystrix para tolerância a falhas
- Zuul API Gateway
- Config Server para configuração centralizada
- Banco de Dados PostgreSQL e H2
- Docker para conteinerização

## Estrutura do Projeto

O projeto segue uma arquitetura baseada em microsserviços organizada da seguinte maneira:

- **hr-config-server**: Servidor de configuração centralizada.
- **hr-eureka-server**: Serviço de descoberta de microsserviços.
- **hr-worker**: Microsserviço para gestão de trabalhadores.
- **hr-payroll**: Microsserviço para cálculo de folha de pagamento.
- **hr-user**: Microsserviço de gestão de usuários e permissões.
- **hr-oauth**: Microsserviço de autenticação com OAuth e JWT.
- **hr-api-gateway-zuul**: API Gateway com Zuul para roteamento e segurança.

## Como Executar o Projeto

### Configurar o ambiente:
1. Instalar JDK 11
2. Configurar variáveis JAVA_HOME e PATH
3. Configurar credenciais do GitHub para o Config Server
   - O **Config Server** acessa a conta do GitHub e o caminho do repositório onde estão todos os arquivos de configuração dos ambientes de desenvolvimento e produção. 
   - **Observação**: Nunca exponha suas credenciais diretamente no código. Use variáveis de ambiente ou arquivos de configuração para manter as credenciais seguras.

### Subir os microsserviços (na seguinte ordem):

```sh
mvnw spring-boot:run -pl hr-config-server
mvnw spring-boot:run -pl hr-eureka-server
mvnw spring-boot:run -pl hr-worker
mvnw spring-boot:run -pl hr-payroll
mvnw spring-boot:run -pl hr-user
mvnw spring-boot:run -pl hr-oauth
mvnw spring-boot:run -pl hr-api-gateway-zuul
```

### Acessar os serviços:

- **Config Server**: http://localhost:8888
- **Eureka Server**: http://localhost:8761
- **API Gateway**: http://localhost:8765

## Banco de Dados

### Configuração PostgreSQL no Docker

Para rodar o banco de dados localmente com Docker, substitua os dados sensíveis (como senhas e usuários) por variáveis de ambiente:

```sh
docker network create hr-net

docker run -p 5432:5432 --name hr-worker-pg12 --network hr-net     -e POSTGRES_PASSWORD=<SUA_SENHA>     -e POSTGRES_DB=db_hr_worker postgres:12-alpine

docker run -p 5432:5432 --name hr-user-pg12 --network hr-net     -e POSTGRES_PASSWORD=<SUA_SENHA>     -e POSTGRES_DB=db_hr_user postgres:12-alpine
```

## Autenticação com JWT

O microsserviço **hr-oauth** é responsável pela autenticação de usuários e geração de tokens JWT.

Para obter um token, faça uma requisição **POST**:

```sh
curl -X POST "http://localhost:8765/hr-oauth/oauth/token"      -H "Content-Type: application/x-www-form-urlencoded"      -d "grant_type=password&username=<SEU_USUARIO>&password=<SUA_SENHA>"
```

Isso retorna um token JWT que pode ser usado para acessar os demais microsserviços.

## Implantação com Docker

### Criar e rodar os containers Docker para cada microsserviço

#### Criar a rede Docker:
```sh
docker network create hr-net
```

#### Construir e rodar os containers:

```sh
mvnw clean package -DskipTests

docker build -t hr-config-server:v1 ./hr-config-server
docker run -p 8888:8888 --name hr-config-server --network hr-net     -e GITHUB_USER=<SEU_USUARIO> -e GITHUB_PASS=<SUA_SENHA> hr-config-server:v1
```

Repita para os demais microsserviços, ajustando os nomes e portas conforme necessário.

## Conclusão

Este projeto apresenta uma arquitetura completa de microsserviços com Java e Spring Boot, destacando conceitos como comunicação entre serviços, autenticação segura, balanceamento de carga e implantação com Docker.


