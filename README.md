# ESTouro - Tower Defense Game 🎈

Projeto desenvolvido no âmbito da Unidade Curricular de Padrões de Desenho de Software. O jogo consiste num clássico Tower Defense onde o objetivo é rebentar balões utilizando torres estratégicas.

> **Nota importante:** A estrutura base e o motor gráfico do jogo foram fornecidos pelo docente. O meu contributo focou-se exclusivamente na **implementação de Padrões de Desenho (Design Patterns)** para tornar o sistema modular e extensível.

## 🧠 Padrões de Desenho Implementados

### 1. Decorator (Bloons)
Utilizado para adicionar propriedades aos balões (como armadura ou escudos) de forma dinâmica, sem alterar a classe base `Bloon`.
*Ficheiros:* `BloonDecorator.java`, `BloonArmadura.java`, `BloonEscudo.java`.

### 2. Factory Method (Creators)
Implementado para a criação de instâncias de Torres e Balões, permitindo que o sistema decida qual objeto instanciar em tempo de execução.
*Ficheiros:* `TorreCreator.java`, `BloonCreator.java`.

### 3. Observer
Utilizado para gerir a interação entre os elementos do mundo, onde as torres "observam" a posição dos balões para disparar.
*Ficheiros:* `BloonObserver.java`.

### 4. Visitor
Implementado para operações de persistência (Save/Load) e manipulação, permitindo adicionar novas operações às torres sem modificar as suas classes.
*Ficheiros:* `TorreVisitor.java`, `ManipuladorTorreVisitor.java`.

### 5. Strategy
Define algoritmos de ataque diferentes para as torres (Atacar o mais forte, o mais perto, o primeiro, etc.).
*Ficheiros:* `EstrategiaAtaque.java`.

## 🛠️ Tecnologias
* **Linguagem:** Java
* **Interface:** Java Swing / AWT
