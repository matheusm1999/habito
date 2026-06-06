Como usuário, eu quero registrar um novo hábito
para que eu possa acompanhar e iniciar uma rotina diária.

Descrição:
Suporte ao armazenamento, validação e recuperação de hábitos criados pelo usuário.

Critérios de aceite (Back-end):
- O sistema salva um hábito com nome, descrição, frequência e meta associados ao usuário.
- Validações do lado do servidor rejeitam nomes vazios.
- O hábito criado é retornado em listagens do usuário.
- Erros são comunicados de forma clara para o front-end.

Contexto:
Serviço responsável por persistência, validação e consistência dos dados de hábitos.