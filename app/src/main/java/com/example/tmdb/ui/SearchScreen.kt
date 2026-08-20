package com.example.tmdb.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import com.example.tmdb.data.model.Movie

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(viewModel: MovieViewModel = viewModel()) {

    var query by remember { mutableStateOf("") }
    val uiState by viewModel.uiState.collectAsState()

    // Guarda qual filme foi clicado. Enquanto for null, nenhum diálogo
    // aparece. Quando o usuário toca em um item, esse valor é preenchido
    // e o AlertDialog (definido mais abaixo) é exibido automaticamente.
    var selectedMovie by remember { mutableStateOf<Movie?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Buscar Filmes") })
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            TextField(
                value = query,
                onValueChange = { newText ->
                    query = newText
                    viewModel.searchMovies(newText)
                },
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Search, contentDescription = "Buscar")
                },
                placeholder = { Text("Digite o nome de um filme...") },
                modifier = Modifier.fillMaxWidth()
            )

            when (val state = uiState) {

                is MovieUiState.Idle -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Digite o nome de um filme para começar a busca.",
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }

                is MovieUiState.Loading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }

                is MovieUiState.Success -> {
                    if (state.movies.isEmpty()) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "Nenhum filme encontrado para essa busca.",
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                    } else {
                        LazyColumn(
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                            contentPadding = PaddingValues(vertical = 8.dp)
                        ) {
                            items(state.movies) { movie ->
                                // Ao clicar no item, guarda o filme selecionado —
                                // isso dispara a exibição do diálogo mais abaixo.
                                MovieListItem(
                                    movie = movie,
                                    onClick = { selectedMovie = movie }
                                )
                            }
                        }
                    }
                }

                is MovieUiState.Error -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = state.message,
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
            }
        }
    }

    // Exibe o diálogo de detalhes somente quando há um filme selecionado.
    // Ao fechar (botão "Fechar" ou tocar fora), selectedMovie volta a
    // null e o diálogo desaparece.
    selectedMovie?.let { movie ->
        AlertDialog(
            onDismissRequest = { selectedMovie = null },
            confirmButton = {
                TextButton(onClick = { selectedMovie = null }) {
                    Text("Fechar")
                }
            },
            title = {
                Text(movie.title)
            },
            text = {
                Column {
                    AsyncImage(
                        model = movie.fullPosterUrl,
                        contentDescription = "Pôster do filme ${movie.title}",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .padding(bottom = 8.dp),
                        contentScale = ContentScale.Fit
                    )
                    Text(
                        text = "Lançamento: ${movie.releaseDate ?: "Data não informada"}",
                        style = MaterialTheme.typography.bodySmall
                    )
                    Text(
                        text = "Nota: ${movie.voteAverage ?: "N/A"}",
                        style = MaterialTheme.typography.bodySmall
                    )
                    Text(
                        text = movie.overview ?: "Sinopse não disponível.",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
            }
        )
    }
}