# Criar Issues com ScriptRunner (HAPI) no Jira

### Requisitos Básicos

Para começar, certifique-se de que você tem:

- **Acesso ao Jira** com permissões para criar issues no projeto desejado.
- **Permissões de administrador** ou de desenvolvedor com acesso ao ScriptRunner.
- Conhecimento básico de **Groovy** e da estrutura de dados do Jira.

### Criando uma Issue Básica

A criação de uma issue no Jira com HAPI é simples. Aqui está um exemplo básico de como criar uma issue utilizando o ScriptRunner.

```groovy
Issues.create('ABC', 'Task') {
    setSummary('Minha primeira issue com HAPI')
}
```

#### Explicação:
- `Issues.create('ABC', 'Task')`: cria uma issue no projeto 'ABC' do tipo 'Task'. Lembre-se que o tipo de issue pode variar entre instâncias, então use o ID correspondente se necessário.
- `setSummary('Minha primeira issue com HAPI')`: define o título da issue.

> Nota: A criação de issues com HAPI define automaticamente quatro campos obrigatórios: **projeto**, **tipo de issue**, **sumário** e **reporter** (reporter é definido como o usuário atual). Se seu projeto tiver campos obrigatórios adicionais, o código pode falhar a menos que você os defina.

#### Encontrando IDs de Tipos de Issue

Se você preferir trabalhar com IDs de tipos de issue, especialmente quando há customizações ou novos tipos de issues, você pode encontrar o ID correto nas configurações de administração do Jira. Siga a documentação oficial da Atlassian para encontrar os **IDs de tipos de issue**.

### Criando Issues com Mais Campos

Além dos campos obrigatórios, você pode preencher outros campos da issue, como descrição, prioridade, etiquetas, etc. 

Aqui está um exemplo de como preencher mais campos:

```groovy
Issues.create('ABC', 'Bug') {
    setSummary('Problema crítico no sistema')
    setDescription('Este bug está afetando todos os usuários no ambiente de produção.')
    setPriority('High')
    setLabels(['crítico', 'produção'])
}
```

#### Explicação:
- `setDescription`: adiciona uma descrição detalhada à issue.
- `setPriority`: define a prioridade da issue. Certifique-se de usar uma prioridade válida conforme configurado no Jira.
- `setLabels`: define etiquetas que ajudam na categorização e pesquisa da issue.

> Dica: Utilize **Ctrl + Espaço** no editor de ScriptRunner para autocompletar campos disponíveis, facilitando a identificação de todos os atributos possíveis.

### Criando Subtasks

Para criar subtasks dentro de uma issue existente, você pode usar o método `createSubTask`. Veja o exemplo abaixo:

```groovy
def issue = Issues.getByKey('ABC-1')  // Recupera a issue pela chave
issue.createSubTask('Sub-task') {
    setSummary('Subtarefa para o problema principal')
}
```

#### Explicação:
- `Issues.getByKey('ABC-1')`: recupera a issue principal pela sua chave (neste caso, 'ABC-1').
- `createSubTask('Sub-task')`: cria uma subtarefa do tipo 'Sub-task' dentro da issue principal.

### Tratamento de Campos Obrigatórios

Caso seu projeto tenha campos obrigatórios além dos padrões (projeto, tipo de issue, sumário, e reporter), você precisará defini-los no script. Campos como data de vencimento, componentes, ou outros campos personalizados devem ser preenchidos para evitar falhas ao criar a issue.

Por exemplo, se o campo "Data de Vencimento" for obrigatório:

```groovy
Issues.create('ABC', 'Task') {
    setSummary('Tarefa com data de vencimento')
    setDueDate(new Date() + 7) // Define a data de vencimento para 7 dias a partir de hoje
}
```

