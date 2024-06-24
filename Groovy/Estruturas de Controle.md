## Estruturas de Controle em Groovy

### Estruturas de Controle

Estruturas de controle são usadas para direcionar o fluxo de execução de um programa, permitindo a execução condicional de código e a repetição de blocos de código. Em Groovy, as principais estruturas de controle são `if`, `switch`, e loops (`for`, `while`).

### Estrutura Condicional `if`

A estrutura `if` é usada para executar um bloco de código apenas se uma condição específica for verdadeira.

#### Exemplo

```groovy
def idade = 18

if (idade < 18) {
    println("Menor de idade")
} else if (idade == 18) {
    println("Tem 18 anos")
} else {
    println("Maior de idade")
}
```

### Estrutura de Seleção `switch`

A estrutura `switch` é usada para executar diferentes blocos de código com base no valor de uma expressão. Ela é uma alternativa ao uso de múltiplas instruções `if-else`.

#### Exemplo

```groovy
def dia = "Segunda"

switch (dia) {
    case "Segunda":
        println("Início da semana")
        break
    case "Sexta":
        println("Quase fim de semana")
        break
    case "Sábado":
    case "Domingo":
        println("Fim de semana")
        break
    default:
        println("Dia normal")
}
```

### Estruturas de Repetição

#### Loop `for`

O loop `for` é usado para iterar sobre uma sequência de valores, como uma faixa de números, uma lista ou um array.

##### Exemplo

```groovy
for (def i = 0; i < 5; i++) {
    println("Iteração número $i")
}
```

Para iterar sobre uma coleção:

```groovy
def nomes = ["Ana", "Pedro", "Maria"]

for (nome in nomes) {
    println(nome)
}
```

#### Loop `while`

O loop `while` executa um bloco de código enquanto uma condição específica for verdadeira.

##### Exemplo

```groovy
def contador = 0

while (contador < 5) {
    println("Contador: $contador")
    contador++
}
```

#### Loop `do-while`

O loop `do-while` é semelhante ao loop `while`, mas garante que o bloco de código seja executado pelo menos uma vez, independentemente da condição.

##### Exemplo

```groovy
def contador = 0

do {
    println("Contador: $contador")
    contador++
} while (contador < 5)
```
