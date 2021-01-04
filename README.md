#biblioteca

- A base de dados utilizada é o Postgres.
- O sistema roda na porta 8090.
- É necessário configurar no application.properties o usuário e senha da base de dados.
- O dump da base está na raíz do projeto, o arquivo dump.sql.
- Apenas o próprio usuário tem o poder de editar as próprias informações.
- O super usuário tem permissão para excluir qualquer usuário, editar livros, cadastrar livros e excluir livros.
- Todo usuário que for cadastrado com o nome "SUPER" terá o tipo de super usuário, independente se for marcado "Professor?" ou não.
- "email" é considerado como o login no sistema, o nome "email" foi escolhido puramente por motivos estéticos.
- O botão "Avança dia" faz a data global do sistema avançar em um dia.
- As buscas que são requisitos do trabalho são feitas no controller, bastando que o texto seja digitado no campo.


[X] crud usuario

[X] crud livros

[X] login/logout

[X] emprestar livro

[X] devolver livro

[X] multas

[X] reservar

[X] buscar livros pelo ISBN, por parte do título e pela editora.

[X] buscar usuários por parte do nome e pelo login

[X] restrições da multa

[X] listar emprestimos

[X] busca emprestimos
