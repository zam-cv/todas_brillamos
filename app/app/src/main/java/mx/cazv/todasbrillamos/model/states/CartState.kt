package mx.cazv.todasbrillamos.model.states

import mx.cazv.todasbrillamos.model.models.CartItem

data class CartState(
    val folder: String = "",
    val cart: List<CartItem> = emptyList(),
    val totalPrice: Double = 0.0,
    val isLoading: Boolean = false,
    val error: String? = null
)