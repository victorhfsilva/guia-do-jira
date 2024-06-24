## Tratamento de Exceções em Groovy

### Introdução ao Tratamento de Exceções

O tratamento de exceções é um aspecto crucial de qualquer linguagem de programação, permitindo que os desenvolvedores lidem com situações inesperadas ou erros de forma controlada. Groovy, sendo uma linguagem baseada na JVM, utiliza as mesmas estruturas de tratamento de exceções que o Java, com algumas melhorias sintáticas que tornam o código mais conciso e legível.

### Estrutura Básica do Tratamento de Exceções

A estrutura básica para tratamento de exceções em Groovy é composta pelos blocos `try`, `catch`, `finally`, e opcionalmente `throw`.

#### Bloco `try-catch`

O bloco `try` é usado para envolver o código que pode lançar uma exceção. O bloco `catch` é usado para capturar e tratar essa exceção.

##### Exemplo

```groovy
try {
    def resultado = 10 / 0
} catch (ArithmeticException e) {
    println("Erro: Divisão por zero.")
}
```

#### Bloco `finally`

O bloco `finally` é opcional e contém código que será executado independentemente de uma exceção ter sido lançada ou não. É útil para liberar recursos ou executar ações de limpeza.

##### Exemplo

```groovy
try {
    def arquivo = new File("caminho/para/arquivo.txt")
    arquivo.text = "Escrevendo no arquivo."
} catch (IOException e) {
    println("Erro ao escrever no arquivo: ${e.message}")
} finally {
    println("Operação de escrita no arquivo concluída.")
}
```

### Lançando Exceções com `throw`

O `throw` é usado para lançar explicitamente uma exceção.

##### Exemplo

```groovy
def dividir(a, b) {
    if (b == 0) {
        throw new IllegalArgumentException("Divisor não pode ser zero.")
    }
    return a / b
}

try {
    dividir(10, 0)
} catch (IllegalArgumentException e) {
    println("Erro: ${e.message}")
}
```

### Capturando Múltiplas Exceções

Você pode capturar múltiplas exceções usando vários blocos `catch`.

##### Exemplo

```groovy
try {
    def texto = "123a"
    def numero = Integer.parseInt(texto)
} catch (NumberFormatException e) {
    println("Erro: Formato de número inválido.")
} catch (Exception e) {
    println("Erro geral: ${e.message}")
}
```

### Usando `try-with-resources`

Groovy herda do Java a capacidade de usar a estrutura `try-with-resources`, que é útil para gerenciar automaticamente a liberação de recursos, como fluxos de I/O.

##### Exemplo

```groovy
try (def reader = new BufferedReader(new FileReader("arquivo.txt"))) {
    println(reader.readLine())
} catch (IOException e) {
    println("Erro ao ler o arquivo: ${e.message}")
}
```

### Exceções Personalizadas

Você pode definir suas próprias exceções personalizadas criando classes que estendem `Exception`.

##### Exemplo

```groovy
class MinhaExcecao extends Exception {
    MinhaExcecao(String mensagem) {
        super(mensagem)
    }
}

def metodoQueLancaExcecao() {
    throw new MinhaExcecao("Algo deu errado.")
}

try {
    metodoQueLancaExcecao()
} catch (MinhaExcecao e) {
    println("Erro: ${e.message}")
}
```