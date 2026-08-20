package com.example.tmdb.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Representa a resposta completa do endpoint de busca do TMDB
 * (GET /search/movie). A API retorna os resultados paginados,
 * mas pra esse app só vamos usar a lista "results".
 */
@Serializable
data class MovieSearchResponse(
    val page: Int,
    val results: List<Movie>,
    @SerialName("total_results")
    val totalResults: Int,
    @SerialName("total_pages")
    val totalPages: Int
)

/**
 * Representa um único filme retornado pela API.
 *
 * Os nomes dos campos no JSON do TMDB vêm em snake_case
 * (ex: "poster_path"), mas em Kotlin usamos camelCase por
 * convenção. O @SerialName faz essa "tradução" automaticamente
 * na hora do parse — sem ele, o kotlinx.serialization não
 * encontraria o campo e o valor ficaria nulo.
 */
@Serializable
data class Movie(
    val id: Int,
    val title: String,

    // Pode vir vazio, por isso é nullable
    val overview: String?,

    @SerialName("poster_path")
    val posterPath: String?,

    @SerialName("release_date")
    val releaseDate: String?,

    @SerialName("vote_average")
    val voteAverage: Double?
) {
    /**
     * O TMDB não devolve a URL completa da imagem, só o caminho
     * relativo (ex: "/abc123.jpg"). Essa propriedade monta a URL
     * final que o Coil vai usar pra carregar o pôster.
     * "w500" define a largura da imagem em pixels — dá pra trocar
     * por "w200", "original", etc, dependendo da qualidade que
     * quiser exibir.
     */
    val fullPosterUrl: String?
        get() = posterPath?.let { "https://image.tmdb.org/t/p/w500$it" }
}