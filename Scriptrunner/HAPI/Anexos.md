
# Trabalhar com Anexos no Jira usando ScriptRunner (HAPI)

O **HAPI** do ScriptRunner facilita a adição, acesso e exclusão de anexos em issues no Jira. Você pode adicionar anexos ao criar, atualizar, ou transitar issues, e também pode acessar informações sobre os anexos ou excluí-los quando necessário.

## Adicionando Anexos

### Adicionando Anexos a uma Issue Existente

Para adicionar um anexo a uma issue já existente, você precisa fornecer um **arquivo** ou o **caminho para o arquivo**. O arquivo precisa estar acessível no servidor Jira onde o código está sendo executado.

#### Exemplo: Adicionando um Anexo a uma Issue

```groovy
def issue = Issues.getByKey('SR-1')

// Adicionando anexo por meio de um objeto File
issue.addAttachment(new File('/path/to/file'))

// Ou, usando uma string com o caminho do arquivo
issue.addAttachment('/path/to/file')
```

> **Nota**: Certifique-se de que o usuário executando o script tem permissão para adicionar anexos no projeto da issue.

### Adicionando Anexos ao Criar, Atualizar ou Transitar uma Issue

Você também pode adicionar anexos ao criar, atualizar ou transitar uma issue, como mostrado no exemplo abaixo.

#### Exemplo: Criando uma Issue e Adicionando um Anexo

```groovy
Issues.create('JRA', 'Bug') {
    setSummary('Preciso de ajuda!')

    // Adicionando anexo por meio de um objeto File
    addAttachment(new File('/path/to/file'))

    // Ou usando uma string com o caminho do arquivo
    addAttachment('/path/to/other/file')
}
```

### Dados Transitórios como Anexos

Se você tiver dados transitórios que gostaria de adicionar como anexo, salve esses dados em um arquivo temporário, anexe o arquivo à issue e, em seguida, exclua o arquivo.

## Acessando Anexos

Você pode acessar os anexos de uma issue usando o método `getAttachments()` para listar os anexos e realizar operações, como verificar o nome do arquivo ou outras propriedades.

#### Exemplo: Listando Anexos de uma Issue

```groovy
Issues.getByKey('SR-1').attachments.each { attachment ->
    log.warn("Nome do anexo: ${attachment.filename}")
}
```

### Acessando Anexos Durante Transições

Durante uma transição de workflow, anexos adicionados por um usuário podem não estar acessíveis pela API do Jira até que a transição seja concluída. No entanto, o HAPI permite acessar esses anexos "em trânsito" usando o método `getAllAttachments()`.

Isso é útil para validar anexos durante uma transição, verificando propriedades como nome do arquivo, tamanho ou conteúdo, antes que o anexo seja salvo no banco de dados.

#### Exemplo: Validando o Conteúdo de Anexos em uma Transição

```groovy
issue.allAttachments.any {
    it.withInputStream {
        it.text.trim() == 'conteudo_especifico'
    }
}
```

## Obter a URL de Download de um Anexo

O HAPI facilita a obtenção da URL de download de qualquer anexo em uma issue, o que pode ser útil para relatórios ou notificações por e-mail.

#### Exemplo: Obtendo URLs de Download de Anexos

```groovy
def downloadUrls = issue.attachments*.downloadUrl
log.warn("URLs para download: ${downloadUrls}")
```

## Excluindo Anexos

Você pode excluir anexos de uma issue usando o método `delete()` da classe **Attachment**. Para excluir todos os anexos de uma issue, siga o exemplo abaixo.

#### Exemplo: Excluindo Todos os Anexos de uma Issue

```groovy
issue.attachments.each {
    it.delete()
}
```

### Excluindo Anexos Ignorando Permissões de Segurança

Se precisar excluir anexos ignorando as permissões de segurança, use o método `deleteOverrideSecurity()`.

#### Exemplo: Excluindo Anexos Ignorando a Segurança

```groovy
issue.attachments.each {
    it.deleteOverrideSecurity()
}
```

