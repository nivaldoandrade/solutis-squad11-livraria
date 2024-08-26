# Desafio - Livraria Virtual

Este projeto é uma aplicação de gerenciamento de livraria baseada em console, projetada para facilitar o cadastro de livros, 
realização de vendas e visualização de informações sobre livros e transações. O sistema oferece um menu interativo com as
seguintes funcionalidades:

1. **Cadastrar Livro**: Permite adicionar novos livros à livraria.
2. **Realizar Venda**: Registra a venda de um ou mais livros.
3. **Listar Livros**: Mostra todos os livros cadastrados, sejam eles eletrônicos ou impressos.
4. **Listar Vendas**: Exibe o histórico de todas as vendas realizadas.
5. **Sair do Programa**: Encerra a aplicação.

## Pré-requisitos

- **Java 21**: Certifique-se de ter o JDK 21 instalado.
- **MySQL**: O banco de dados MySQL deve estar em execução na porta padrão 3306.

## Configuração do Banco de Dados

O projeto usa um arquivo de configuração para conectar ao banco de dados. O arquivo `db.properties` está localizado na 
pasta `resources` e contém as seguintes propriedades:

```text
user=root
password=root
dburl=jdbc:mysql://localhost:3306/
databaseName=solutis
```

Você pode ajustar estas configurações de acordo com o seu ambiente, se necessário. No entanto, é importante manter os nomes 
das propriedades (user, password, dburl, databaseName) e a estrutura do arquivo conforme o exemplo acima, para garantir a correta 
conexão com o banco de dados.

## Rodando Localmente

### 1. Clone o Repositório

Clone o repositório para o seu ambiente local:

```bash
git clone https://github.com/nivaldoandrade/solutis-squad11-livraria.git
cd solutis-squad11-livraria
```

## 2. Configurar o Banco de Dados

Você pode usar Docker para iniciar um contêiner MySQL com o seguinte comando:

```bash
docker run --name some-mysql -p3306:3306 -e MYSQL_ROOT_PASSWORD=root -d mysql
```
Ou, se preferir, certifique-se de que o MySQL está rodando na sua máquina na porta padrão 3306.


## 3. Compilar e Executar o Sistema

1. Compile o projeto:

```bash
mvn clean install
```

2. Execute o sistema:
Navegue até o diretório onde o JAR foi gerado e execute:

```bash
java -jar target/desafio-livraria-1.0-SNAPSHOT-jar-with-dependencies.jar
```

Ou, se você estiver usando uma IDE, execute a classe principal LivrariaVirtual a partir do diretório src/main/java.