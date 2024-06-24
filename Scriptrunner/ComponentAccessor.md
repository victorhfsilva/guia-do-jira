### ComponentAccessor do JIRA com Groovy

O `ComponentAccessor` é uma ferramenta poderosa no JIRA que permite acessar os componentes principais do sistema JIRA. Esses componentes podem ser usados para manipular dados, executar operações e interagir com a API do JIRA de maneira eficiente.

### Introdução

**O que é o `ComponentAccessor`?**
- O `ComponentAccessor` é uma classe do JIRA que fornece acesso a vários componentes internos do JIRA.
- Esses componentes incluem serviços, gerenciadores e utilitários que são essenciais para a manipulação e gestão de dados no JIRA.

### Importando o ComponentAccessor

Antes de começar a usar o `ComponentAccessor`, é necessário importá-lo em seu script Groovy:

```groovy
import com.atlassian.jira.component.ComponentAccessor
```

### Componentes Comuns Acessíveis pelo ComponentAccessor

Aqui estão alguns dos componentes mais usados e como acessá-los:

1. **IssueManager**
   - Gerencia operações relacionadas a issues (criação, atualização, exclusão).
   ```groovy
   def issueManager = ComponentAccessor.getIssueManager()
   ```

2. **ProjectManager**
   - Gerencia operações relacionadas a projetos.
   ```groovy
   def projectManager = ComponentAccessor.getProjectManager()
   ```

3. **UserManager**
   - Gerencia operações relacionadas a usuários.
   ```groovy
   def userManager = ComponentAccessor.getUserManager()
   ```

4. **CustomFieldManager**
   - Gerencia campos personalizados.
   ```groovy
   def customFieldManager = ComponentAccessor.getCustomFieldManager()
   ```

5. **WorkflowManager**
   - Gerencia workflows.
   ```groovy
   def workflowManager = ComponentAccessor.getWorkflowManager()
   ```

6. **SearchService**
   - Executa buscas usando JQL.
   ```groovy
   def searchService = ComponentAccessor.getComponent(SearchService)
   ```

### Exemplos Práticos

#### Obter Campo de uma issue

```groovy
import com.atlassian.jira.component.ComponentAccessor

def issueManager = ComponentAccessor.getIssueManager()
def issue = issueManager.getIssueObject("ISSUE-123")
issue.getSummary()
```

#### Obter Campos Personalizados de Issue

```groovy
import com.atlassian.jira.component.ComponentAccessor

def issueManager = ComponentAccessor.getIssueManager()
def issue = issueManager.getIssueObject("SD-282792")

issue.getCustomFieldValue("Quantidade Itens Vinculados")
```

#### Executar uma Busca JQL

```groovy
import com.atlassian.jira.web.bean.PagerFilter
import com.atlassian.jira.bc.issue.search.SearchService
import com.atlassian.jira.component.ComponentAccessor
import com.atlassian.jira.issue.search.SearchProvider
import com.atlassian.jira.jql.builder.JqlQueryBuilder
import com.atlassian.jira.user.ApplicationUser

def searchService = ComponentAccessor.getComponent(SearchService)
def searchProvider = ComponentAccessor.getComponent(SearchProvider)
def user = ComponentAccessor.userManager.getUserByName("edivaldo_lima")

def query = JqlQueryBuilder.newBuilder().where().project("SD").and().assigneeUser(user.getName()).buildQuery()
def searchResults = searchService.search(user, query, PagerFilter.getUnlimitedFilter())

searchResults.getResults().get(0).getSummary()
```

### Conclusão

O `ComponentAccessor` é uma ferramenta essencial para qualquer desenvolvedor ou administrador que deseja automatizar e personalizar o JIRA usando Groovy. Ele oferece acesso direto aos principais componentes do JIRA, permitindo a manipulação eficaz de dados e a execução de operações complexas. Compreender como usar o `ComponentAccessor` e os componentes que ele expõe é fundamental para maximizar o potencial de automação e personalização do JIRA.