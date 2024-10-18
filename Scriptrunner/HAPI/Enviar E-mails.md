# Envio de E-mails com ScriptRunner (HAPI) no Jira

Enviar e-mails é uma necessidade comum ao automatizar processos no Jira, como notificações ou atualizações. O **HAPI** do ScriptRunner oferece uma maneira simples de enviar e-mails diretamente a partir de scripts. Abaixo, você aprenderá como configurar e enviar e-mails, incluindo e-mails HTML e com anexos.

### Pré-requisitos

Antes de enviar e-mails pelo HAPI, é necessário configurar um servidor de e-mails SMTP no Jira. Isso permitirá que o Jira envie e-mails através da fila de mensagens do sistema.

### Envio de E-mails Simples

O envio básico de e-mails requer que você especifique pelo menos um destinatário, um assunto e o corpo do e-mail. O exemplo abaixo mostra o envio de um e-mail com os requisitos mínimos:

#### Exemplo: Enviando E-mail para Múltiplos Destinatários

```groovy
Mail.send {
    setTo('foo@example.com', 'bar@example.com')
    setSubject('Este é o assunto')
    setBody('Este é o corpo do e-mail')
}
```

#### Explicação:
- `setTo`: define os destinatários do e-mail.
- `setSubject`: define o assunto do e-mail.
- `setBody`: define o conteúdo do corpo do e-mail (texto simples).

> **Nota:** Quando o e-mail é enviado, ele é adicionado à fila de e-mails do Jira e será enviado dentro de um minuto.

### Definindo o Remetente do E-mail

Você pode especificar o endereço de e-mail do remetente com `setFrom()`, além de configurar o nome exibido no cliente de e-mail com `setFromName()`.

#### Exemplo: Definindo o Remetente e Nome

```groovy
Mail.send {
    setTo('foo@example.com')
    setFrom('from@example.com')
    setFromName('Seu Chefe')
    setSubject('Aviso Importante')
    setBody('Este é o corpo do e-mail enviado pelo seu chefe.')
}
```

#### Explicação:
- `setFrom`: define o endereço de e-mail do remetente.
- `setFromName`: define o nome que será exibido como remetente.

### Envio de E-mails HTML

Caso queira enviar um e-mail no formato **HTML**, você pode usar o método `setHtml()`, que habilita a interpretação de HTML no corpo da mensagem.

#### Exemplo: Enviando E-mail em HTML

```groovy
Mail.send {
    setTo('foo@example.com')
    setSubject('E-mail com HTML')
    setHtml()
    setBody('Este é um e-mail com <b>HTML</b>.')
}
```

#### Explicação:
- `setHtml()`: habilita o corpo do e-mail para interpretar conteúdo HTML.
- `setBody`: define o conteúdo do e-mail com tags HTML.

### Envio de Anexos

Você pode adicionar arquivos como anexos ao e-mail utilizando o método `addAttachment()`. Esse método pode ser chamado múltiplas vezes, se necessário.

#### Exemplo: Enviando E-mail com Anexos

```groovy
Mail.send {
    setTo('foo@example.com')
    setSubject('E-mail com Anexo')
    setBody('Este e-mail contém um anexo.')
    addAttachment('relatorio-mensal.xlsx')
}
```

#### Explicação:
- `addAttachment`: adiciona um arquivo ao e-mail. Especifique o nome do arquivo ou caminho onde o arquivo está armazenado.

### Envio de Imagens Inline no E-mail

Para incluir imagens no corpo de um e-mail HTML como imagens embutidas, você pode usar `addAttachment()` para adicionar a imagem e, em seguida, referenciá-la no corpo do e-mail com um **content ID (CID)**.

#### Exemplo: E-mail com Imagem Inline

```groovy
Mail.send {
    setTo('foo@example.com')
    setSubject('E-mail com Imagem Inline')
    setHtml()
    setBody('Imagem embutida <img src="cid:${addAttachment("imagem-logo.png")}/>"')
}
```

#### Explicação:
- `addAttachment("imagem-logo.png")`: adiciona a imagem como anexo e retorna o content ID, que pode ser usado no `src` da tag `<img>`.
