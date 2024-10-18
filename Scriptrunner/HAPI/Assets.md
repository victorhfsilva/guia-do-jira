
# Trabalhar com Assets/Insight usando ScriptRunner (HAPI) no Jira

O **Assets** (anteriormente conhecido como Insight) é um banco de dados de objetos digitais no Jira que representa diversos ativos, como hardware, software, pessoas, e muito mais.

## Termos-chave

- **Object Schema**: Um banco de dados de tipos de objetos e ativos. Todos os ativos pertencem a um tipo de objeto que, por sua vez, está agrupado em schemas.
- **Object Schema Key**: A chave atribuída ao schema de objetos, geralmente uma sigla como "AAA" ou "IAS".
- **Object Type**: Um grupo que contém ativos do mesmo tipo (exemplo: "Membro da Equipe" que agrupa uma lista de funcionários).
- **Asset Object**: A representação digital de um ativo (exemplo: um servidor).
- **Attributes**: Definem os tipos de objetos e seus ativos (exemplo: Nome, Status, Sistema Operacional).
- **References**: Relacionamentos entre objetos (exemplo: um computador que faz referência a um sistema operacional).

## Trabalhando com Assets

### Definindo Campos Customizados de Assets

Para associar ativos ao Jira, você deve ter um campo personalizado de **Assets** disponível. Veja um exemplo de como definir múltiplos ativos em um campo de Assets:

```groovy
issue.update {
    setCustomFieldValue('My asset custom field') {
        set('AAA-1', 'AAA-2')
    }
}
```

### Criando um Novo Objeto de Asset

Você pode criar um novo objeto de **Asset** com múltiplos atributos usando HAPI. No exemplo abaixo, criamos um objeto "Host" no schema "IAS":

```groovy
Assets.create('IAS', 'Host') {
    setAttribute('Name', 'Servidor Web de Vendas')
    setAttribute('Hostname', 'salesweb')
    setAttribute('FQDN', 'sales.acme.com')
    setAttribute('Status', 'Em Serviço')
    setAttribute('RAM', 32_000)
    setAttribute('Virtual', true)
}
```

### Utilizando IDs em vez de Nomes

Para tornar os scripts mais robustos, você pode usar IDs de objetos e atributos em vez de nomes, pois os IDs permanecem constantes.

```groovy
Assets.create(2) {  // 2 é o ID do tipo de objeto
    setAttribute(71, Assets.getById(7))  // 71 é o ID do atributo e 7 o ID do valor
}
```

### Recuperando Objetos de Assets por Chave

Você pode recuperar um objeto de **Asset** usando sua chave única (como "AAA-1"):

```groovy
def asset = Assets.getByKey('AAA-1')
```

### Recuperando Valores de Atributos

Para recuperar valores de atributos de um objeto, use os getters adequados para o tipo de atributo. Por exemplo:

```groovy
def asset = Assets.create('IAS', 'Host') {
    setAttribute('Name', 'Servidor Web')
    setAttribute('RAM', 32_000)
    setAttribute('Virtual', true)
    setAttribute('Status', 'Em Serviço')
    setAttribute('Operating System',
        Assets.create('IAS', 'Operating System') {
            setAttribute('Name', 'Linux')
        }
    )
}

assert asset.getString('Name') == 'Servidor Web'
assert asset.getInteger('RAM') == 32_000
assert asset.getBoolean('Virtual') == true
assert asset.getReference('Operating System').getString('Name') == 'Linux'
```

### Atualizando um Objeto de Asset

Para atualizar um objeto de **Asset**, você pode usar o método `update` e modificar os atributos:

```groovy
def asset = Assets.getByKey('AAA-1')
asset.update {
    setAttribute('Name', 'Servidor Web de Vendas Atualizado')
    setAttribute('FQDN', 'vendas.acme.com')
}
```

### Adicionando e Removendo Valores de Atributos

Caso um atributo permita múltiplos valores, você pode adicionar ou remover valores usando os métodos `add()` e `remove()`.

#### Exemplo: Adicionando um Valor a um Atributo de CPUs

```groovy
asset.update {
    setAttribute('CPUs') {
        add('IAS-999')
    }
}
```

#### Exemplo: Removendo um Valor de um Atributo

```groovy
asset.update {
    setAttribute('CPUs') {
        remove('IAS-999')
    }
}
```

### Limpando Valores de Atributos

Você pode limpar (remover) o valor de um atributo usando `clearAttribute()`:

```groovy
asset.update {
    clearAttribute('FQDN')
}
```

### Trabalhando com Anexos em Objetos de Assets

#### Adicionando Anexos

Para adicionar um anexo a um objeto de **Asset**, especifique o arquivo que deseja anexar:

```groovy
asset.addAttachment(new File('/tmp/relatorio.txt'))
```

#### Lendo Conteúdo de Anexos

Para ler o conteúdo de um anexo, você pode usar o seguinte método:

```groovy
asset.attachments.find { it.filename == 'relatorio.txt' }.withInputStream { is ->
    log.warn(is.text)  // Processa o conteúdo do arquivo
}
```

#### Deletando Anexos

Para deletar todos os anexos de um objeto de **Asset**:

```groovy
asset.attachments.each { it.delete() }
```

### Executando Consultas AQL

A linguagem de consulta de Assets (AQL) permite que você busque objetos de ativos de forma eficiente, semelhante ao JQL no Jira.

#### Exemplo: Buscando Objetos de Host

```groovy
Assets.search("objectType = Host order by Name asc").each {
    log.warn(it.getString('Name'))
}
```

#### Contando Objetos com AQL

```groovy
def count = Assets.count("objectType = Host")
log.warn("Número de hosts: ${count}")
```

### Trabalhando com Comentários

Você pode adicionar, recuperar e deletar comentários em objetos de **Asset**.

#### Exemplo: Adicionando um Comentário

```groovy
asset.addComment("Este é um comentário de exemplo.")
```

#### Exemplo: Recuperando e Deletando Comentários

```groovy
asset.comments.findAll { it.author == 'admin' }.each { it.delete() }
```
