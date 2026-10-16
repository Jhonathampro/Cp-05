# API de Registro Meteorológico

## Sobre o projeto

Esta aplicação consiste em uma API REST desenvolvida com Spring Boot para gerenciar registros meteorológicos. Ela permite armazenar informações sobre estações meteorológicas, datas de registro, temperatura, umidade relativa do ar, precipitação e condições climáticas.

A aplicação utiliza MySQL para persistência dos dados, Flyway para controlar as migrações do banco de dados e Swagger/OpenAPI para documentar e testar os endpoints.

## Tecnologias utilizadas

- Java 25
- Spring Boot
- Maven
- MySQL 8.4.6
- Docker
- Docker Compose
- Flyway
- Swagger/OpenAPI

---

## 1. Preparação do ambiente

Antes de iniciar a aplicação, certifique-se de que os seguintes programas estão instalados e funcionando:

- Docker Desktop
- Java 25

Se estiver utilizando o laboratório da FIAP, verifique se os serviços necessários estão ativos.

Abra o PowerShell na pasta raiz do projeto.

---

## 2. Inicialização do banco de dados

O MySQL é executado em um contêiner gerenciado pelo Docker Compose.

Acesse a pasta que contém o arquivo `docker-compose.yml`:

```powershell
cd compose
```

Inicie o contêiner do MySQL:

```powershell
docker compose up -d
```

Verifique se o serviço está em execução:

```powershell
docker compose ps
```

Para visualizar os contêineres ativos no Docker:

```powershell
docker ps
```

### Parar o banco de dados

Para interromper o serviço:

```powershell
docker compose down
```

**Observação:** os dados do MySQL são armazenados em uma pasta local configurada no Docker Compose. Ao utilizar `docker compose down`, os dados persistidos no volume não são removidos.

---

## 3. Configuração da conexão com o banco

A aplicação utiliza o perfil `dev`, configurado no arquivo `application-dev.properties`.

As configurações padrão da conexão são:

| Variável | Valor padrão |
|---|---|
| `DB_HOST` | `localhost` |
| `DB_PORT` | `3306` |
| `DB_NAME` | `registro_meteorologico` |
| `DB_USERNAME` | `root` |
| `DB_PASSWORD` | `root` |

A URL de conexão utiliza o parâmetro `createDatabaseIfNotExist=true`, que permite criar o banco caso ele ainda não exista, desde que o usuário tenha permissão para isso.

Após estabelecer a conexão, o Flyway executa as migrações SQL disponíveis no diretório:

```text
src/main/resources/db/migration
```

Dessa forma, a estrutura do banco é criada e os scripts de migração são controlados por versão.

---

## 4. Execução da API pelo Maven

Com o banco de dados em execução, volte para a pasta raiz do projeto:

```powershell
cd ..
```

Caso já esteja na raiz, não é necessário executar esse comando.

Inicie a aplicação utilizando o Maven Wrapper:

```powershell
.\mvnw.cmd spring-boot:run
```

Aguarde até que o Spring Boot informe que a aplicação foi iniciada com sucesso.

### Gerar o arquivo JAR

Para compilar o projeto e gerar o arquivo JAR sem executar os testes:

```powershell
.\mvnw.cmd clean package -DskipTests
```

O arquivo gerado ficará no diretório `target`.

**Importante:** a API utiliza a porta `8080` por padrão. Se outro serviço estiver utilizando essa porta, será necessário liberar a porta ou configurar outra para a aplicação.

---

## 5. Construção da imagem Docker da API

O projeto possui um `Dockerfile` para construir a imagem da aplicação.

Na pasta raiz, onde está localizado o arquivo `Dockerfile`, execute:

```powershell
docker build -t api-registro-meteorologico .
```

Para verificar se a imagem foi criada:

```powershell
docker images
```

O Dockerfile utiliza uma etapa de construção com Maven e uma imagem Java para executar o arquivo JAR gerado.

**Observação:** nesta configuração, o MySQL é iniciado pelo Docker Compose, enquanto a API pode ser executada diretamente pelo Maven. Construir a imagem da API não inicia automaticamente um contêiner para ela.

---

## 6. Gerenciamento dos contêineres

### Listar contêineres em execução

```powershell
docker ps
```

### Listar todos os contêineres

Incluindo os que estão parados:

```powershell
docker ps -a
```

### Iniciar um contêiner parado

Para iniciar o contêiner do MySQL:

```powershell
docker start mysql_846
```

### Parar um contêiner

```powershell
docker stop mysql_846
```

### Acessar o MySQL pelo terminal

Para abrir o cliente MySQL dentro do contêiner:

```powershell
docker exec -it mysql_846 mysql -u root -p
```

Digite a senha configurada no Docker Compose quando solicitado.

### Remover um contêiner

Primeiro, interrompa o contêiner:

```powershell
docker stop mysql_846
```

Depois, remova-o:

```powershell
docker rm mysql_846
```

**Atenção:** remover um contêiner não é o mesmo que remover a imagem. Os dados persistidos dependem do volume configurado no Docker Compose.

---

## 7. Acesso ao Swagger/OpenAPI

Com a API em execução, abra o navegador e acesse:

**Swagger UI:**

http://localhost:8080/swagger-ui/index.html

O Swagger permite visualizar a documentação dos endpoints e testar as requisições diretamente pelo navegador.

### Como testar um endpoint

1. Localize o endpoint desejado.
2. Expanda a seção correspondente.
3. Clique em **Try it out**.
4. Preencha os parâmetros ou o corpo da requisição.
5. Clique em **Execute**.
6. Confira o código HTTP e os dados retornados pela API.

---

## 8. Migrações do banco com Flyway

As migrações SQL estão localizadas em:

```text
src/main/resources/db/migration
```

Os scripts versionados são responsáveis por definir as alterações iniciais do banco e executar as operações de dados previstas no projeto.

O Flyway mantém o controle das migrações já aplicadas, permitindo acompanhar a versão do esquema do banco.

A configuração `spring.jpa.hibernate.ddl-auto=none` impede que o Hibernate altere automaticamente a estrutura das tabelas, deixando essa responsabilidade para as migrações.

---

## Considerações finais

O projeto integra uma API REST desenvolvida com Spring Boot, persistência de dados em MySQL, controle de migrações com Flyway e documentação interativa por meio do Swagger/OpenAPI.

O Docker Compose facilita a inicialização do banco de dados, enquanto o Maven Wrapper permite executar e compilar a aplicação. Essa estrutura reúne os principais recursos necessários para registrar e consultar informações meteorológicas por meio de uma API.
