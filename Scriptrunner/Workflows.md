## Workflow Functions no Jira: Scripts para Customizar Transições de Workflow

### Introdução

Workflow functions são scripts que você pode usar para personalizar transições de workflow no Jira. Com o ScriptRunner, é possível criar condições, validações e pós-funções para controlar e automatizar o comportamento das transições de workflow.

### Exemplos de Scripts para Workflow Functions

#### Condição: Permitir Transição Apenas se o Usuário Pertence a um Grupo Específico

Este script verifica se o usuário que está tentando fazer a transição pertence a um grupo específico.

```groovy
import com.atlassian.jira.component.ComponentAccessor

def currentUser = ComponentAccessor.jiraAuthenticationContext.loggedInUser
def groupManager = ComponentAccessor.groupManager

// Nome do grupo
def groupName = "jira-administrators"

// Verifica se o usuário pertence ao grupo
if (!groupManager.isUserInGroup(currentUser, groupName)) {
    return false
}

return true
```

#### Validação: Impedir Transição se um Campo Obrigatório Estiver Vazio

Este script impede a transição se um campo personalizado obrigatório estiver vazio.

```groovy
import com.atlassian.jira.component.ComponentAccessor

def customFieldManager = ComponentAccessor.customFieldManager
def issueManager = ComponentAccessor.issueManager

// Nome do campo personalizado
def customFieldName = "Campo Obrigatório"

def customField = customFieldManager.getCustomFieldObjectByName(customFieldName)
def fieldValue = issue.getCustomFieldValue(customField)

if (!fieldValue) {
    invalidInputException = new InvalidInputException("O campo '$customFieldName' é obrigatório e não pode estar vazio.")
}
```

#### Pós-Função: Atualizar um Campo Personalizado Após a Transição

Este script atualiza um campo personalizado após a transição de uma issue.

```groovy
import com.atlassian.jira.component.ComponentAccessor

def customFieldManager = ComponentAccessor.customFieldManager
def issueManager = ComponentAccessor.issueManager
def user = ComponentAccessor.jiraAuthenticationContext.loggedInUser

// Nome do campo personalizado e novo valor
def customFieldName = "Campo Atualizável"
def newValue = "Novo Valor"

// Obtém o campo personalizado
def customField = customFieldManager.getCustomFieldObjectByName(customFieldName)

// Atualiza o campo personalizado
issue.setCustomFieldValue(customField, newValue)
issueManager.updateIssue(user, issue, EventDispatchOption.ISSUE_UPDATED, false)
```

#### Pós-Função: Enviar Notificação por Email Após a Transição

Este script envia uma notificação por email sempre que uma issue é movida para um status específico.

```groovy
import com.atlassian.jira.component.ComponentAccessor
import com.atlassian.mail.server.SMTPMailServer
import com.atlassian.mail.Email

def issue = transientVars.issue
def mailServer = ComponentAccessor.mailServerManager.defaultSMTPMailServer

if (!mailServer) {
    log.warn("No SMTP mail server defined!")
    return
}

def emailAddress = "example@example.com" // Endereço de email do destinatário
def subject = "Issue Movida para Novo Status: ${issue.key}"
def body = """\
Olá,

A issue ${issue.key} foi movida para o status ${issue.status.name}.

Resumo: ${issue.summary}
Descrição: ${issue.description}

Atenciosamente,
Sua Equipe Jira
"""

def email = new Email(emailAddress)
email.setSubject(subject)
email.setBody(body)

mailServer.send(email)
```

#### Validação: Impedir Transição se a Issue Estiver com Status Bloqueado

Este script impede a transição se a issue estiver com um status que indica bloqueio.

```groovy
import com.atlassian.jira.component.ComponentAccessor

// Status que indica bloqueio
def blockedStatus = "Blocked"

if (issue.status.name == blockedStatus) {
    invalidInputException = new InvalidInputException("A transição não pode ser realizada porque a issue está bloqueada.")
}
```

#### Conclusão

As workflow functions permitem um alto grau de personalização e automação nos fluxos de trabalho do Jira. Utilizando scripts Groovy com o ScriptRunner, você pode implementar condições, validações e pós-funções para atender às necessidades específicas de seus processos de negócios, aumentando a eficiência e a precisão das operações do Jira.