# Jobs no Jira: Scripts para Tarefas Agendadas com ScriptRunner

### Introdução
Jobs no Jira permitem executar scripts automaticamente em intervalos regulares, utilizando o ScriptRunner. Essa funcionalidade é útil para tarefas de manutenção, relatórios periódicos, automações e integrações que precisam ser realizadas em horários ou frequências específicas.

### Exemplos de Scripts para Jobs

#### Job para Limpar Comentários Antigos

Este script remove comentários que são mais antigos que um ano em todas as issues de um projeto específico.

```groovy
import com.atlassian.jira.component.ComponentAccessor
import com.atlassian.jira.issue.comments.CommentManager
import com.atlassian.jira.issue.IssueManager

def projectKey = "PROJETO"
def retentionPeriod = 365 // Em dias

def issueManager = ComponentAccessor.issueManager
def commentManager = ComponentAccessor.commentManager
def project = ComponentAccessor.projectManager.getProjectObjByKey(projectKey)

if (!project) {
    log.warn("Projeto não encontrado: $projectKey")
    return
}

def now = new Date()
def issues = issueManager.getIssueObjects(issueManager.getIssueIdsForProject(project.id))

issues.each { issue ->
    def comments = commentManager.getComments(issue)
    comments.each { comment ->
        def commentAge = (now.time - comment.created.time) / (1000 * 60 * 60 * 24)
        if (commentAge > retentionPeriod) {
            commentManager.delete(comment)
            log.info("Comentário deletado na issue ${issue.key}")
        }
    }
}
```

#### Job para Gerar Relatório de Issues Atrasadas

Este script gera um relatório de todas as issues que estão atrasadas e envia por email.

```groovy
import com.atlassian.jira.component.ComponentAccessor
import com.atlassian.jira.issue.search.SearchProvider
import com.atlassian.jira.bc.issue.search.SearchService
import com.atlassian.jira.web.bean.PagerFilter
import com.atlassian.mail.Email
import com.atlassian.mail.server.SMTPMailServer
import com.atlassian.jira.util.velocity.VelocityManager

def jqlQuery = "duedate < now() AND status != 'Closed'"
def emailAddress = "example@example.com"
def subject = "Relatório de Issues Atrasadas"
def velocityManager = ComponentAccessor.velocityManager

def user = ComponentAccessor.jiraAuthenticationContext.loggedInUser
def searchService = ComponentAccessor.getComponent(SearchService)
def searchProvider = ComponentAccessor.getComponent(SearchProvider)
def mailServer = ComponentAccessor.mailServerManager.defaultSMTPMailServer

if (!mailServer) {
    log.warn("No SMTP mail server defined!")
    return
}

def parseResult = searchService.parseQuery(user, jqlQuery)
if (!parseResult.isValid()) {
    log.warn("Query inválida: $jqlQuery")
    return
}

def searchResults = searchService.search(user, parseResult.query, PagerFilter.getUnlimitedFilter())
def issues = searchResults.issues

def templatePath = "templates/email-issues-atrasadas.vm"
def templateParams = ["issues": issues]

def body = velocityManager.getEncodedBodyForComponent(templatePath, templateParams, "UTF-8")

def email = new Email(emailAddress)
email.setSubject(subject)
email.setBody(body)

mailServer.send(email)
```

#### Job para Atualizar Campos Personalizados com Dados Externos

Este script atualiza um campo personalizado em todas as issues de um projeto específico com dados obtidos de um serviço externo.

```groovy
import com.atlassian.jira.component.ComponentAccessor
import groovy.json.JsonSlurper

def projectKey = "PROJETO"
def apiUrl = "https://api.exemplo.com/dados"
def customFieldName = "Campo Personalizado"

def project = ComponentAccessor.projectManager.getProjectObjByKey(projectKey)
def customFieldManager = ComponentAccessor.customFieldManager
def issueManager = ComponentAccessor.issueManager
def httpClient = new URL(apiUrl).openConnection()

if (!project) {
    log.warn("Projeto não encontrado: $projectKey")
    return
}

def customField = customFieldManager.getCustomFieldObjectByName(customFieldName)
if (!customField) {
    log.warn("Campo personalizado não encontrado: $customFieldName")
    return
}

def jsonSlurper = new JsonSlurper()
def response = httpClient.getInputStream().text
def data = jsonSlurper.parseText(response)

def issues = issueManager.getIssueObjects(issueManager.getIssueIdsForProject(project.id))

issues.each { issue ->
    def newValue = data.find { it.id == issue.id }?.value
    if (newValue) {
        issue.setCustomFieldValue(customField, newValue)
        issueManager.updateIssue(user, issue, EventDispatchOption.ISSUE_UPDATED, false)
        log.info("Campo atualizado para a issue ${issue.key}")
    }
}
```

#### Job para Notificar Usuários sobre Issues Atribuídas

Este script envia um lembrete por email para os usuários que possuem issues atribuídas que estão sem atividade por mais de 7 dias.

```groovy
import com.atlassian.jira.component.ComponentAccessor
import com.atlassian.mail.server.SMTPMailServer
import com.atlassian.mail.Email

def projectKey = "PROJETO"
def inactivityPeriod = 7 // Em dias

def issueManager = ComponentAccessor.issueManager
def commentManager = ComponentAccessor.commentManager
def mailServer = ComponentAccessor.mailServerManager.defaultSMTPMailServer

if (!mailServer) {
    log.warn("No SMTP mail server defined!")
    return
}

def project = ComponentAccessor.projectManager.getProjectObjByKey(projectKey)
if (!project) {
    log.warn("Projeto não encontrado: $projectKey")
    return
}

def now = new Date()
def issues = issueManager.getIssueObjects(issueManager.getIssueIdsForProject(project.id))

issues.each { issue ->
    def assignee = issue.assignee
    if (assignee) {
        def lastComment = commentManager.getLastComment(issue)
        def lastUpdate = lastComment ? lastComment.created : issue.updated
        def inactivity = (now.time - lastUpdate.time) / (1000 * 60 * 60 * 24)
        if (inactivity > inactivityPeriod) {
            def emailAddress = assignee.emailAddress
            def subject = "Lembrete: Issue Atribuída sem Atividade"
            def body = """\
Olá ${assignee.displayName},

A issue ${issue.key} não teve atividade por mais de $inactivityPeriod dias.

Resumo: ${issue.summary}
Descrição: ${issue.description}

Por favor, verifique e tome as ações necessárias.

Atenciosamente,
Sua Equipe Jira
"""

            def email = new Email(emailAddress)
            email.setSubject(subject)
            email.setBody(body)

            mailServer.send(email)
            log.info("Lembrete enviado para ${assignee.displayName} sobre a issue ${issue.key}")
        }
    }
}
```

#### Conclusão

Os jobs no Jira, utilizando ScriptRunner, são uma ferramenta poderosa para automatizar tarefas regulares e manter o sistema Jira eficiente e atualizado. Com esses exemplos de scripts, você pode implementar soluções para limpeza de dados, geração de relatórios, integração com serviços externos e notificações automáticas. Adaptar e expandir esses scripts conforme suas necessidades específicas aumentará a produtividade e a eficácia de sua equipe.