package com.example.tmdb.ui

import com.example.tmdb.data.model.Movie

/**
 * Representa todos os estados possíveis da tela de busca de filmes.
 *
 * Usar uma "sealed interface" garante que só existem esses estados
 * definidos aqui — o Compose consegue tratar cada caso (when) sem
 * precisar de um "else", porque o Kotlin sabe que não existe mais
 * nenhuma outra possibilidade.
 *
 * É essa a peça que resolve o requisito da atividade de "tratamento
 * adequado dos estados de carregamento e erros".
 */
sealed interface MovieUiState {

    /**
     * Estado inicial, antes do usuário digitar qualquer busca.
     * A tela mostra algo como "Digite o nome de um filme para começar".
     */
    data object Idle : MovieUiState

    /**
     * A requisição está em andamento (esperando resposta da API).
     * A tela mostra um indicador de carregamento (spinner).
     */
    data object Loading : MovieUiState

    /**
     * A requisição deu certo e retornou uma lista de filmes.
     * Se a lista vier vazia, é porque a busca não encontrou nada
     * (ex: usuário digitou um nome que não existe) — a tela trata
     * isso separado da lista com itens, mostrando uma mensagem
     * de "nenhum resultado encontrado".
     */
    data class Success(val movies: List<Movie>) : MovieUiState

    /**
     * A requisição falhou (sem internet, erro do servidor, API key
     * inválida, etc). Guardamos a mensagem de erro para exibir ao
     * usuário de forma legível.
     */
    data class Error(val message: String) : MovieUiState
}