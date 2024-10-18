# Trabalhando com Épicos, Histórias e Sprints no Jira usando ScriptRunner (HAPI)

O **HAPI** do ScriptRunner facilita o gerenciamento de **Épicos**, **Histórias** e **Sprints** no Jira. A seguir, você aprenderá como criar e associar épicos e histórias, definir o status de impedimento e trabalhar com sprints de maneira eficiente.

## Criando um Épico

Para criar um **Épico** em um projeto, você pode definir um sumário e o nome do épico.

#### Exemplo: Criando um Épico

```groovy
Issues.create('SSPA', 'Epic') {
    setSummary('Meu novo épico')
    setEpicName('Epic Name')
}
```

### Explicação:
- **setSummary**: Define o sumário do épico.
- **setEpicName**: Define o nome do épico que aparecerá nas visualizações ágeis.

## Criando uma História e Associando a um Épico

Você pode criar uma **História** e associá-la a um épico existente usando a referência da issue do épico ou sua chave.

#### Exemplo: Criando uma História e Associando a um Épico

```groovy
def epic = Issues.getByKey('SSPA-24')  // Recupera o épico pelo código

def story = Issues.create('SSPA', 'Story') {
    setSummary('Minha nova funcionalidade')

    // Associando à issue do épico
    setEpic(epic)

    // Ou associando pelo código do épico
    setEpic('SSPA-24')
}
```

### Explicação:
- **setEpic**: Associa a história ao épico. Você pode usar a referência de issue ou a chave da issue do épico.

## Definindo o Status de Impedimento (Flagged)

Você pode definir o status de **Flagged** para indicar que a issue está bloqueada por algum motivo.

#### Exemplo: Criando uma História com Status de Impedimento

```groovy
def story = Issues.create('SSPA', 'Story') {
    setSummary('Minha nova funcionalidade (bloqueada)')
    setCustomFieldValue('Flagged') {
        set('Impediment')
    }
}

// Alternativamente, você pode marcar/desmarcar a flag depois de criar a issue
story.flag()  // Marcar como impedida
story.unflag()  // Desmarcar
log.warn("Está bloqueada? ${story.flagged}")
```

### Explicação:
- **setCustomFieldValue('Flagged')**: Define o status de impedimento como "Impediment".
- **flag()**: Marca a história como impedida.
- **unflag()**: Remove o status de impedimento.

## Trabalhando com Sprints

### Adicionando a Issue ao Sprint Atual ou Próximo

Você pode associar uma issue ao **Sprint Atual**, ao **Próximo Sprint** ou movê-la de volta para o **Backlog**. Ao definir um sprint, o contexto de **board** é importante, pois sprints são associados a **boards**.

#### Exemplo: Associando uma Issue a um Sprint

```groovy
issue.update {
    setSprints {
        // Define o sprint ativo no board especificado
        active('Meu Board SCRUM')

        // Define o próximo sprint não iniciado no board especificado
        next('Meu Board SCRUM')

        // Move a issue para o backlog, removendo-a de qualquer sprint
        backlog()
    }
}
```

### Explicação:
- **active('Nome do Board')**: Adiciona a issue ao sprint ativo do board especificado.
- **next('Nome do Board')**: Adiciona a issue ao próximo sprint no board especificado.
- **backlog()**: Remove a issue de qualquer sprint e a move para o backlog.

### Definindo o Sprint pelo Nome ou ID

Você também pode associar uma issue a um sprint específico usando o nome ou o ID do sprint.

#### Exemplo: Definindo o Sprint pelo Nome ou ID

```groovy
issue.update {
    setSprints {
        set('Sprint 1')  // Define o sprint pelo nome

        // Adiciona a issue a múltiplos sprints
        set('Sprint 1', 'Sprint 2')

        // Define o sprint pelo ID
        set(14)

        // Ou usa objetos de Sprint
    }
}
```

### Nota sobre Sprint Names:
- **Nomes de Sprints** não são únicos e podem causar problemas se a issue estiver associada a múltiplos sprints com o mesmo nome. Se houver duplicidade de nomes, prefira usar **IDs de Sprint**.