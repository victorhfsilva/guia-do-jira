# Issues no ScriptRunner do JIRA

Este guia detalha como realizar operações CRUD (Create, Read, Update, Delete) e outras operações relevantes em issues no JIRA utilizando o ScriptRunner com scripts Groovy.

### Introdução

O ScriptRunner é uma ferramenta poderosa para automação e personalização do JIRA. Com ele, você pode criar scripts Groovy para realizar operações CRUD em issues de maneira eficiente.

### Criação

```groovy
Issues.create('SD', 'Incident') {
    setSummary('Scriptrunner test 2406241646')
    setPriority('High')
    setDescription('Some description')
    setComponents('Sistemas Internos')
}
```

### Leitura

```groovy
Issues.getByKey("SD-282800")
```

### Leitura de parâmetros 

```groovy
def issue = Issues.getByKey("SD-282792")
issue.getCustomFieldValue("Quantidade Itens Vinculados")
```

```groovy
Issues.getByKey("SD-282792").summary
```

### Atualização

```groovy
Issues.getByKey('SD-282800').update {
    setSummary {
        append(' - extra information')
    }
    setDescription {
        prepend('h1. Title\n')
    }
}
```

### Exclusão

```groovy
Issues.getByKey('SD-282800').delete()
```

### Transicionamento

```groovy
Issues.getByKey('SD-282800').transition('Cancelado') {
    setAssignee('hsilva_victor')
    setCustomFieldValue("Motivo", "Desistência do usuário")
    setComment("Issue cancelada")
}
```

### Busca JQL

```groovy
Issues.search("project = 'SD' AND issueKey = 'SD-282800'").each { issue ->
    // do something with `issue`
    log.warn(issue.summary)
}
```