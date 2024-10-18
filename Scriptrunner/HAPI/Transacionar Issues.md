# Transições de Issues com ScriptRunner (HAPI) no Jira

Com o **HAPI** do ScriptRunner, transitar issues no Jira é um processo simples e pode ser feito diretamente a partir da console de script. Além disso, você pode atualizar campos durante a transição e até mesmo pular condições, validações e permissões quando necessário. Vamos explorar esses processos detalhadamente.

### Transição de Issues

Para transitar uma issue de um estado para outro, você pode utilizar o método `transition`. Por exemplo, para iniciar o progresso em uma issue, o script seria:

```groovy
def issue = Issues.getByKey('ABC-1')

issue.transition('Start Progress')
```

#### Explicação:
- `Issues.getByKey('ABC-1')`: recupera a issue pela chave 'ABC-1'.
- `transition('Start Progress')`: transita a issue para o estado "Iniciar Progresso" (Start Progress).

Este exemplo move a issue de um estado de backlog ou aberto para o estado de "Iniciar Progresso", o que é equivalente ao que você faria manualmente na interface do Jira.

### Atualizando Campos Durante a Transição

Além de transitar a issue, você pode atualizar certos campos simultaneamente. No exemplo abaixo, estamos resolvendo uma issue e atualizando o campo de resolução, além de adicionar um comentário:

```groovy
def issue = Issues.getByKey('ABC-1')

issue.transition('Resolve Issue') {
    setResolution('Done')
    setComment('Resolvendo esta issue')
}
```

#### Explicação:
- `transition('Resolve Issue')`: transita a issue para o estado de "Resolver Issue".
- `setResolution('Done')`: define o campo de resolução para "Concluído" (Done).
- `setComment('Resolvendo esta issue')`: adiciona um comentário à issue durante a transição.

Você pode adicionar outros campos que queira modificar durante o processo de transição, como prioridade, rótulos, etc., da mesma maneira que faria ao atualizar uma issue fora de uma transição.

### Ignorando Condições, Validações e Permissões

Em alguns casos, você pode querer ignorar condições, validações e permissões associadas a uma transição. Por exemplo, quando você deseja forçar uma transição sem passar pelos requisitos normais. Isso pode ser feito utilizando o bloco `transitionOptions`.

Aqui está como você pode pular essas verificações durante a transição:

```groovy
def issue = Issues.getByKey('ABC-1')

issue.transition('To Do') {
    transitionOptions {
        skipConditions()
        skipPermissions()
        skipValidators()
    }
}
```

#### Explicação:
- `skipConditions()`: ignora as condições associadas à transição.
- `skipPermissions()`: ignora a verificação de permissões para o usuário.
- `skipValidators()`: ignora validadores que normalmente seriam aplicados na transição.
