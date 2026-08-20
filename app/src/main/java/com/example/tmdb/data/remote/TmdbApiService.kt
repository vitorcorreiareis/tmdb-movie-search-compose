package com.example.tmdb.data.remote

import com.example.tmdb.data.model.MovieSearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Interface que descreve os endpoints da API do TMDB que o app usa.
 *
 * O Retrofit lê essa interface e gera automaticamente, em tempo de
 * execução, o código que faz a chamada HTTP de verdade. Você nunca
 * implementa essa interface manualmente — o Retrofit cria uma
 * implementação "por baixo dos panos".
 */
interface TmdbApiService {

    /**
     * Busca filmes por texto.
     * Endpoint real: GET https://api.themoviedb.org/3/search/movie
     *
     * @param apiKey a chave de autenticação do TMDB (obrigatória em toda chamada)
     * @param query o texto digitado pelo usuário no campo de busca
     * @param language define o idioma dos resultados (título, sinopse, etc)
     *
     * A função é "suspend" porque é assíncrona — ela roda numa coroutine
     * e não trava a thread principal (UI) enquanto espera a resposta da rede.
     */
    @GET("search/movie")
    suspend fun searchMovies(
        @Query("api_key") apiKey: String,
        @Query("query") query: String,
        @Query("language") language: String = "pt-BR"
    ): MovieSearchResponse
}