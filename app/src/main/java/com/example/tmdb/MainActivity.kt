package com.example.tmdb

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.tmdb.ui.SearchScreen
import com.example.tmdb.ui.theme.TmdbTheme

/**
 * Ponto de entrada do app. É a única Activity do projeto — como usamos
 * Jetpack Compose, não precisamos de várias Activities/Fragments para
 * várias telas; tudo é gerenciado por Composables dentro de setContent.
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Permite que o conteúdo do app desenhe por trás das barras de
        // status/navegação do sistema, dando um visual mais moderno.
        enableEdgeToEdge()

        setContent {
            // TmdbTheme aplica as cores/tipografia definidas em ui/theme
            // (gerado automaticamente pelo wizard do Android Studio).
            TmdbTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // SearchScreen é a tela principal do app (arquivo 7),
                    // onde fica o campo de busca e a lista de resultados.
                    SearchScreen()
                }
            }
        }
    }
}