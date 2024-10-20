package mx.cazv.todasbrillamos.model.models

/**
 * Data class que representa un producto en la lista de favoritos.
 * @author Carlos Zamudio
 *
 * @property folder El nombre de la carpeta del producto.
 * @property favorites La lista de productos en la lista de favoritos.
 */
data class Favorites(
    val folder: String,
    val favorites: List<ProductRaw>
)