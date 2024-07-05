# Behaviours no Jira: Scripts para Customizar Comportamentos de Campos com ScriptRunner

### Introdução

Behaviours no Jira permitem customizar a interface de usuário e o comportamento de campos em telas de criação, edição e transição de issues. Utilizando o ScriptRunner, você pode controlar a visibilidade, obrigatoriedade e valores dos campos dinamicamente com scripts Groovy.

### Exemplos de Scripts para Behaviours

### Tornar um Campo Obrigatório com Base em Outro Campo

Este script torna o campo "Descrição" obrigatório se o campo "Tipo de Issue" estiver definido como "Bug".

```groovy
import com.onresolve.jira.groovy.user.FieldBehaviours
import com.onresolve.jira.groovy.user.FormField
import com.atlassian.jira.component.ComponentAccessor

def tipoIssueField = getFieldById("issuetype")
def descricaoField = getFieldById("description")

def tipoIssueValue = tipoIssueField.getValue()

if (tipoIssueValue == "Bug") {
    descricaoField.setRequired(true)
} else {
    descricaoField.setRequired(false)
}
```

#### Definir o Valor de um Campo com Base em Outro Campo

Este script define automaticamente o campo "Prioridade" como "Alta" se o campo "Severidade" estiver definido como "Crítica".

```groovy
import com.onresolve.jira.groovy.user.FieldBehaviours
import com.onresolve.jira.groovy.user.FormField
import com.atlassian.jira.component.ComponentAccessor

def severidadeField = getFieldByName("Severidade")
def prioridadeField = getFieldById("priority")

def severidadeValue = severidadeField.getValue()

if (severidadeValue == "Crítica") {
    prioridadeField.setFormValue("Alta")
}
```

#### Mostrar ou Ocultar Campos com Base em Outro Campo

Este script oculta o campo "Motivo de Rejeição" a menos que o campo "Status" esteja definido como "Rejeitado".

```groovy
import com.onresolve.jira.groovy.user.FieldBehaviours
import com.onresolve.jira.groovy.user.FormField
import com.atlassian.jira.component.ComponentAccessor

def statusField = getFieldById("status")
def motivoRejeicaoField = getFieldByName("Motivo de Rejeição")

def statusValue = statusField.getValue()

if (statusValue == "Rejeitado") {
    motivoRejeicaoField.setHidden(false)
} else {
    motivoRejeicaoField.setHidden(true)
}
```

#### Preencher Campos Automaticamente com Valores Derivados

Este script preenche automaticamente o campo "Resumo" com um valor concatenado de outros campos.

```groovy
import com.onresolve.jira.groovy.user.FieldBehaviours
import com.onresolve.jira.groovy.user.FormField
import com.atlassian.jira.component.ComponentAccessor

def tipoIssueField = getFieldById("issuetype")
def componenteField = getFieldByName("Componente")
def resumoField = getFieldById("summary")

def tipoIssueValue = tipoIssueField.getValue()
def componenteValue = componenteField.getValue()

def novoResumo = "${tipoIssueValue} - ${componenteValue}"
resumoField.setFormValue(novoResumo)
```

#### Validar Campos com Regras Personalizadas

Este script impede a criação de uma issue se o campo "Data de Entrega" estiver definido para uma data no passado.

```groovy
import com.onresolve.jira.groovy.user.FieldBehaviours
import com.onresolve.jira.groovy.user.FormField
import com.atlassian.jira.component.ComponentAccessor
import java.text.SimpleDateFormat

def dataEntregaField = getFieldByName("Data de Entrega")
def dataEntregaValue = dataEntregaField.getValue() as String

def dateFormat = new SimpleDateFormat("dd/MM/yyyy")
def dataEntrega = dateFormat.parse(dataEntregaValue)
def hoje = new Date()

if (dataEntrega.before(hoje)) {
    dataEntregaField.setError("A Data de Entrega não pode ser uma data passada.")
} else {
    dataEntregaField.clearError()
}
```

#### Conclusão

Behaviours no Jira, utilizando ScriptRunner, oferecem uma maneira poderosa de personalizar a interface e o comportamento dos campos de issue de acordo com as necessidades específicas da sua organização. Com esses exemplos de scripts, você pode controlar a obrigatoriedade, visibilidade e valores dos campos dinamicamente, melhorando a usabilidade e garantindo a integridade dos dados inseridos. Adapte e expanda esses scripts conforme necessário para atender aos requisitos específicos do seu fluxo de trabalho.