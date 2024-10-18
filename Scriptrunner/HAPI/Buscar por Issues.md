# Pesquisar Issues com ScriptRunner (HAPI) no Jira

O HAPI simplifica a execução de consultas JQL (Jira Query Language), permitindo não apenas encontrar issues, mas também realizar ações em lote, como atualizações em massa.

## Executando Consultas JQL para Pesquisar Issues

O HAPI facilita a execução de consultas JQL diretamente no seu script. Por exemplo, para obter todas as tarefas (Tasks) do projeto ABC, você pode usar o seguinte script:

```groovy
Issues.search("project = 'ABC' AND issuetype = 'Task'").each { issue ->
    // Faça algo com cada issue
    log.warn(issue.key)
}
```

### Explicação:

- `Issues.search("project = 'ABC' AND issuetype = 'Task'")`: executa uma consulta JQL que retorna todas as issues do tipo 'Task' no projeto 'ABC'.
- `.each { issue -> ... }`: itera por cada issue retornada pela consulta.

> **Nota:** O método `Issues.search` retorna um **Iterator**, projetado para que você percorra os resultados sem carregar todas as issues na memória de uma só vez, evitando problemas de desempenho.

## Atualizando Todas as Issues Retornadas

Você pode realizar atualizações em massa nas issues retornadas pela consulta. Por exemplo, para adicionar um comentário e alterar a prioridade de todas as tarefas de baixa prioridade no projeto ABC:

```groovy
Issues.search("project = 'ABC' AND issuetype = 'Task' AND priority = Low").each { issue ->
    issue.update {
        setComment('Esta tarefa precisa de atenção')
        setPriority('High')
    }
    log.warn(issue.key)
}
```

### Explicação:

- A consulta JQL agora inclui `AND priority = Low` para filtrar apenas as tarefas de baixa prioridade.
- Dentro do bloco `issue.update { ... }`, estamos adicionando um comentário e alterando a prioridade para 'High'.

## Considerações sobre Memória e Performance

É importante evitar executar consultas que retornem um número ilimitado de issues sem paginação, pois isso pode consumir muita memória. O HAPI lida com isso buscando as issues em lotes, mantendo o uso de memória constante e prevenindo erros de falta de memória.

Se você precisar de uma lista de issues, limite o tamanho da lista:

```groovy
def issuesList = Issues.search('project = ABC').take(1000).toList()
```

- `take(1000)`: limita a lista a 1000 issues.
- `.toList()`: converte o resultado em uma lista.

> **Importante:** Embora seja aceitável trabalhar com listas de até mil issues, evitar trabalhar com milhões de issues em uma única lista para não sobrecarregar a memória.

## Contando Issues com Eficiência

Se você apenas precisa do número de issues que correspondem a uma consulta, use o método `count`:

```groovy
def issueCount = Issues.count('project = ABC')
```

Este método é mais eficiente do que buscar todas as issues apenas para contá-las.

## Permissões e Segurança em Consultas JQL

As permissões do usuário atual são respeitadas ao executar consultas JQL. Isso significa que o script retornará o mesmo conjunto de issues que o usuário veria ao executar a consulta no Jira.

Se você precisar retornar issues independentemente das permissões, pode usar os métodos `searchOverrideSecurity` e `countOverrideSecurity`:

```groovy
Issues.searchOverrideSecurity('project = ABC').take(1000).toList()
```

ou

```groovy
def issueCount = Issues.countOverrideSecurity('project = ABC')
```

> **Atenção:** Usar `OverrideSecurity` pode expor informações que o usuário atual não deveria acessar. Use com cautela e somente se tiver certeza das implicações de segurança.

## Verificando se uma Issue Corresponde a uma Consulta

Você pode verificar se uma issue específica corresponde a uma consulta JQL usando o método `matches`:

```groovy
def issue = Issues.getByKey('ABC-1')
if (issue.matches('project = ABC')) {
    // A issue corresponde à consulta
}
```

Se quiser realizar essa verificação ignorando as permissões do usuário, use `matchesOverrideSecurity`:

```groovy
if (issue.matchesOverrideSecurity('project = ABC')) {
    // A issue corresponde à consulta, independentemente das permissões
}
```

## Uso Avançado de JQL com HAPI

Em alguns casos, você pode precisar filtrar a lista de issues além do que é possível expressar em JQL. Você pode usar métodos do Groovy para isso.

### Exemplo: Filtrando Issues Atribuídas ao Usuário 'admin'

Embora isso possa ser feito diretamente em JQL, vamos usar um exemplo para demonstrar:

```groovy
Issues.search('project = ABC').findAll { issue ->
    issue.assignee?.name == 'admin'
}.each { issue ->
    // Faça algo com a issue
}
```

### Consideração sobre o Uso de `findAll`

O método `findAll` do Groovy coleta todas as issues que correspondem ao critério, o que pode não ser eficiente em instâncias com um grande número de issues. Para melhorar a eficiência, especialmente quando você precisa limitar o número de issues processadas, utilize a API de Streams do Java.

### Exemplo: Limitando o Número de Issues Processadas

```groovy
import java.util.stream.Collectors

def issues = Issues.search('project = ABC')
    .stream()
    .filter { issue -> issue.assignee?.name == 'admin' }
    .limit(10)
    .collect(Collectors.toList())

issues.each { issue ->
    // Faça algo com a issue
}
```

### Explicação:

- `.stream()`: obtém um fluxo de issues para processamento.
- `.filter { ... }`: aplica um filtro às issues.
- `.limit(10)`: limita o resultado a 10 issues.
- `.collect(Collectors.toList())`: coleta o resultado em uma lista.

Este método interrompe a busca e o processamento de issues assim que encontra as 10 primeiras que correspondem ao critério, tornando-o mais eficiente.
