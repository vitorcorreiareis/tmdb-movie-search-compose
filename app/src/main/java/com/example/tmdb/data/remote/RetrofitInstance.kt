package com.example.tmdb.data.remote

import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

/**
 * Objeto singleton (só existe UMA instância dele em todo o app) responsável
 * por criar e configurar o cliente Retrofit que fala com a API do TMDB.
 *
 * Usar "object" em vez de "class" garante que a configuração (URL base,
 * conversor JSON, etc) é feita uma única vez, e reaproveitada em toda
 * chamada de rede.
 */
object RetrofitInstance {

    // URL base da API do TMDB. Toda chamada (ex: "search/movie") é
    // concatenada a partir daqui pelo Retrofit.
    private const val BASE_URL = "https://api.themoviedb.org/3/"

    /**
     * Configura o parser JSON (kotlinx.serialization).
     * ignoreUnknownKeys = true evita que o app quebre se a API do TMDB
     * devolver algum campo que não mapeamos no Movie.kt.
     */
    private val json = Json {
        ignoreUnknownKeys = true
    }

    /**
     * Interceptor de log: imprime no Logcat a URL, os headers e o corpo
     * de cada requisição/resposta. Útil para debugar erros de rede.
     */
    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    /**
     * Cliente HTTP (OkHttp) usado pelo Retrofit por baixo dos panos,
     * já com o interceptor de log conectado.
     */
    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()

    /**
     * Instância única do Retrofit, configurada com:
     * - a URL base da API
     * - o cliente HTTP (com logging)
     * - o conversor que transforma JSON <-> data class Kotlin
     */
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .build()

    /**
     * Instância pronta da nossa interface de API (TmdbApiService).
     * É isso que o ViewModel vai usar para fazer as chamadas.
     */
    val api: TmdbApiService by lazy {
        retrofit.create(TmdbApiService::class.java)
    }
}