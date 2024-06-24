## Coleções em Groovy

Groovy oferece uma sintaxe concisa e poderosa para trabalhar com coleções, incluindo listas, mapas e arrays. Estas estruturas de dados são fundamentais para o desenvolvimento de aplicações eficientes e flexíveis. 

### Listas

Listas são coleções ordenadas de elementos que podem conter duplicatas. Em Groovy, as listas são instâncias da classe `java.util.ArrayList`.

#### Criação de Listas

##### Sintaxe

```groovy
def lista = [elemento1, elemento2, elemento3]
```

##### Exemplo

```groovy
def frutas = ["Maçã", "Banana", "Cereja"]
println(frutas) // Saída: [Maçã, Banana, Cereja]
```

#### Métodos Comuns

- **add()**: adiciona um elemento à lista.
- **remove()**: remove um elemento da lista.
- **get()**: obtém um elemento pelo índice.
- **size()**: retorna o número de elementos na lista.
- **contains()**: verifica se um elemento está na lista.
- **each()**: itera sobre cada elemento da lista.

##### Exemplos

```groovy
def frutas = ["Maçã", "Banana", "Cereja"]

// Adicionar elemento
frutas.add("Damasco")
println(frutas) // Saída: [Maçã, Banana, Cereja, Damasco]

// Remover elemento
frutas.remove("Banana")
println(frutas) // Saída: [Maçã, Cereja, Damasco]

// Obter elemento
def primeiraFruta = frutas.get(0)
println(primeiraFruta) // Saída: Maçã

// Tamanho da lista
println(frutas.size()) // Saída: 3

// Verificar se contém elemento
println(frutas.contains("Cereja")) // Saída: true

// Iterar sobre elementos
frutas.each { fruta ->
    println(fruta)
}
```

### Mapas

Mapas são coleções de pares chave-valor. Em Groovy, os mapas são instâncias da classe `java.util.LinkedHashMap`.

#### Criação de Mapas

##### Sintaxe

```groovy
def mapa = [chave1: valor1, chave2: valor2, chave3: valor3]
```

##### Exemplo

```groovy
def pessoa = [nome: "João", idade: 30, cidade: "São Paulo"]
println(pessoa) // Saída: [nome:João, idade:30, cidade:São Paulo]
```

#### Métodos Comuns

- **put()**: adiciona um par chave-valor ao mapa.
- **remove()**: remove um par chave-valor do mapa.
- **get()**: obtém o valor associado a uma chave.
- **size()**: retorna o número de pares chave-valor no mapa.
- **containsKey()**: verifica se uma chave está no mapa.
- **each()**: itera sobre cada par chave-valor do mapa.

##### Exemplos

```groovy
def pessoa = [nome: "João", idade: 30, cidade: "São Paulo"]

// Adicionar par chave-valor
pessoa.put("profissao", "Engenheiro")
println(pessoa) // Saída: [nome:João, idade:30, cidade:São Paulo, profissao:Engenheiro]

// Remover par chave-valor
pessoa.remove("cidade")
println(pessoa) // Saída: [nome:João, idade:30, profissao:Engenheiro]

// Obter valor por chave
def nome = pessoa.get("nome")
println(nome) // Saída: João

// Tamanho do mapa
println(pessoa.size()) // Saída: 3

// Verificar se contém chave
println(pessoa.containsKey("idade")) // Saída: true

// Iterar sobre pares chave-valor
pessoa.each { chave, valor ->
    println("$chave: $valor")
}
```

### Arrays

Arrays são coleções de elementos de tamanho fixo. Em Groovy, os arrays são instâncias da classe `java.util.Arrays`.

#### Criação de Arrays

```groovy
def numeros = new int[3]
numeros[0] = 10
numeros[1] = 20
numeros[2] = 30
println(numeros) // Saída: [10, 20, 30]
```

#### Métodos Comuns

- **length**: retorna o tamanho do array.
- **sort()**: ordena os elementos do array.
- **each()**: itera sobre cada elemento do array.

##### Exemplos

```groovy
def numeros = [10, 20, 30] as int[]

// Tamanho do array
println(numeros.length) // Saída: 3

// Ordenar array
numeros = [30, 10, 20] as int[]
numeros.sort()
println(numeros) // Saída: [10, 20, 30]

// Iterar sobre elementos
numeros.each { numero ->
    println(numero)
}
```

## Manipulação Avançada de Coleções em Groovy

