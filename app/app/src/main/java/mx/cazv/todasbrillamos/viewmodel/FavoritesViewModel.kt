package mx.cazv.todasbrillamos.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import mx.cazv.todasbrillamos.model.models.ProductRaw
import mx.cazv.todasbrillamos.model.services.FavoritesService
import mx.cazv.todasbrillamos.model.states.FavoritesState

/**
 * ViewModel para gestionar el estado de los favoritos.
 * @author Carlos Zamudio
 */
class FavoritesViewModel : ViewModel() {
    private val favoritesService = FavoritesService()

    private val _state = MutableStateFlow(FavoritesState())
    val state: StateFlow<FavoritesState> = _state.asStateFlow()

    /**
     * Carga la lista de productos favoritos.
     *
     * @param token El token de autenticación.
     */
    fun loadFavorites(token: String) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            try {
                val favoritesResponse = favoritesService.favorites(token)
                _state.value = _state.value.copy(
                    favorites = favoritesResponse,
                    isLoading = false
                )
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    isLoading = false
                )
            }
        }
    }

    /**
     * Verifica si un producto existe en la lista de favoritos.
     *
     * @param token El token de autenticación.
     * @param productId El ID del producto.
     * @return `true` si el producto existe en la lista de favoritos, `false` en caso contrario.
     */
    suspend fun exists(token: String, productId: Int): Boolean {
        var exists = _state.value.favorites.favorites.any { it.id == productId }

        if (exists) {
            return true
        } else {
            exists = favoritesService.exists(token, productId).exists
            return exists
        }
    }

    /**
     * Añade un producto a la lista de favoritos.
     *
     * @param token El token de autenticación.
     * @param favorite El producto a añadir.
     */
    fun addFavorite(token: String, favorite: ProductRaw) {
        viewModelScope.launch {
            try {
                favoritesService.addFavorite(token, favorite.id)
                val updatedFavorites = _state.value.favorites.favorites.toMutableList()
                updatedFavorites.add(favorite)
                _state.value = _state.value.copy(favorites =
                    _state.value.favorites.copy(favorites = updatedFavorites)
                )
            } catch (e: Exception) {
                // TODO: Handle error
            }
        }
    }

    /**
     * Elimina un producto de la lista de favoritos.
     *
     * @param token El token de autenticación.
     * @param id El ID del producto.
     */
    fun deleteProductFromCart(token: String, id: Int) {
        viewModelScope.launch {
            try {
                favoritesService.removeFavorite(token, id)
                val updatedFavorites = _state.value.favorites.favorites.filter { it.id != id }
                _state.value = _state.value.copy(favorites =
                    _state.value.favorites.copy(favorites = updatedFavorites))
            } catch (e: Exception) {
                // TODO: Handle error
            }
        }
    }
}