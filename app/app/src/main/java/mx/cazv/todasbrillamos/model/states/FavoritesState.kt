package mx.cazv.todasbrillamos.model.states

import mx.cazv.todasbrillamos.model.models.Favorites

data class FavoritesState(
    val favorites: Favorites = Favorites("", emptyList()),
    val isLoading: Boolean = false,
)