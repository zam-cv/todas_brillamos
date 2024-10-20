package mx.cazv.todasbrillamos.model.states

import mx.cazv.todasbrillamos.model.models.Favorites

/**
 * Data class que representa el estado de los favoritos.
 *
 * @property favorites La lista de productos favoritos.
 * @property isLoading Indica si la lista de favoritos se está cargando.
 */
data class FavoritesState(
    val favorites: Favorites = Favorites("", emptyList()),
    val isLoading: Boolean = false,
)