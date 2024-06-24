# Funções e Closures em Groovy

### Funções

As funções, também chamadas de métodos, são blocos de código que executam uma tarefa específica e podem ser reutilizados em diferentes partes do programa. Groovy suporta a definição de métodos tanto em classes quanto em scripts.

#### Definição de Funções

Um método em Groovy é definido usando a palavra-chave `def`, seguida pelo nome do método, uma lista de parâmetros entre parênteses e um bloco de código entre chaves.

##### Exemplo

```groovy
def saudacao(nome) {
    return "Olá, $nome!"
}

println(saudacao("João")) // Saída: Olá, João!
```

#### Métodos com Tipo de Retorno

Você pode definir explicitamente o tipo de retorno de um método, assim como em Java.

##### Exemplo

```groovy
String saudacao(String nome) {
    return "Olá, $nome!"
}

println(saudacao("Maria")) // Saída: Olá, Maria!
```

#### Parâmetros com Valores Padrão

Groovy permite definir valores padrão para os parâmetros de um método.

##### Exemplo

```groovy
def saudacao(nome = "Visitante") {
    return "Olá, $nome!"
}

println(saudacao()) // Saída: Olá, Visitante!
println(saudacao("Carlos")) // Saída: Olá, Carlos!
```

### Closures em Groovy

Closures são blocos de código que podem ser atribuídos a variáveis, passados como argumentos para métodos ou retornados por métodos. Elas são uma parte poderosa e flexível da linguagem Groovy.

#### Definição de Closures

Uma closure é definida usando chaves `{}`, e os parâmetros são especificados após o símbolo `->`.

##### Exemplo

```groovy
def saudacao = { nome ->
    return "Olá, $nome!"
}

println(saudacao("João")) // Saída: Olá, João!
```

#### Closures com Vários Parâmetros

```groovy
def soma = { a, b ->
    return a + b
}

println(soma(5, 3)) // Saída: 8
```

#### Closures sem Parâmetros

Closures podem ser definidas sem parâmetros, e você pode chamá-las como funções.

##### Exemplo

```groovy
def digaOla = {
    return "Olá, Mundo!"
}

println(digaOla()) // Saída: Olá, Mundo!
```

#### Closures como Argumentos de Funções

Closures podem ser passadas como argumentos para funções, o que permite um alto grau de flexibilidade na definição de comportamentos dinâmicos.

##### Exemplo

```groovy
def executeClosure(closure) {
    closure()
}

def minhaClosure = {
    println("Executando a closure!")
}

executeClosure(minhaClosure) // Saída: Executando a closure!
```

#### Closures e Variáveis de Escopo Externo

Closures têm acesso ao escopo externo em que foram definidas, o que significa que podem acessar e modificar variáveis definidas fora do seu bloco.

##### Exemplo

```groovy
def nome = "João"

def saudacao = {
    return "Olá, $nome!"
}

println(saudacao()) // Saída: Olá, João!

nome = "Maria"
println(saudacao()) // Saída: Olá, Maria!
```

### Exemplos Avançados

#### Métodos Aninhados

Você pode definir métodos dentro de outros métodos.

##### Exemplo

```groovy
def metodoExterno() {
    def metodoInterno() {
        return "Método Interno"
    }
    return metodoInterno()
}

println(metodoExterno()) // Saída: Método Interno
```

#### Utilizando Closures em Coleções

Closures são frequentemente usadas com métodos de coleção em Groovy, como `each`, `collect`, `findAll`, `find`, `any`, `every`, entre outros.

##### Exemplo

```groovy
def numeros = [1, 2, 3, 4, 5]

// Usando each
numeros.each { numero ->
    println(numero)
}

// Usando collect
def quadrados = numeros.collect { numero ->
    return numero * numero
}

println(quadrados) // Saída: [1, 4, 9, 16, 25]
```

#### Closures com Parâmetros Implícitos

Se uma closure tem apenas um parâmetro, você pode omitir a definição do parâmetro. O parâmetro implícito é chamado `it`.

##### Exemplo

```groovy
def saudacao = {
    return "Olá, $it!"
}

println(saudacao("João")) // Saída: Olá, João!
```