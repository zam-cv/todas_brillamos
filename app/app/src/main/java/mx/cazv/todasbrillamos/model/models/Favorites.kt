package mx.cazv.todasbrillamos.model.models

data class Favorites(
    val folder: String,
    val favorites: List<ProductRaw>
)