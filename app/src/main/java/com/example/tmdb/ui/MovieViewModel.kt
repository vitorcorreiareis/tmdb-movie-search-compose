package com.example.tmdb.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tmdb.BuildConfig
import com.example.tmdb.data.remote.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel responsável por toda a lógica de busca de filmes.
 *
 * Ele existe para separar a lógica de negócio (chamar a API, tratar
 * erros) da tela (Composable). A tela só "observa" o estado exposto
 * aqui e reage a mudanças — ela nunca faz a chamada de rede diretamente.
 *
 * Isso é importante também porque o ViewModel sobrevive a mudanças de
 * configuração (ex: girar a tela do celular), então a busca não se
 * perde nem refaz sozinha quando o usuário rotaciona o aparelho.
 */
class MovieViewModel : ViewModel() {

    // A chave vem do BuildConfig, que por sua vez lê o local.properties
    // em tempo de build — assim a chave nunca fica escrita no código-fonte
    // que vai pro GitHub.
    private val apiKey = BuildConfig.TMDB_API_KEY

    // StateFlow privado e mutável: só o ViewModel pode alterar o estado.
    private val _uiState = MutableStateFlow<MovieUiState>(MovieUiState.Idle)

    // Versão pública e somente-leitura, exposta para a tela observar.
    // A tela nunca consegue alterar o estado diretamente, só ler.
    val uiState: StateFlow<MovieUiState> = _uiState.asStateFlow()

    /**
     * Dispara a busca de filmes pelo texto informado.
     *
     * viewModelScope.launch inicia uma coroutine atrelada ao ciclo de
     * vida do ViewModel — se a tela for destruída no meio da busca,
     * a coroutine é cancelada automaticamente, evitando vazamento
     * de memória ou crash por tentar atualizar uma tela que não existe mais.
     */
    fun searchMovies(query: String) {
        // Evita disparar busca com campo vazio
        if (query.isBlank()) {
            _uiState.value = MovieUiState.Idle
            return
        }

        viewModelScope.launch {
            _uiState.value = MovieUiState.Loading

            try {
                val response = RetrofitInstance.api.searchMovies(
                    apiKey = apiKey,
                    query = query
                )

                _uiState.value = MovieUiState.Success(response.results)

            } catch (e: Exception) {
                // Captura qualquer falha: sem internet, timeout, erro do
                // servidor, JSON inesperado, etc. A mensagem exibida ao
                // usuário é genérica e amigável, mas o erro técnico
                // completo (e.message) ainda fica disponível se você
                // quiser logar no Logcat para debugar.
                _uiState.value = MovieUiState.Error(
                    message = "Não foi possível buscar os filmes. Verifique sua conexão e tente novamente."
                )
            }
        }
    }
}