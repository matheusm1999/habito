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

### Visão Geral

Permitir que o usuário autenticado cadastre um novo hábito vinculado à sua conta, informando nome, descrição opcional, meta numérica e dias da semana de execução. O backend deve persistir o registro, validar o payload e retornar o hábito completo imediatamente após a criação.

### Componentes Impactados

- HabitController
- HabitService
- HabitRepository
- HabitEntity
- HabitFrequencyEntity ou estrutura equivalente para persistência dos dias da semana
- HabitRequestDTO
- HabitResponseDTO
- GlobalExceptionHandler
- validações Jakarta ou customizadas relacionadas a habit

### Fluxo de Negócio

1. Usuário autenticado envia requisição de criação de hábito.
2. Controller recebe o payload e dispara a validação de entrada.
3. Service associa o hábito ao usuário autenticado obtido do contexto de segurança.
4. Service valida regras de negócio, incluindo nome não vazio, meta inteira válida e ao menos um dia da semana.
5. Repository persiste o hábito e seus dias de frequência.
6. Service converte a entidade persistida em resposta completa.
7. API retorna o hábito recém-criado com seus dados de auditoria.

### Modelo de Dados

Tabela principal:
- habit

Campos:
- id
- user_id
- name
- description
- goal
- created_at
- updated_at

Tabela de frequência:
- habit_frequency

Campos:
- id
- habit_id
- day_of_week

Observações:
- `goal` deve ser armazenado como inteiro.
- `day_of_week` deve usar um valor canônico por dia da semana, preferencialmente um enum persistido como string.
- A associação entre `habit` e `habit_frequency` deve ser 1:N.

### API

Endpoint:
POST /api/habits

Request:

{
  "name": "Academia",
  "description": "Treino de força",
  "goal": 5,
  "frequencyDays": ["MONDAY", "WEDNESDAY", "FRIDAY"]
}

Response:

{
  "id": 1,
  "name": "Academia",
  "description": "Treino de força",
  "goal": 5,
  "frequencyDays": ["MONDAY", "WEDNESDAY", "FRIDAY"],
  "createdAt": "2026-06-08T22:30:00",
  "updatedAt": "2026-06-08T22:30:00"
}

Status possíveis:

- 201
- 400
- 401
- 403

### Validações

- `name` é obrigatório e não pode ser vazio ou composto apenas por espaços.
- `goal` é obrigatório e deve ser inteiro positivo.
- `frequencyDays` é obrigatório e deve conter ao menos um dia da semana.
- Cada item de `frequencyDays` deve ser um valor válido do domínio de dias da semana.
- `description` é opcional.
- O hábito deve ser vinculado ao usuário autenticado, sem aceitar `userId` no payload.
- Erros de validação devem retornar `400 Bad Request` com detalhes por campo.

### Segurança

- O endpoint deve exigir autenticação.
- O hábito deve ser sempre associado ao usuário identificado no token/contexto de segurança.
- Não é necessária autorização por papel nesta história.
- Um usuário não pode acessar ou criar hábito em nome de outro usuário.

### Testes Recomendados

- Criar hábito com payload válido e usuário autenticado.
- Rejeitar criação com `name` vazio.
- Rejeitar criação com `goal` ausente, nulo ou não inteiro.
- Rejeitar criação com `frequencyDays` vazio.
- Rejeitar criação com dia da semana inválido.
- Confirmar que `description` pode ser omitida.
- Confirmar que o hábito criado retorna `id`, auditoria e frequência persistida.
- Confirmar que a associação é feita ao usuário autenticado e não a um `userId` enviado no corpo.

### Riscos e Observações

- A persistência da frequência em tabela separada aumenta a consistência, mas exige mapper e consultas adicionais.
- Como nomes duplicados são permitidos até dentro da mesma conta, a UI precisará usar `id` como referência principal.
- A validação de dias da semana precisa manter alinhamento com o enum/contrato do frontend para evitar divergência de valores.
- O retorno completo da criação deve refletir exatamente o que foi persistido para evitar inconsistência entre API e banco.
