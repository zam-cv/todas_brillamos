package mx.cazv.todasbrillamos.model.states

import mx.cazv.todasbrillamos.model.models.CartItem


/**
 * Data class que representa el estado de un carrito de compras.
 * @author Carlos Zamudio
 *
 * @property folder El nombre de la carpeta asociada al carrito, si existe.
 * @property cart Lista de elementos (productos) en el carrito de compras.
 * @property totalPrice El precio total de todos los productos en el carrito.
 * @property isLoading Indicador de si el estado está en proceso de carga o no.
 * @property error Posible mensaje de error en caso de que ocurra algún problema.
 */
data class CartState(
    val folder: String = "",
    val cart: List<CartItem> = emptyList(),
    val totalPrice: Double = 0.0,
    val isLoading: Boolean = false,
    val error: String? = null
)