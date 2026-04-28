# Hub de Dados SSD

Sistema destinado à consolidação, tratamento e análise de dados de saúde provenientes de instituições parceiras, com o objetivo de apoiar a estratégia da Secretaria de Saúde (SSD) por meio de indicadores integrados.

---

## Visão Geral

O Hub de Dados SSD recebe planilhas Excel de três fontes — **Einstein**, **Fiocruz** e **Núcleo Telessaúde** — processa e persiste esses dados em banco relacional, aplica regras de negócio e normalização, e disponibiliza uma visão consolidada para subsidiar decisões estratégicas em saúde.

**Fluxo principal:**

```
Planilha Excel (3 fontes)
        ↓
   Leitura e Validação
        ↓
   Normalização e Tratamento por Fonte
        ↓
   Persistência no Banco
        ↓
   Consolidação Cruzada
        ↓
   Relatórios Estratégicos
```

---

## Arquitetura

O projeto segue a **Arquitetura Hexagonal (Ports & Adapters)**:

```
┌─────────────────────────────────────────────────┐
│                    DOMÍNIO                      │
│   Entidades · Regras de Negócio · Serviços      │
└───────────────────┬─────────────────────────────┘
                    │ Ports (interfaces)
       ┌────────────┴────────────┐
       ↓                         ↓
 Inbound Adapters           Outbound Adapters
 (Leitura Excel, API)       (H2/JPA, Relatórios)
```

**Estrutura de pacotes:**
```
src/main/java/br/gov/ssd/datahub/
├── domain/
│   ├── model/          # Entidades e objetos de valor
│   ├── service/        # Serviços de domínio
│   └── exception/      # Exceções de domínio
├── application/
│   ├── port/
│   │   ├── in/         # Ports de entrada (use cases)
│   │   └── out/        # Ports de saída (repositórios)
│   └── usecase/        # Implementações dos use cases
└── infrastructure/
    ├── adapter/
    │   ├── in/         # Adapters de entrada (Excel, REST)
    │   └── out/        # Adapters de saída (JPA, H2)
    └── config/         # Configurações Spring
```

---

## Stack Tecnológica

| Tecnologia | Uso |
|---|---|
| Java 21 | Linguagem principal |
| Spring Boot | Framework base |
| Spring Data JPA | Persistência |
| H2 Database | Banco de dados (desenvolvimento) |
| Apache POI | Leitura de planilhas Excel |
| Lombok | Redução de boilerplate |
| JUnit 5 + AssertJ | Testes |
| Maven | Build e dependências |

> **Nota:** O banco H2 pode ser apagado e resetado livremente durante o desenvolvimento. Migrações incrementais serão aplicadas apenas na preparação para produção.

---

## Cronograma e Acompanhamento

### Fase 0 — Setup do Projeto
> Objetivo: Estrutura base funcionando, pronta para receber as implementações das fontes.

- [x] Criar projeto Spring Boot com estrutura de pacotes hexagonal
- [x] Configurar H2 (modo arquivo, reset por propriedade)
- [x] Configurar Spring Data JPA
- [x] Adicionar dependência Apache POI
- [x] Configurar Lombok e utilitários
- [x] Criar estrutura base de testes (JUnit 5, AssertJ)
- [x] Configurar repositório GitHub (branch strategy, .gitignore)
- [ ] Criar script de reset do banco para desenvolvimento

---

### Fase 1 — Einstein: Importação + Tratamento + Validação
> Objetivo: Dados da Einstein limpos, validados e persistidos no banco.

**Análise e Mapeamento**
- [ ] Receber e analisar planilha modelo da Einstein
- [ ] Documentar dicionário de dados (colunas, tipos, obrigatoriedade)
- [ ] Mapear colunas da planilha para entidades de domínio

**Domínio**
- [ ] Criar entidades de domínio da Einstein
- [ ] Definir objetos de valor (CID, CPF, datas, etc.)
- [ ] Implementar regras de validação no domínio

**Implementação**
- [ ] Criar port de entrada `ImportarEinsteinUseCase`
- [ ] Criar port de saída `EinsteinRepositoryPort`
- [ ] Implementar adapter de leitura Excel (`EinsteinExcelAdapter`)
- [ ] Implementar regras de normalização (nomes, datas, CIDs)
- [ ] Implementar deduplicação de registros
- [ ] Implementar adapter de persistência JPA (`EinsteinJpaAdapter`)

**Testes e Validação**
- [ ] Criar testes unitários das regras de domínio
- [ ] Criar testes de integração (leitura Excel → persistência H2)
- [ ] Validar dados persistidos com especialista de domínio
- [ ] Corrigir ajustes apontados na validação

---

### Fase 2 — Fiocruz: Importação + Tratamento + Validação
> Objetivo: Dados da Fiocruz limpos, validados e persistidos no banco.

**Análise e Mapeamento**
- [ ] Receber e analisar planilha modelo da Fiocruz
- [ ] Documentar dicionário de dados
- [ ] Mapear colunas para entidades de domínio
- [ ] Identificar campos equivalentes aos da Einstein (base para consolidação futura)

