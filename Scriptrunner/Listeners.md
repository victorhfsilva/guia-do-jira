# Listeners no Jira: Scripts que Respondem a Eventos

### Introdução

Listeners são scripts que são executados automaticamente em resposta a eventos específicos no Jira, como a criação de issues ou transições de workflow. Utilizando o ScriptRunner, você pode criar listeners para automatizar tarefas e integrar o Jira com outros sistemas.

### Exemplos de Scripts para Listeners

#### Listener para Criação de Issues

Este script envia uma notificação personalizada por e-mail sempre que uma nova issue é criada.

```groovy
import com.atlassian.jira.component.ComponentAccessor
import com.atlassian.mail.server.SMTPMailServer
import com.atlassian.mail.Email

// Configurações do email
def mailServer = ComponentAccessor.mailServerManager.defaultSMTPMailServer
if (!mailServer) {
    log.warn("No SMTP mail server defined!")
    return
}

def emailAddress = "example@example.com" // Endereço de email do destinatário
def subject = "Nova Issue Criada: ${issue.key}"
def body = """\
Olá,

Uma nova issue foi criada no Jira:

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

#### Listener para Transições de Workflow

Este script adiciona um comentário automaticamente sempre que uma issue é movida para o status "Em Progresso".

```groovy
import com.atlassian.jira.component.ComponentAccessor
import com.atlassian.jira.issue.comments.CommentManager

// Verifica se a transição foi para "Em Progresso"
def status = issue.getStatus()
if (status.name == "In Progress") {
    def commentManager = ComponentAccessor.commentManager
    def user = ComponentAccessor.jiraAuthenticationContext.loggedInUser
    
    def commentBody = "A issue foi movida para o status 'Em Progresso'."
    commentManager.create(issue, user, commentBody, true)
}
```

#### Listener para Atualizações de Issues

Este script atualiza um campo personalizado sempre que uma issue é atualizada.

```groovy
import com.atlassian.jira.component.ComponentAccessor

def customFieldManager = ComponentAccessor.customFieldManager
def issueManager = ComponentAccessor.issueManager
def user = ComponentAccessor.jiraAuthenticationContext.loggedInUser

def customField = customFieldManager.getCustomFieldObjectByName("Meu Campo Personalizado")
def newValue = "Valor Atualizado"

// Atualiza o campo personalizado
issue.setCustomFieldValue(customField, newValue)
issueManager.updateIssue(user, issue, EventDispatchOption.ISSUE_UPDATED, false)
```

#### Listener para Deleção de Issues

Este script registra um log sempre que uma issue é deletada.

```groovy
import com.atlassian.jira.component.ComponentAccessor

// Loga a deleção da issue
log.info("A issue ${issue.key} foi deletada pelo usuário ${issueEvent.user.displayName}.")
```

#### Conclusão

Listeners em Jira, utilizando ScriptRunner e Groovy, permitem automatizar e personalizar ações em resposta a eventos específicos. Esses scripts são poderosos para integrar sistemas, notificar equipes, atualizar campos e muito mais, aumentando a eficiência e a automação no gerenciamento de projetos.