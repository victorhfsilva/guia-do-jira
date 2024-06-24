# Variáveis, Tipos de Dados e Operadores em Groovy

### Introdução ao Groovy

Groovy é uma linguagem de programação dinâmica que roda na Java Virtual Machine (JVM) e é compatível com a sintaxe do Java. Ele oferece suporte para programação orientada a objetos e funcional, facilitando a criação de scripts e aplicações robustas.

### Variáveis em Groovy

Variáveis são usadas para armazenar dados que podem ser usados e manipulados posteriormente no programa.

#### Declaração de Variáveis

Em Groovy, você pode declarar variáveis sem especificar o tipo explicitamente, utilizando a palavra-chave `def`:

```groovy
def nome = "João"
def idade = 25
```

Você também pode declarar variáveis com tipos explícitos, como no Java:

```groovy
String sobrenome = "Silva"
int ano = 2023
```

### Tipos de Dados

Groovy suporta todos os tipos de dados primitivos do Java e também oferece alguns tipos de dados adicionais.

#### Tipos de Dados Primitivos
- **int**: números inteiros
- **long**: números inteiros de maior precisão
- **float**: números de ponto flutuante de precisão simples
- **double**: números de ponto flutuante de precisão dupla
- **boolean**: valores booleanos (true/false)
- **char**: um único caractere

#### Tipos de Dados Não Primitivos
- **String**: sequência de caracteres
- **List**: lista de elementos
- **Map**: coleção de pares chave-valor
- **Date**: data e hora

#### Exemplos
```groovy
def numeroInteiro = 10
double numeroDecimal = 3.14
boolean isGroovyCool = true
String saudacao = "Olá, Mundo!"
List listaDeNomes = ["Ana", "Pedro", "Maria"]
Map dadosPessoa = [nome: "Carlos", idade: 30]
Date hoje = new Date()
```

### Operadores

Groovy suporta uma variedade de operadores para realizar operações em variáveis e valores.

#### Operadores Aritméticos

- **+** (adição): `3 + 2` resulta em `5`
- **-** (subtração): `5 - 2` resulta em `3`
- **\*** (multiplicação): `4 * 2` resulta em `8`
- **/** (divisão): `10 / 2` resulta em `5.0`
- **%** (módulo): `10 % 3` resulta em `1`

#### Operadores de Comparação

- **==** (igual a): `5 == 5` resulta em `true`
- **!=** (diferente de): `5 != 3` resulta em `true`
- **<** (menor que): `3 < 5` resulta em `true`
- **>** (maior que): `5 > 3` resulta em `true`
- **<=** (menor ou igual a): `3 <= 5` resulta em `true`
- **>=** (maior ou igual a): `5 >= 3` resulta em `true`

#### Operadores Lógicos

- **&&** (E lógico): `true && false` resulta em `false`
- **||** (OU lógico): `true || false` resulta em `true`
- **!** (negação lógica): `!true` resulta em `false`

#### Operadores de Atribuição

- **=** (atribuição): `x = 5`
- **+=** (adição e atribuição): `x += 3` é o mesmo que `x = x + 3`
- **-=** (subtração e atribuição): `x -= 2` é o mesmo que `x = x - 2`
- **\*=** (multiplicação e atribuição): `x *= 4` é o mesmo que `x = x * 4`
- **/=** (divisão e atribuição): `x /= 2` é o mesmo que `x = x / 2`
- **%=** (módulo e atribuição): `x %= 3` é o mesmo que `x = x % 3`

### Operadores Especiais

Groovy inclui vários operadores especiais que facilitam diversas operações comuns em programação. Aqui estão alguns dos operadores especiais mais usados, juntamente com exemplos de como utilizá-los.

#### Operador de Navegação Segura (?.)

O operador de navegação segura `?.` é usado para evitar exceções de ponteiro nulo (NullPointerException). Ele permite chamar métodos ou acessar propriedades em um objeto somente se esse objeto não for nulo.

##### Exemplo
```groovy
class Pessoa {
    String nome
    Endereco endereco
}

class Endereco {
    String cidade
}

Pessoa pessoa = new Pessoa(nome: "João")
println(pessoa.endereco?.cidade) // Saída: null, sem lançar NullPointerException
```

#### Operador Spread (*.)

O operador spread `*.` é usado para aplicar uma operação em todos os elementos de uma coleção. Ele permite executar métodos ou acessar propriedades em todos os elementos de uma lista, por exemplo.

##### Exemplo

```groovy
class Livro {
    String titulo
}

List<Livro> livros = [new Livro(titulo: "Livro 1"), new Livro(titulo: "Livro 2"), new Livro(titulo: "Livro 3")]
List<String> titulos = livros*.titulo
println(titulos) // Saída: [Livro 1, Livro 2, Livro 3]
```

#### Operador de Conversão (as)

O operador `as` é usado para converter um tipo de dado para outro. Ele pode ser usado para realizar conversões explícitas entre tipos.

##### Exemplo

```groovy
String numeroComoString = "123"
int numero = numeroComoString as int
println(numero) // Saída: 123
```

#### Operador de Expressão Regular (==~)

O operador `==~` é usado para verificar se uma string corresponde a uma expressão regular.

##### Exemplo
```groovy
String texto = "Groovy é ótimo"
boolean corresponde = texto ==~ /.*ótimo/
println(corresponde) // Saída: true
```

#### Operador Elvis (?:)

O operador Elvis `?:` é uma forma abreviada de fornecer um valor padrão caso uma expressão seja nula ou falsa.

##### Exemplo
```groovy
String nome = null
String nomePadrao = nome ?: "Desconhecido"
println(nomePadrao) // Saída: Desconhecido
```