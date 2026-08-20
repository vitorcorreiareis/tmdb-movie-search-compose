package com.example.tmdb.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.tmdb.data.model.Movie

/**
 * Representa visualmente UM filme na lista de resultados: pôster,
 * título, data de lançamento e nota média.
 *
 * O parâmetro onClick permite que o item seja tocado — a lógica de
 * "o que acontece ao clicar" fica na tela (SearchScreen), não aqui.
 * Isso mantém o componente reutilizável: ele não precisa saber se
 * o clique abre um diálogo, navega para outra tela, etc.
 */
@Composable
fun MovieListItem(movie: Movie, onClick: () -> Unit) {
    Card(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Row(
            modifier = Modifier.padding(8.dp)
        ) {
            AsyncImage(
                model = movie.fullPosterUrl,
                contentDescription = "Pôster do filme ${movie.title}",
                modifier = Modifier.width(90.dp),
                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier.padding(start = 12.dp)
            ) {
                Text(
                    text = movie.title,
                    style = MaterialTheme.typography.titleMedium
                )

                Text(
                    text = "Lançamento: ${movie.releaseDate ?: "Data não informada"}",
                    style = MaterialTheme.typography.bodySmall
                )

                Text(
                    text = "Nota: ${movie.voteAverage ?: "N/A"}",
                    style = MaterialTheme.typography.bodySmall
                )

                movie.overview?.let { synopsis ->
                    Text(
                        text = synopsis,
                        style = MaterialTheme.typography.bodySmall,
                        maxLines = 3
                    )
                }
            }
        }
    }
}