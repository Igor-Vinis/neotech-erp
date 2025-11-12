[README_NeoTech_ERP.md](https://github.com/user-attachments/files/23490953/README_NeoTech_ERP.md)
# 🧠 NeoTech ERP

> Sistema ERP modular desenvolvido em **Java**, com foco em boas práticas de **Orientação a Objetos (POO)**, **arquitetura em camadas** e **manutenibilidade de código**.  
> O projeto tem como propósito simular um sistema real de gestão de vendas, clientes e produtos.

---

## 🚀 Objetivos do Projeto

- Consolidar os fundamentos intermediários/avançados de **Java** (Collections, Streams, Exceptions, Modularização, etc.).
- Aplicar uma **arquitetura limpa**, separando responsabilidades por camadas.
- Evoluir gradualmente para um **sistema realista e escalável**, com persistência em arquivos e, futuramente, banco de dados.
- Servir como base para projetos futuros em **Spring Boot**, **Kafka**, **entre outros**.

---

## 🧩 Estrutura do Projeto

```
neotech-erp/
 ├─ src/main/java/com/neotech/erp/
 │   ├─ app/           → Ponto de entrada da aplicação (MainApp.java)
 │   ├─ domain/        → Entidades centrais do sistema (POO pura)
 │   │   ├─ Cliente.java
 │   │   ├─ Produto.java
 │   │   ├─ ItemVenda.java
 │   │   └─ Venda.java
 │   ├─ dao/           → Camada de acesso a dados (vazia por enquanto)
 │   ├─ service/       → Regras de negócio e validações
 │   └─ util/          → Utilitários genéricos (ex: formatadores, validadores)
 ├─ data/              → Arquivos CSV temporários (clientes, produtos, vendas)
 └─ README.md
```

---

## ⚙️ Tecnologias Utilizadas

| Categoria | Ferramenta |
|------------|-------------|
| Linguagem  | Java 17+ |
| IDE        | IntelliJ IDEA |
| Paradigma  | Programação Orientada a Objetos (POO) |
| Persistência | Arquivos `.csv` (futuro: PostgreSQL) |

---

## 🧠 Conceitos Envolvidos

- **Encapsulamento, Herança e Polimorfismo**
- **Composição de Objetos** (`Venda` contém `ItemVenda`, que referencia `Produto`)
- **Boas práticas de camadas** 
- **Tratamento de exceções customizadas**
- **Streams API para manipulação de coleções**
- **Separação entre lógica de negócio e persistência**

---

## 🧪 Próximos Passos (Roteiro Futuro)

- [ ] Implementar persistência em arquivo CSV.  
- [ ] Criar camada DAO genérica.  
- [ ] Implementar testes simples de integração.  
- [ ] Adicionar menu interativo em console (`MainApp`).  
- [ ] Converter para aplicação Spring Boot com REST API.  
- [ ] Implementar microserviço de relatórios (Kafka ou RabbitMQ).

---

## 👨‍💻 Autor

**Igor Vinícius**  
💡 Desenvolvedor em formação, apaixonado por IA, nanotecnologia e engenharia de software.  
📚 Projeto pessoal de aprendizado com foco em excelência técnica e domínio das bases da computação.

---

## 🧬 Licença

Este projeto é de uso educacional e pessoal, sem fins comerciais.  
Você pode estudar, modificar e compartilhar, desde que mantenha os créditos ao autor original.
