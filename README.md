# API de Registro Meteorológico

API para gerenciamento de registros meteorológicos, desenvolvida como parte de um projeto acadêmico da FIAP.

## Como executar o projeto

### 1. Iniciar o banco de dados

Na raiz do projeto, execute:

```powershell
docker compose -f compose/docker-compose.yml up -d
```

Esse comando inicia o container do banco de dados.

**Importante:** execute os próximos passos somente após o banco estar pronto.

### 2. Construir a imagem da API

Na raiz do projeto, execute:

```powershell
docker build -t api-registro-meteorologico .
```

Esse comando constrói a imagem da aplicação e realiza o empacotamento necessário durante o processo de build.

### 3. Executar a API

Primeiro, verifique o nome da rede criada pelo Docker Compose:

```powershell
docker inspect mysql_846 --format "{{json .NetworkSettings.Networks}}"
```

Identifique o nome da rede no resultado. No comando abaixo, `compose_default` é o nome utilizado no ambiente de desenvolvimento; se o resultado apresentar outro nome, substitua-o.

Execute:

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

### 4. Acessar a documentação da API

Com o container em execução, acesse a documentação interativa pelo Swagger:

[http://localhost:8081/swagger-ui/index.html](http://localhost:8081/swagger-ui/index.html)

Por meio do Swagger, é possível visualizar os endpoints disponíveis e realizar requisições para testar a API.

## Comandos úteis

Verificar os containers em execução:

```powershell
docker ps
```

Consultar os logs da API:

```powershell
docker logs api-registro-meteorologico
```

Acompanhar os logs em tempo real:

```powershell
docker logs -f api-registro-meteorologico
```

Parar a API:

```powershell
docker stop api-registro-meteorologico
```

Iniciar novamente a API:

```powershell
docker start api-registro-meteorologico
```

Parar o banco de dados:

```powershell
docker compose -f compose/docker-compose.yml down
```

## Observações

- O Docker Desktop precisa estar em execução.
- O banco de dados deve estar disponível antes da inicialização da API.
- A API utiliza a porta `8081` no computador e a porta `8080` dentro do container.
- Para executar novamente o `docker run` após a criação inicial, remova o container anterior ou utilize `docker start` para iniciá-lo novamente.
- Os arquivos locais do banco de dados devem ser preservados para evitar a perda dos dados persistidos.

---

**Desenvolvido por Jhonatham Jesus — FIAP**