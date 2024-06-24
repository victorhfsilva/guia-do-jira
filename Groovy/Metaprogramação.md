## Metaprogramação em Groovy

### Introdução

Metaprogramação é a prática de escrever código que manipula outros códigos, seja modificando a estrutura ou comportamento durante a execução. Groovy, uma linguagem dinâmica, oferece várias ferramentas e técnicas poderosas para metaprogramação, permitindo maior flexibilidade e dinamismo na programação.

### Métodos Dinâmicos

Em Groovy, você pode adicionar métodos a classes existentes em tempo de execução utilizando o `metaClass`.

#### Adicionando Métodos

##### Exemplo

```groovy
class Pessoa {
    String nome
}

Pessoa.metaClass.saudacao = { ->
    println("Olá, meu nome é $nome.")
}

def pessoa = new Pessoa(nome: "Ana")
pessoa.saudacao() // Saída: Olá, meu nome é Ana.
```

#### Modificando Métodos Existentes

##### Exemplo

```groovy
class Calculadora {
    def soma(a, b) {
        a + b
    }
}

Calculadora.metaClass.soma = { a, b ->
    println("Soma sendo chamada com $a e $b")
    delegate.soma(a, b) + 10
}

def calc = new Calculadora()
println(calc.soma(2, 3)) // Saída: Soma sendo chamada com 2 e 3
                         //         15
```

### Propriedades Dinâmicas

Você pode adicionar propriedades dinâmicas a objetos em tempo de execução.

##### Exemplo

```groovy
class Carro {
    String modelo
}

def carro = new Carro(modelo: "Toyota")
carro.metaClass.cor = "Vermelho"
println(carro.cor) // Saída: Vermelho
```

### Métodos ExpandoMetaClass

O `ExpandoMetaClass` é uma ferramenta poderosa para adicionar e modificar métodos e propriedades em classes Groovy.

##### Exemplo

```groovy
ExpandoMetaClass.enableGlobally()

String.metaClass.inverter = { ->
    delegate.reverse()
}

println("Groovy".inverter()) // Saída: yvoorG
```

### Categorias

Categorias permitem adicionar métodos a classes existentes dentro de um escopo específico.

##### Exemplo

```groovy
class StringCategoria {
    static String inverter(String self) {
        self.reverse()
    }
}

use(StringCategoria) {
    println("Metaprogramação".inverter()) // Saída: oãçargormateM
}
```

### Métodos de Expando

Expando é uma classe que permite criar objetos dinâmicos com métodos e propriedades personalizadas.

##### Exemplo

```groovy
def pessoa = new Expando(nome: "Carlos", idade: 25)
pessoa.saudacao = { ->
    println("Olá, meu nome é $nome e eu tenho $idade anos.")
}

pessoa.saudacao() // Saída: Olá, meu nome é Carlos e eu tenho 25 anos.
```

### Interceptando Métodos e Propriedades

Groovy permite interceptar chamadas de métodos e acessos a propriedades usando `invokeMethod` e `getProperty`.

#### Interceptando Métodos

##### Exemplo

```groovy
class Exemplo {
    def invokeMethod(String nome, args) {
        return "Método $nome chamado com argumentos: ${args.join(', ')}"
    }
}

def exemplo = new Exemplo()
println(exemplo.teste(1, 2, 3)) // Saída: Método teste chamado com argumentos: 1, 2, 3
```

#### Interceptando Propriedades

##### Exemplo

```groovy
class Exemplo {
    def propriedades = [:]

    def getProperty(String nome) {
        return propriedades[nome] ?: "Propriedade $nome não encontrada"
    }

    void setProperty(String nome, valor) {
        propriedades[nome] = valor
    }
}

def exemplo = new Exemplo()
exemplo.idade = 30
println(exemplo.idade) // Saída: 30
println(exemplo.nome)  // Saída: Propriedade nome não encontrada
```

### Métodos de ExpandoMetaClass para Herança

Com `ExpandoMetaClass`, você pode modificar a hierarquia de classes dinamicamente.

##### Exemplo

```groovy
ExpandoMetaClass.enableGlobally()

class Animal {
    def fazerSom() {
        return "Som genérico"
    }
}

Animal.metaClass.fazerSom = { ->
    return "Som modificado"
}

def animal = new Animal()
println(animal.fazerSom()) // Saída: Som modificado
```

### ProxyMetaClass

O `ProxyMetaClass` permite criar proxies para classes, interceptando todas as chamadas de método.

##### Exemplo

```groovy
class Exemplo {
    def metodo() {
        println("Método original")
    }
}

def proxy = ProxyMetaClass.getInstance(Exemplo)
proxy.interceptor = new GroovyInterceptor() {
    Object beforeInvoke(Object obj, String nome, Object[] args) {
        println("Antes de chamar $nome")
        return null
    }

    Object afterInvoke(Object obj, String nome, Object[] args, Object resultado) {
        println("Depois de chamar $nome")
        return resultado
    }
}

def exemplo = new Exemplo()
proxy.use {
    exemplo.metodo()
}
// Saída:
// Antes de chamar metodo
// Método original
// Depois de chamar metodo
```