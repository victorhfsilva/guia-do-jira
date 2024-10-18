
# Atualizar Campos com ScriptRunner (HAPI) no Jira

O processo de atualização de campos pode ser feito de várias formas, desde o uso de nomes de campo, IDs de campo até a manipulação de valores subjacentes, como opções e versões. Também abordaremos como atualizar campos de texto, datas, opções e como ajustar valores existentes.

### Atualizando Campos Personalizados (Custom Fields)

Os campos personalizados podem ser atualizados usando o nome legível do campo ou o ID numérico. Para encontrar o ID de um campo personalizado, observe a parte numérica do ID de string do campo. Por exemplo, se o ID for `customfield_12345`, o ID numérico será `12345`.

#### Exemplo: Usando o Nome do Campo

```groovy
issue.update {
    setCustomFieldValue('Meu campo de texto', 'Novo valor')
}
```

#### Exemplo: Usando o ID do Campo

```groovy
issue.update {
    setCustomFieldValue(10104, 'Novo valor pelo ID')
}
```

### Lendo Valores de Campos Personalizados

Para obter o valor de um campo personalizado, você pode usar o atalho `getCustomFieldValue()`.

#### Exemplo: Usando o Nome do Campo

```groovy
def value = issue.getCustomFieldValue('Meu campo de texto')
log.warn(value)
```

#### Exemplo: Usando o ID do Campo

```groovy
def value = issue.getCustomFieldValue(10146)
log.warn(value)
```

> Nota: Caso o campo tenha sido atualizado recentemente, use `issue.refresh()` para garantir que o valor retornado seja o mais recente.

### Atualizando Campos de Texto

Campos de texto, como descrição, sumário ou campos de texto curtos e longos, podem ser atualizados facilmente. Também é possível manipular o conteúdo desses campos (adicionar, substituir ou remover partes do texto).

#### Exemplo: Atualizando Texto Simples

```groovy
issue.update {
    setCustomFieldValue('Meu campo de texto', 'Novo conteúdo')
}
```

#### Exemplo: Manipulando o Conteúdo de um Campo de Descrição

```groovy
issue.update {
    setDescription {
        set('Descrição inicial do sistema')
        append(' - adicionando mais detalhes')
        prepend('Informações importantes: ')
        replace('sistema', 'aplicação')
    }
}
```

### Atualizando Campos de Data

Campos de data, como **Data de Vencimento** (due date) ou campos personalizados de data, podem ser definidos usando vários formatos, como strings, timestamps ou objetos **LocalDate**.

#### Exemplo: Definindo uma Data de Vencimento

```groovy
issue.update {
    setDueDate('21/Out/2024')
}
```

#### Exemplo: Definindo uma Data Usando um Objeto `LocalDate`

```groovy
issue.update {
    setDueDate {
        set(LocalDate.now().plusDays(7))  // Define a data para 7 dias a partir de hoje
    }
}
```

#### Exemplo: Ajustando uma Data Existente

```groovy
issue.update {
    setDueDate {
        set(get().plusDays(7))  // Adiciona 7 dias à data de vencimento atual
    }
}
```

### Atualizando Campos de Opções, Versões e Componentes

Campos de seleção (selects), botões de rádio e caixas de seleção podem ser atualizados tanto pelo nome da opção quanto pelo ID da opção.

#### Exemplo: Definindo uma Única Opção

```groovy
issue.update {
    setCustomFieldValue('Meus botões de rádio', 'Sim')
}
```

#### Exemplo: Definindo Múltiplas Opções (Caixas de Seleção)

```groovy
issue.update {
    setCustomFieldValue('Minhas caixas de seleção', 'Sim', 'Talvez')
}
```

#### Exemplo: Adicionando uma Opção a um Campo Existente

```groovy
issue.update {
    setCustomFieldValue('Minhas caixas de seleção') {
        add('Talvez')
    }
    setFixVersions {
        add('v2.0')
    }
}
```

#### Exemplo: Removendo e Substituindo Opções

```groovy
issue.update {
    setCustomFieldValue('Minhas caixas de seleção') {
        remove('Não')
        replace('Talvez', 'Sim')
    }
}
```

### Atualizando Campos de Usuário

Você pode definir campos de usuário, como o **assignee** (responsável), utilizando métodos especiais para desatribuir ou atribuir automaticamente um usuário.

#### Exemplo: Definindo o Assignee

```groovy
issue.update {
    setAssignee {
        unassigned()  // Desatribui o usuário
        automatic()   // Atribui automaticamente o responsável
    }
}
```

###  Atualizando Campos em Massa Usando JQL

Você pode atualizar vários campos em múltiplas issues utilizando uma consulta JQL.

#### Exemplo: Atualizando o Campo de Descrição em Múltiplas Issues

```groovy
Issues.search('project = ABC and description ~ Marathon').each { issue ->
    issue.update {
        setDescription {
            replace('Marathon', 'Snickers')
        }
    }
}
```
