# Trabalhar com Comentários no Jira usando ScriptRunner (HAPI)

O **HAPI** do ScriptRunner facilita o gerenciamento de comentários em issues no Jira. Você pode adicionar, atualizar, recuperar, restringir a visibilidade e excluir comentários de forma simples e eficiente. Abaixo, exploraremos como realizar essas operações.

## Adicionando Comentários

### Adicionar um Comentário a uma Issue Existente

Você pode adicionar um comentário diretamente a uma issue usando a chave da issue.

#### Exemplo: Adicionando um Comentário

```groovy
Issues.getByKey('SR-1').addComment('Este é um comentário.')
```

### Adicionar um Comentário ao Atualizar uma Issue

Ao atualizar uma issue, você pode adicionar um comentário ao mesmo tempo.

#### Exemplo: Atualizando Descrição e Adicionando um Comentário

```groovy
def issue = Issues.getByKey('SR-1')

issue.update {
    setDescription('Estou atualizando a descrição.')
    setComment('Este é um comentário.')
}
```

### Adicionar um Comentário ao Transitar uma Issue

Você também pode adicionar um comentário durante uma transição de issue. No exemplo abaixo, resolvemos a issue e adicionamos um comentário:

#### Exemplo: Resolvendo uma Issue com Comentário

```groovy
def issue = Issues.getByKey('ABC-1')

issue.transition('Resolve Issue') {
    setResolution('Done')
    setComment('Resolvendo esta issue.')
}
```

## Atualizando Comentários

Você pode atualizar o corpo de um comentário já existente, além de modificar as restrições de visibilidade.

#### Exemplo: Atualizando o Corpo de um Comentário e Definindo Restrições

```groovy
def comment = Issues.getByKey('SR-1').addComment('Comentário original.')

comment.update {
    setBody('Comentário atualizado.')
    setGroupRestriction('jira-administrators')
}
```

## Recuperando Comentários

Para recuperar todos os comentários de uma issue, você pode usar o método `comments`.

#### Exemplo: Recuperando Comentários de uma Issue

```groovy
def issue = Issues.getByKey('SR-1')
def comments = issue.comments
comments.each { comment ->
    log.warn("Comentário: ${comment.body}")
}
```

### Ignorando Permissões ao Recuperar Comentários

Caso o usuário atual não tenha permissão para visualizar todos os comentários, você pode usar o método `getCommentsOverrideSecurity()` para ignorar as permissões.

#### Exemplo: Recuperando Comentários Ignorando Permissões

```groovy
def issue = Issues.getByKey('SR-1')
issue.addComment('Adicionando um comentário.')

def comments = issue.getCommentsOverrideSecurity()
comments.each { comment ->
    log.warn("Comentário: ${comment.body}")
}
```

## Definindo Restrições de Visibilidade

### Restringindo a um Grupo

Você pode restringir a visualização de um comentário para um grupo específico.

#### Exemplo: Comentário Restrito ao Grupo "jira-administrators"

```groovy
def issue = Issues.getByKey('SR-1')

issue.update {
    setComment('Este comentário é restrito.') {
        setGroupRestriction('jira-administrators')
    }
}
```

### Restringindo a um Project Role

Você também pode restringir a visualização de um comentário a uma Project Role, como "Administradores".

#### Exemplo: Comentário Restrito à Função "Administradores"

```groovy
def issue = Issues.getByKey('SR-1')

issue.update {
    setComment('Este comentário é restrito a administradores.') {
        setProjectRoleRestriction('Administrators')
    }
}
```

## Excluindo Comentários

Você pode excluir um comentário usando o método `delete()`.

#### Exemplo: Excluindo um Comentário

```groovy
def comment = Issues.getByKey('SR-1').addComment('Este é um comentário para ser excluído.')
comment.delete()
```

### Ignorando o Despacho de Eventos ao Excluir Comentários

Por padrão, um evento de exclusão de comentário é disparado. Se você quiser evitar esse evento, pode usar a opção abaixo:

```groovy
def comment = Issues.getByKey('SR-1').addComment('Comentário a ser excluído sem evento.')
comment.delete { dispatchEvent = false }
```

### Excluindo Comentários Ignorando Permissões

Se o usuário atual não tiver permissão para excluir o comentário, você pode usar o método `deleteOverrideSecurity()`.

#### Exemplo: Excluindo Comentários Ignorando Permissões

```groovy
def comment = Issues.getByKey('SR-1').addComment('Comentário a ser excluído ignorando permissões.')
comment.deleteOverrideSecurity()
```