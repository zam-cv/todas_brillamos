package mx.cazv.todasbrillamos.model.models

data class CartProduct(
    val name: String,
    val price: Double,
    val product_id: Int,
    val hash: String,
    val type: String
)

data class CartItem(
    val quantity: Int,
    val product: CartProduct
)

data class CartResponse(
    val folder: String,
    val cart: List<CartItem>
)