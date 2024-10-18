# Atualizar Issues com ScriptRunner (HAPI) no Jira

A atualização de issues é equivalente à ação "Editar Issue" na interface gráfica do usuário, porém de forma automatizada e programática. 

### Atualizando Campos de uma Issue

Para atualizar uma issue, você pode usar o método `update`. Este exemplo mostra como atualizar o sumário e a descrição de uma issue existente.

```groovy
def issue = Issues.getByKey('ABC-1')

issue.update {
    setSummary('Resumo atualizado')
    setDescription('Olá *mundo*')
}
```

#### Explicação:
- `Issues.getByKey('ABC-1')`: recupera a issue pela chave 'ABC-1'.
- `update`: inicia o processo de atualização da issue. Dentro do bloco, você pode definir os campos que deseja alterar.

### Atualizando Múltiplos Campos

Com o HAPI, você pode atualizar vários campos ao mesmo tempo, como no exemplo abaixo:

```groovy
issue.update {
    setSummary('Novo resumo')
    setDescription('Descrição atualizada')
    setPriority('High')
    setLabels(['importante', 'urgente'])
}
```

Neste exemplo, além de atualizar o sumário e a descrição, também alteramos a prioridade e adicionamos etiquetas.

###  Evitando Notificações e Despacho de Eventos

Ao atualizar múltiplas issues em lote ou em casos em que não deseja enviar notificações aos usuários, você pode evitar que eventos sejam disparados e e-mails sejam enviados.

Aqui está um exemplo de como desativar esses eventos:

```groovy
import com.atlassian.jira.event.type.EventDispatchOption

issue.update {
    setSummary('Novo resumo sem notificação')
    setEventDispatchOption(EventDispatchOption.DO_NOT_DISPATCH)
    setSendEmail(false)
}
```

#### Explicação:
- `setEventDispatchOption(EventDispatchOption.DO_NOT_DISPATCH)`: impede que o evento de atualização seja disparado.
- `setSendEmail(false)`: desativa o envio de e-mails de notificação.

### Trabalhando com Post-Functions

Quando você está lidando com uma transição de issue, como em uma post-function de workflow, é mais adequado utilizar o método `set()` em vez de `update()`. A função `set()` modifica a issue apenas na memória durante a execução da post-function, sem gravar as alterações diretamente no banco de dados até que a transação seja concluída.

Aqui está um exemplo de como usar o `set()` em uma post-function para alterar a versão de correção (fix-version):

```groovy
issue.set {
    setFixVersions {
        add('v2.0')
    }
}
```

#### Explicação:
- `issue.set`: define os campos da issue na memória, sem gravar diretamente no banco de dados.
- `setFixVersions`: adiciona a versão de correção "v2.0" à issue.

> **Nota**: Usar `set()` em vez de `update()` é importante durante a execução de uma post-function, pois evita que uma atualização redundante seja enviada para o banco de dados ou que transações incorretas sejam iniciadas.
