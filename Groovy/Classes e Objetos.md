## Classes e Objetos em Groovy

### Introdução

Classes e objetos são fundamentais na programação orientada a objetos (OOP). Groovy, sendo uma linguagem baseada na JVM, adota o paradigma OOP de maneira semelhante ao Java, mas com algumas melhorias e simplificações na sintaxe.


### Definição de Classes

Uma classe em Groovy é uma estrutura que define propriedades (atributos) e métodos (comportamentos) para os objetos que são instâncias dessa classe.

#### Exemplo

```groovy
class Pessoa {
    String nome
    int idade

    void saudacao() {
        println("Olá, meu nome é $nome e eu tenho $idade anos.")
    }
}

def pessoa = new Pessoa(nome: "João", idade: 25)
pessoa.saudacao() // Saída: Olá, meu nome é João e eu tenho 25 anos.
```

### Construtores

Construtores são métodos especiais usados para inicializar objetos. Groovy permite a definição de construtores explícitos, mas também fornece um construtor padrão automaticamente.

#### Construtor Padrão

```groovy
class Pessoa {
    String nome
    int idade
}

def pessoa = new Pessoa()
pessoa.nome = "Maria"
pessoa.idade = 30
println(pessoa.nome) // Saída: Maria
println(pessoa.idade) // Saída: 30
```

#### Construtor Parametrizado

```groovy
class Pessoa {
    String nome
    int idade

    Pessoa(String nome, int idade) {
        this.nome = nome
        this.idade = idade
    }
}

def pessoa = new Pessoa("Carlos", 40)
println(pessoa.nome) // Saída: Carlos
println(pessoa.idade) // Saída: 40
```

### Herança

Herança é um princípio da OOP onde uma classe (subclasse) herda as propriedades e métodos de outra classe (superclasse).

#### Exemplo

```groovy
class Animal {
    String nome

    void comer() {
        println("$nome está comendo.")
    }
}

class Cachorro extends Animal {
    void latir() {
        println("$nome está latindo.")
    }
}

def cachorro = new Cachorro(nome: "Rex")
cachorro.comer() // Saída: Rex está comendo.
cachorro.latir() // Saída: Rex está latindo.
```

### Interfaces

Interfaces definem um contrato que uma classe deve seguir. Em Groovy, uma interface pode conter declarações de métodos, que devem ser implementados pelas classes que implementam a interface.

#### Exemplo

```groovy
interface Veiculo {
    void acelerar()
    void frear()
}

class Bicicleta implements Veiculo {
    void acelerar() {
        println("A bicicleta está acelerando.")
    }

    void frear() {
        println("A bicicleta está freando.")
    }
}

def bicicleta = new Bicicleta()
bicicleta.acelerar() // Saída: A bicicleta está acelerando.
bicicleta.frear() // Saída: A bicicleta está freando.
```
