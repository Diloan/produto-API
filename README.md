# API de produtos
API de produtos desenvolvida em Java com Spring Boot e banco de dados PostgreSQL.
Com esta aplicação, é possível realizar operações de CRUD (Criar, Ler, Atualizar e Deletar) de produtos, bem como obter 
informações sobre os mesmos.

## Features

- CRUD de produtos
- CRUD de Pedidos junto com seus itens
- Aplicação de desconto em pedidos

## Tecnologias

- [Java](https://www.java.com/pt-BR/) - Linguagem de programação para backend, versão 17
- [Spring boot](https://spring.io/projects/spring-boot) - Framework para desenvolvimento de aplicações Java
- [Spring Data JPA](https://spring.io/projects/spring-data-jpa) - Framework para persistência de dados
- [PostgreSQL](https://www.postgresql.org/) - Banco de dados relacional
- [Maven](https://maven.apache.org/) - Gerenciador de dependências

## Instalação

### Requisitos
- Java 8 ou superior
- Maven
- Banco de dados PostgreSQL

#### 1. Clone o repositório para sua máquina local:
```sh
  git clone https://github.com/Diloan/produto-API.git
```

#### 2. Acesse o diretório da aplicação:
```sh
cd produto-api
```
#### 3. Execute o comando Maven para instalar as dependências:
```sh
mvn install
```

#### 4. Configure as informações de conexão com o banco de dados no arquivo `application.properties`.

#### 5. Execute a aplicação com o comando ou use sua IDE de preferência para executar o projeto:
```sh
mvn spring-boot:run
```

## API
A API está disponível na URL http://localhost:8010/produtos. As seguintes operações são suportadas:

### Produtos
- Criar um novo produto: `POST /produtos/adicionar`
- Obter informações de todos os produtos: `GET /produtos?pagina=0&quantidade=4&ordenacao=nome&direcao=DESC`
- Obter informações de um produto específico: `GET /produtos/{id}`
- Atualizar informações de um produto: `PATCH /produtos/{id}`
- Deletar um produto: `DELETE /produtos/{id}`

### Pedidos
- Criar um novo pedido: `POST /pedidos/adicionar`
- Obter informações de todos os pedidos: `GET /pedidos?pagina=0&quantidade=4&ordenacao=valor&direcao=DESC`
- Obter informações de um pedido específico: `GET /pedidos/{id}`
- Atualizar informações de um pedido: `PATCH /pedidos/{id}`
- Deletar um pedido: `DELETE /pedidos/{id}`
- Aplicar desconto em um pedido: `PATCH /pedidos/{id}/desconto/{desconto}`