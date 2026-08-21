# TMDB Movie Search

Aplicativo Android desenvolvido em **Kotlin** com **Jetpack Compose** que consome a API pública do **The Movie Database (TMDB)** para buscar filmes dinamicamente por título, exibindo os resultados de forma organizada com tratamento de estados de carregamento e erro.

Projeto desenvolvido para a disciplina de Desenvolvimento Mobile — curso de Análise e Desenvolvimento de Sistemas (ADS), IFTM Campus Patrocínio.

---

## 📱 Funcionalidades

- Campo de busca com pesquisa dinâmica (a cada caractere digitado)
- Consumo de API REST via Retrofit
- Parse de JSON com kotlinx.serialization
- Carregamento e cache de imagens (pôsteres) com Coil
- Tratamento explícito dos estados da tela:
  - **Idle** — estado inicial, antes de qualquer busca
  - **Loading** — indicador de carregamento durante a requisição
  - **Success** — lista de resultados (ou mensagem de "nenhum resultado encontrado")
  - **Error** — mensagem amigável em caso de falha de rede ou da API
- Diálogo de detalhes ao tocar em um filme, exibindo pôster, data de lançamento, nota média e sinopse completa

---

## 🌐 API utilizada

**The Movie Database (TMDB) API**
🔗 https://www.themoviedb.org/documentation/api

O app consome o endpoint de busca de filmes:

```
GET https://api.themoviedb.org/3/search/movie
```

**Parâmetros utilizados:**

| Parâmetro | Descrição |
|---|---|
| `api_key` | Chave de autenticação da API (obrigatória) |
| `query` | Texto digitado pelo usuário na busca |
| `language` | Idioma dos resultados (`pt-BR`) |

Os pôsteres dos filmes são carregados a partir da CDN de imagens do próprio TMDB:
```
https://image.tmdb.org/t/p/w500/{poster_path}
```

---

## 🏗️ Arquitetura e tecnologias

| Camada | Tecnologia |
|---|---|
| UI | Jetpack Compose + Material 3 |
| Estado / lógica | ViewModel + StateFlow |
| Rede | Retrofit + OkHttp |
| Parse de JSON | kotlinx.serialization |
| Imagens | Coil 3 |
| Linguagem | Kotlin |

O projeto segue uma separação simples de responsabilidades:

```
app/src/main/java/com/example/tmdb/
├── data/
│   ├── model/       -> Modelos de dados (Movie, MovieSearchResponse)
│   └── remote/      -> Configuração do Retrofit e definição dos endpoints
├── ui/
│   ├── MovieUiState.kt      -> Estados possíveis da tela (Idle/Loading/Success/Error)
│   ├── MovieViewModel.kt    -> Lógica de busca e gerenciamento de estado
│   ├── SearchScreen.kt      -> Tela principal (campo de busca + lista + diálogo)
│   ├── MovieListItem.kt     -> Item individual da lista de resultados
│   └── theme/               -> Cores, tipografia e tema do app
└── MainActivity.kt
```

---

## ⚙️ Como executar o projeto

### Pré-requisitos

- Android Studio (versão recente, com suporte a AGP 9.x)
- JDK 11 ou superior
- Uma chave de API gratuita do TMDB

### Passo a passo

1. **Clone o repositório:**
   ```bash
   git clone https://github.com/SEU_USUARIO/tmdb-movie-search-compose.git
   ```

2. **Obtenha uma API key gratuita do TMDB:**
   - Crie uma conta em https://www.themoviedb.org/
   - Acesse **Configurações → API** e solicite uma chave do tipo "Developer"

3. **Configure a chave localmente:**

   Na raiz do projeto, crie (ou edite) o arquivo `local.properties` e adicione a linha:
   ```properties
   TMDB_API_KEY=sua_chave_aqui
   ```

   > Esse arquivo não é versionado pelo Git (está no `.gitignore`) — cada pessoa que rodar o projeto precisa da própria chave.

4. **Abra o projeto no Android Studio**, aguarde a sincronização do Gradle e execute em um emulador ou dispositivo físico (mínimo Android 7.0 / API 24).

---



## 🎥 Vídeo demonstrativo

🔗 https://youtu.be/UkaRWkYdb78

---

## 👤 Autor

**Vitor Augusto Correia dos Reis**
Curso de Análise e Desenvolvimento de Sistemas — IFTM Campus Patrocínio
