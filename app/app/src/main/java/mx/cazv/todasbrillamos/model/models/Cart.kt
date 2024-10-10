package mx.cazv.todasbrillamos.model.models

/**
 * Data class que representa un producto en el carrito.
 * @author Carlos Zamudio
 *
 * @property name El nombre del producto.
 * @property price El precio del producto.
 * @property product_id El ID del producto.
 * @property hash El hash de la imagen del producto.
 * @property type Extensión de la imagen del producto (jpg, png, etc.).
 * @property stock La cantidad de productos en existencia.
 *
 */
data class CartProduct(
    val name: String,
    val price: Double,
    val product_id: Int,
    val hash: String,
    val type: String,
    val stock: Int
)

/**
 * Data class que representa un elemento en el carrito.
 * @property quantity La cantidad del producto en el carrito.
 * @param product El producto en el carrito.
 */
data class CartItem(
    val quantity: Int,
    val product: CartProduct
)

/**
 * Data class que representa la respuesta cuando se obtiene todo el carrito.
 * @property folder El nombre de la carpeta del carrito.
 * @property cart La lista de elementos en el carrito.
 */
data class CartResponse(
    val folder: String,
    val cart: List<CartItem>
)