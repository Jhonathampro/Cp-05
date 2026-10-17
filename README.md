# API de Registro Meteorológico

API REST desenvolvida com Spring Boot para cadastrar, consultar, atualizar e excluir registros meteorológicos. A aplicação utiliza MySQL para armazenamento dos dados, Flyway para gerenciamento das migrações do banco e Swagger para documentação e testes dos endpoints.

## Tecnologias utilizadas

- Java 25
- Spring Boot
- Maven
- MySQL 8.4.6
- Docker
- Docker Compose
- Flyway
- Swagger / OpenAPI

## Pré-requisitos

Antes de executar o projeto, é necessário ter o Docker Desktop instalado e em execução.

- [Docker Desktop](https://www.docker.com/products/docker-desktop/)
- Git, para clonar o repositório.

## Como executar o projeto

### 1. Clonar o repositório

Clone o repositório e acesse a pasta do projeto:

```bash
git clone https://github.com/Jhonathampro/Cp-05.git
cd Cp-05
```

### 2. Iniciar o banco de dados

Na raiz do projeto, execute:

```powershell
docker compose -f compose/docker-compose.yml up -d
```

Esse comando cria e inicia o contêiner do MySQL utilizando a configuração definida no Docker Compose.

Para verificar se o banco está em execução:

```powershell
docker ps
```

### 3. Construir a imagem da API

Na raiz do projeto, execute:

```powershell
docker build -t api-registro-meteorologico .
```

O Docker utiliza o `Dockerfile` para compilar e empacotar a aplicação e gerar a imagem necessária para executar a API.

### 4. Executar a API

A API deve ser executada em um contêiner conectado à mesma rede Docker do MySQL.

Primeiro, consulte a rede associada ao contêiner do banco:

```powershell
docker inspect mysql_846 --format "{{json .NetworkSettings.Networks}}"
```

No ambiente utilizado durante o desenvolvimento, a rede retornada foi `compose_default`.

Utilize o nome da rede retornado no comando anterior para executar a API:

```powershell
docker run -d `
  --name api-registro-meteorologico `
  --network compose_default `
  -p 8081:8080 `
  -e DB_HOST=mysqldb `
  -e DB_PORT=3306 `
  -e DB_NAME=registro_meteorologico `
  -e DB_USERNAME=root `
  -e DB_PASSWORD=root `
  api-registro-meteorologico
```

O comando configura a conexão com o banco de dados e publica a API na porta `8081` do computador.

**Importante:** se o nome da rede for diferente, substitua `compose_default` pelo nome retornado no comando de inspeção. A senha configurada é destinada ao ambiente local de desenvolvimento.

### 5. Acessar o Swagger

Com a API em execução, acesse:

http://localhost:8081/swagger-ui/index.html

O Swagger permite visualizar a documentação dos endpoints e executar requisições para testar as operações disponibilizadas pela API.

## Configuração do banco de dados

O projeto utiliza MySQL 8.4.6 com as seguintes configurações:

| Configuração | Valor |
|---|---|
| Contêiner | `mysql_846` |
| Imagem | `mysql:8.4.6` |
| Porta | `3306` |
| Usuário | `root` |
| Senha | `root` |
| Banco da aplicação | `registro_meteorologico` |

A aplicação utiliza variáveis de ambiente para configurar a conexão com o banco:

| Variável | Valor padrão |
|---|---|
| `DB_HOST` | `localhost` |
| `DB_PORT` | `3306` |
| `DB_NAME` | `registro_meteorologico` |
| `DB_USERNAME` | `root` |
| `DB_PASSWORD` | `root` |

Ao executar a API em um contêiner Docker, o endereço do banco deve ser o nome acessível do serviço na rede Docker, como `mysqldb`, e não `localhost`.

## Migrações com Flyway

O Flyway gerencia as migrações do banco de dados durante a inicialização da aplicação.

As migrações iniciais são:

- `V1__create_table_tb_registro_meteorologico.sql`: cria a tabela de registros meteorológicos.
- `V2__insert_initial_registros_meteorologicos.sql`: insere os registros iniciais.

As migrações são armazenadas em `src/main/resources/db/migration` e executadas conforme a configuração do projeto.

## Gerenciamento dos contêineres

### Verificar os contêineres em execução

```powershell
docker ps
```

Para listar também os contêineres parados:

```powershell
docker ps -a
```

### Consultar os logs da API

```powershell
docker logs api-registro-meteorologico
```

Para acompanhar os logs em tempo real:

```powershell
docker logs -f api-registro-meteorologico
```

Pressione `Ctrl + C` para sair do acompanhamento dos logs.

### Parar a API

```powershell
docker stop api-registro-meteorologico
```

### Iniciar novamente a API

```powershell
docker start api-registro-meteorologico
```

### Parar o banco de dados

```powershell
docker compose -f compose/docker-compose.yml down
```

Esse comando para e remove o contêiner gerenciado pelo Compose. Os dados armazenados no diretório local configurado como volume são preservados.

## Atualizar a aplicação

Após alterações no código-fonte, construa novamente a imagem:

```powershell
docker build -t api-registro-meteorologico .
```

Para utilizar a nova imagem, remova o contêiner anterior e crie outro:

```powershell
docker stop api-registro-meteorologico
docker rm api-registro-meteorologico
```

Depois, execute novamente o comando `docker run` da seção de execução da API.

## Observações

- O Docker Desktop deve estar em execução.
- O MySQL precisa estar disponível antes da inicialização da API.
- A API e o banco são executados em contêineres separados.
- A porta `8081` é utilizada para acessar a API no computador; a aplicação utiliza a porta interna `8080`.
- O diretório de dados do MySQL não deve ser excluído se for necessário preservar os registros.