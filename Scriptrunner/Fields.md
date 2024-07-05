## Script Fields: Criação e Uso de Campos Calculados com o Script Runner e Groovy

### Introdução

Campos calculados são um recurso poderoso no Jira que permite criar campos personalizados cujos valores são gerados dinamicamente com base em scripts. Utilizando ScriptRunner e Groovy, você pode calcular e exibir dados em tempo real, melhorar a automação e aumentar a flexibilidade de seu fluxo de trabalho.

### Escrevendo o Script em Groovy

No editor de scripts do ScriptRunner, você escreverá um script Groovy para calcular o valor do campo. Vamos criar um exemplo de um campo calculado que exibe a soma de dois outros campos numéricos.

```groovy
import com.atlassian.jira.component.ComponentAccessor

// Obtém o valor dos campos existentes
def customFieldManager = ComponentAccessor.getCustomFieldManager()
def issueManager = ComponentAccessor.getIssueManager()

def issue = issueManager.getIssueObject(issue.id)
def customField1 = customFieldManager.getCustomFieldObjectByName("Campo Numérico 1")
def customField2 = customFieldManager.getCustomFieldObjectByName("Campo Numérico 2")

def value1 = issue.getCustomFieldValue(customField1) as Double
def value2 = issue.getCustomFieldValue(customField2) as Double

// Calcula a soma
def sum = value1 + value2

// Retorna o valor calculado
return sum
```

### Exemplos Práticos de Uso

1. **Campos de Data**: Calcular a diferença entre datas, como o tempo restante até uma data de vencimento.
   ```groovy
   def dueDate = issue.getDueDate()
   def currentDate = new Date()
   def daysLeft = (dueDate - currentDate) / (1000 * 60 * 60 * 24)
   return daysLeft
   ```

2. **Campos de Texto**: Concatenar valores de diferentes campos para criar um resumo personalizado.
   ```groovy
   def summary = issue.getSummary()
   def key = issue.getKey()
   return "$key - $summary"
   ```

#### Conclusão

Campos calculados utilizando ScriptRunner e Groovy oferecem uma grande flexibilidade e potencial para personalizar e automatizar processos no Jira. Com o conhecimento básico de Groovy e a compreensão dos requisitos do seu fluxo de trabalho, você pode criar soluções dinâmicas e eficientes para atender às necessidades específicas da sua equipe.