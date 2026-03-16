# Exercícios Java 🖥️

Coleção de exercícios práticos desenvolvidos durante o curso de Software Developer (Desenvolvimento Web Full-Stack) da CESAE Digital — programa Reskilling 4 Employment.

---

## Exercícios

### EX01 — Banco Simulado 🏦
Simulação de uma conta bancária com menu interativo.

**Conceitos:** variáveis globais, validação de input, menu com `do-while`, operador ternário, formatação de valores com `String.format()`

**Funcionalidades:** criar conta, depositar, levantar, verificar saldo, contador de transações

---

### EX02 — Calculadora de Notas 📊
Converte notas numéricas em conceitos (A-F) com feedback personalizado.

**Conceitos:** `switch`, `if-else` encadeado, operador ternário, loop de validação, leitura de caracteres

**Funcionalidades:** calcular conceito, exibir situação (aprovado/reprovado), feedback com horas de estudo recomendadas

---

### EX03 — Lista de Compras 🛒
Gestor de lista de compras com cálculo de IVA.

**Conceitos:** `ArrayList`, constantes (`final`), validação de duplicados, cálculos com percentagem, formatação de output em colunas

**Funcionalidades:** adicionar itens, calcular subtotal, IVA (23%) e total, limite máximo de 10 itens

---

### EX04 — Analisador de Texto 🔍
Analisa um texto e apresenta estatísticas detalhadas.

**Conceitos:** `split()` com regex (`\\s+`), `ArrayList`, `Character` (isLetter, isUpperCase, isLowerCase), `printf` com `%.2f`

**Funcionalidades:** contar palavras, letras, espaços, maiúsculas e minúsculas, palavra mais longa/curta, média de comprimento, pesquisa de palavra

---

### EX05 — Jogo de Adivinhação 🎮
Jogo interativo de adivinhar número com dificuldades e histórico.

**Conceitos:** `Random`, `ArrayList` para histórico, dificuldades configuráveis via array, dicas por intervalo de diferença

**Funcionalidades:** 3 níveis de dificuldade (Fácil/Médio/Difícil), dicas progressivas (muito alto/alto/um pouco alto), histórico de tentativas, estatísticas finais

---

### EX06 — Sistema de Biblioteca 📚
Sistema completo de gestão de biblioteca com empréstimos e devoluções.

**Conceitos:** `ArrayLists` paralelos, `LocalDate`, validação de formato (ISBN, número de leitor), deteção de atraso com cálculo de dias

**Funcionalidades:** adicionar livros, pesquisar por título/autor/ISBN, emprestar com prazo de 7 dias, devolver com deteção de atraso, estatísticas da biblioteca

---

### Sudoku 🔢
Programa interativo para manipular quadrículas Sudoku 9x9.

**Conceitos:** arrays bidimensionais, manipulação de matrizes, algoritmo de validação com arrays booleanos, reflexão e permuta de linhas/colunas

**Funcionalidades:** geração automática de quadrícula válida, permuta de números/linhas/colunas/faixas, reflexão horizontal e vertical, validação completa da grelha

---

## Como executar cada exercício

```bash
# Compilar
javac NomeDoFicheiro.java

# Executar
java NomeDoFicheiro

# Exemplo:
javac EX06.java
java EX06
```

## Estrutura do repositório

```
exercicios-java/
├── Exercicios/
│   ├── EX01.java    # Banco Simulado
│   ├── EX02.java    # Calculadora de Notas
│   ├── EX03.java    # Lista de Compras
│   ├── EX04.java    # Analisador de Texto
│   ├── EX05.java    # Jogo de Adivinhação
│   └── EX06.java    # Sistema de Biblioteca
└── Sudoku/
    └── Sudoku2.java # Jogo de Sudoku
```

## Tecnologias
- Java
- `ArrayList`, `Scanner`, `Random`
- `LocalDate` (java.time)
- Programação estruturada e orientada a objetos
- Validação de input e tratamento de erros

## Autora
Kelly Piacheski de Abreu Wolmer
