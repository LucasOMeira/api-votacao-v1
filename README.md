# API de Votação

## Descrição
Esta aplicação é uma API RESTful para gerenciar um sistema de votação. A API permite CRUD de candidatos, eleitores, cargos e sessões, bem como operações de votação e geração de boletim de urna.

## Tecnologias Utilizadas
- Java 17
- Spring Boot
- Spring Data JPA
- Spring Web
- Hibernate
- Lombok
- H2 Database (para testes)
- MySQL (ou outro banco de dados relacional)
- Liquibase (para versionamento de banco de dados)
- Cache com Spring Cache
- JUnit e Mockito (para testes unitários)

## Requisitos
- JDK 17
- Maven
- Banco de dados MySQL (ou qualquer banco de dados relacional de sua escolha)

## Configuração do Banco de Dados
Configure as propriedades do banco de dados no arquivo `src/main/resources/application.properties`:

### properties
spring.datasource.url=jdbc:mysql://localhost:3306/sua_base_de_dados
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver


### **Como Rodar a Aplicação**
- Clone o Repositório:
git clone https://github.com/seu-usuario/api-votacao.git
cd api-votacao
- Compile e rode a aplicação:
mvn spring-boot:run
- A aplicação estará disponível em http://localhost:8080

### **Endpoints da API**

## Candidatos: 
- GET /candidatos: Lista todos os candidatos.
- GET /candidatos/{id}: Retorna um candidato específico.
- POST /candidatos: Cria um novo candidato.
- PUT /candidatos/{id}: Atualiza um candidato existente.
- DELETE /candidatos/{id}: Deleta um candidato (se não tiver votos).

## Eleitores:
- GET /eleitores: Lista todos os eleitores.
- GET /eleitores/{id}: Retorna um eleitor específico.
- POST /eleitores: Cria um novo eleitor.
- PUT /eleitores/{id}: Atualiza um eleitor existente.
- DELETE /eleitores/{id}: Deleta um eleitor.

## Sessões:
- POST /abrir-sessao: Abre uma nova sessão de votação.
- PATCH /fechar-sessao/{id}: Fecha uma sessão de votação.

## Votação:
- POST /eleitores/{id}/votar: Registra um voto.

## Boletim de Urna:
- GET /boletim-urna/{idSessao}: Gera um boletim de urna para uma sessão específica.

### Tratamento de Erros
- Eleitor não pode votar duas vezes: O eleitor não pode registrar mais de um voto na mesma sessão.
- Impossível votar com sessão fechada: Não é possível registrar votos em uma sessão que já foi encerrada.
- Impossível deletar candidato com votos: Não é possível deletar um candidato que já tenha votos registrados.
- Impossível emitir boletim de urna com sessão ainda aberta: Não é possível gerar o boletim de urna para uma sessão que ainda está aberta.

### Testes 
## Os testes unitários são escritos usando JUnit e Mockito. Para rodar os testes, use o comando:
mvn test