**Domínio**
- [ ] Criar/adaptar entidades de domínio da Fiocruz
- [ ] Implementar regras de validação específicas da Fiocruz

**Implementação**
- [ ] Criar port de entrada `ImportarFiocruzUseCase`
- [ ] Criar port de saída `FiocruzRepositoryPort`
- [ ] Implementar adapter de leitura Excel (`FiocruzExcelAdapter`)
- [ ] Implementar regras de normalização específicas da Fiocruz
- [ ] Implementar deduplicação de registros
- [ ] Implementar adapter de persistência JPA (`FiocruzJpaAdapter`)

**Testes e Validação**
- [ ] Criar testes unitários das regras de domínio
- [ ] Criar testes de integração
- [ ] Validar dados persistidos com especialista de domínio
- [ ] Corrigir ajustes apontados na validação

---

### Fase 3 — Núcleo Telessaúde: Importação + Tratamento + Validação
> Objetivo: Dados do Núcleo Telessaúde limpos, validados e persistidos no banco.

**Análise e Mapeamento**
- [ ] Receber e analisar planilha modelo do Núcleo Telessaúde
- [ ] Documentar dicionário de dados
- [ ] Mapear colunas para entidades de domínio
- [ ] Identificar campos equivalentes às fontes anteriores

**Domínio**
- [ ] Criar/adaptar entidades de domínio do Telessaúde
- [ ] Implementar regras de validação específicas do Telessaúde

**Implementação**
- [ ] Criar port de entrada `ImportarTelessaudeUseCase`
- [ ] Criar port de saída `TelessaudeRepositoryPort`
- [ ] Implementar adapter de leitura Excel (`TelessaudeExcelAdapter`)
- [ ] Implementar regras de normalização específicas do Telessaúde
- [ ] Implementar deduplicação de registros
- [ ] Implementar adapter de persistência JPA (`TelessaudeJpaAdapter`)

**Testes e Validação**
- [ ] Criar testes unitários das regras de domínio
- [ ] Criar testes de integração
- [ ] Validar dados persistidos com especialista de domínio
- [ ] Corrigir ajustes apontados na validação

---

### Fase 4 — Consolidação Cruzada
> Objetivo: Visão unificada e cruzada das três fontes, com indicadores estratégicos.

**Definição**
- [ ] Reunião com SSD para definir indicadores estratégicos necessários
- [ ] Definir critérios de correlação entre fontes (chave de cruzamento)
- [ ] Documentar regras de consolidação

**Domínio**
- [ ] Criar entidades de consolidação
- [ ] Implementar regras de cruzamento Einstein × Fiocruz × Telessaúde
- [ ] Implementar deduplicação cruzada (mesmo paciente/evento em múltiplas fontes)

**Implementação**
- [ ] Criar use case `ConsolidarDadosUseCase`
- [ ] Implementar lógica de correlação entre fontes
- [ ] Persistir dados consolidados
- [ ] Implementar indicadores calculados

**Testes e Validação**
- [ ] Criar testes de consolidação com dados das três fontes
- [ ] Validar indicadores com especialista de domínio e SSD
- [ ] Ajustar regras conforme validação

---

### Fase 5 — Relatórios e Entrega Estratégica
> Objetivo: Dados consolidados disponíveis para consumo e decisão estratégica.

**API e Consultas**
- [ ] Criar endpoints REST de consulta por fonte
- [ ] Criar endpoints de indicadores consolidados
- [ ] Implementar filtros por período, fonte e indicador
- [ ] Documentar API (Swagger/OpenAPI)

**Relatórios**
- [ ] Definir formato de saída com SSD (API, planilha exportada, relatório)
- [ ] Implementar geração de relatório consolidado
- [ ] Validar relatório com SSD

**Preparação para Produção**
- [ ] Revisar configurações para migração H2 → PostgreSQL
- [ ] Criar scripts de migração de schema (Flyway ou Liquibase)
- [ ] Revisar segurança e autenticação
- [ ] Documentação técnica final

---

## Status das Fases

| Fase | Descrição | Status |
|---|---|---|
| Fase 0 | Setup do Projeto | Em andamento |
| Fase 1 | Einstein | Não iniciada |
| Fase 2 | Fiocruz | Não iniciada |
| Fase 3 | Núcleo Telessaúde | Não iniciada |
| Fase 4 | Consolidação Cruzada | Não iniciada |
| Fase 5 | Relatórios e Entrega | Não iniciada |

---

## Como Executar (Desenvolvimento)

```bash
# Clonar o repositório
git clone https://github.com/sergioajiki/ssd-data-hub.git

# Build
./mvnw clean install

# Executar
./mvnw spring-boot:run
```

> O banco H2 é inicializado automaticamente. Para resetar, apague o arquivo `data/datahub.mv.db` ou configure `spring.datasource.url=jdbc:h2:mem:datahub` para modo em memória.

---

## Equipe

| Papel | Responsável |
|---|---|
| Desenvolvedor | SAjiki |