Groovy oferece uma gama de métodos avançados para manipulação de coleções, permitindo a realização de operações complexas de forma simples e eficiente.

### Métodos Avançados para Listas

#### `collect`

O método `collect` é usado para transformar uma coleção aplicando uma closure a cada elemento.

##### Exemplo

```groovy
def numeros = [1, 2, 3, 4, 5]
def quadrados = numeros.collect { it * it }
println(quadrados) // Saída: [1, 4, 9, 16, 25]
```

#### `find`

O método `find` retorna o primeiro elemento que satisfaz a condição especificada pela closure.

##### Exemplo

```groovy
def numeros = [1, 2, 3, 4, 5]
def primeiroPar = numeros.find { it % 2 == 0 }
println(primeiroPar) // Saída: 2
```

#### `findAll`

O método `findAll` retorna todos os elementos que satisfazem a condição especificada pela closure.

##### Exemplo

```groovy
def numeros = [1, 2, 3, 4, 5]
def pares = numeros.findAll { it % 2 == 0 }
println(pares) // Saída: [2, 4]
```

#### `any`

O método `any` verifica se pelo menos um elemento na coleção satisfaz a condição especificada pela closure.

##### Exemplo

```groovy
def numeros = [1, 2, 3, 4, 5]
def existePar = numeros.any { it % 2 == 0 }
println(existePar) // Saída: true
```

#### `every`

O método `every` verifica se todos os elementos na coleção satisfazem a condição especificada pela closure.

##### Exemplo

```groovy
def numeros = [1, 2, 3, 4, 5]
def todosSaoPositivos = numeros.every { it > 0 }
println(todosSaoPositivos) // Saída: true
```

#### `groupBy`

O método `groupBy` agrupa elementos da coleção com base em uma closure.

##### Exemplo

```groovy
def palavras = ["Groovy", "Java", "JavaScript", "Python", "Ruby"]
def agrupadoPorPrimeiraLetra = palavras.groupBy { it[0] }
println(agrupadoPorPrimeiraLetra) // Saída: [G:[Groovy], J:[Java, JavaScript], P:[Python], R:[Ruby]]
```

### Métodos Avançados para Mapas

#### `collectEntries`

O método `collectEntries` é usado para transformar um mapa aplicando uma closure a cada par chave-valor.

##### Exemplo

```groovy
def mapa = [a: 1, b: 2, c: 3]
def mapaAoQuadrado = mapa.collectEntries { chave, valor ->
    [(chave): valor * valor]
}
println(mapaAoQuadrado) // Saída: [a:1, b:4, c:9]
```

#### `find`

O método `find` em mapas retorna o primeiro par chave-valor que satisfaz a condição especificada pela closure.

##### Exemplo

```groovy
def mapa = [a: 1, b: 2, c: 3]
def primeiroPar = mapa.find { chave, valor -> valor > 1 }
println(primeiroPar) // Saída: b=2
```

#### `findAll`

O método `findAll` em mapas retorna todos os pares chave-valor que satisfazem a condição especificada pela closure.

##### Exemplo

```groovy
def mapa = [a: 1, b: 2, c: 3]
def paresMaioresQue1 = mapa.findAll { chave, valor -> valor > 1 }
println(paresMaioresQue1) // Saída: [b:2, c:3]
```

#### `any`

O método `any` em mapas verifica se pelo menos um par chave-valor satisfaz a condição especificada pela closure.

##### Exemplo

```groovy
def mapa = [a: 1, b: 2, c: 3]
def existeValorMaiorQue2 = mapa.any { chave, valor -> valor > 2 }
println(existeValorMaiorQue2) // Saída: true
```

#### `every`

O método `every` em mapas verifica se todos os pares chave-valor satisfazem a condição especificada pela closure.

##### Exemplo

```groovy
def mapa = [a: 1, b: 2, c: 3]
def todosMaioresQue0 = mapa.every { chave, valor -> valor > 0 }
println(todosMaioresQue0) // Saída: true
```

#### `groupBy`

O método `groupBy` em mapas agrupa pares chave-valor com base em uma closure.

##### Exemplo

```groovy
def mapa = [a: 1, b: 2, c: 3, d: 4]
def agrupadoPorParidade = mapa.groupBy { chave, valor -> valor % 2 == 0 ? "par" : "ímpar" }
println(agrupadoPorParidade) // Saída: [ímpar:[a:1, c:3], par:[b:2, d:4]]
```
