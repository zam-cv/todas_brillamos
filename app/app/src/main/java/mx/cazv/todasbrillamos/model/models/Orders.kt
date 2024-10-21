package mx.cazv.todasbrillamos.model.models

/**
 * Archivo que contiene las clases de datos relacionadas con el seguimiento de pedidos.
 * @author Carlos Zamudio
 */

/**
 * Clase de datos que representa la información de un pedido.
 *
 * @property delivery_date La fecha de entrega del pedido.
 * @property status El estado del pedido.
 * @property order_received_date La fecha de recepción del pedido.
 * @property preparing_order_date La fecha de preparación del pedido.
 * @property shipped_date La fecha de envío del pedido.
 * @property products La lista de productos del pedido.
 */
data class OrderInfoWithProducts(
    val delivery_date: String,
    val status: String,
    val order_received_date: String,
    val preparing_order_date: String?,
    val shipped_date: String?,
    val products: List<OrderProduct>
)

/**
 * Clase de datos que representa un producto en un pedido.
 *
 * @property product_name El nombre del producto.
 * @property quantity La cantidad del producto.
 * @property price El precio del producto.
 * @property hash El hash de la imagen del producto.
 * @property type El tipo de la imagen del producto.
 */
data class OrderProduct(
    val product_name: String,
    val quantity: Int,
    val price: Double,
    val hash: String,
    val type: String
)

/**
 * Clase de datos que representa el resumen de un pedido.
 *
 * @property total_price El precio total del pedido.
 * @property total_products El número total de productos en el pedido.
 * @property delivery_date La fecha de entrega del pedido.
 * @property products La lista de productos en el pedido.
 */
data class OrderSummary(
    val total_price: Double,
    val total_products: Int,
    val delivery_date: String,
    val products: List<ProductSummary>
)

/**
 * Clase de datos que representa el resumen de un producto.
 *
 * @property hash El hash de la imagen del producto.
 * @property type El tipo de la imagen del producto.
 */
data class ProductSummary(
    val hash: String,
    val type: String
)

/**
 * Clase de datos que representa la información de un seguimiento de un pedido.
 *
 * @property folder El nombre de la carpeta del pedido.
 * @property orders La lista de pedidos.
 */
data class Tracking(
    val folder: String,
    val orders: List<OrderSummary>
)

/**
 * Clase de datos que representa la información de un pedido.
 *
 * @property folder El nombre de la carpeta del pedido.
 * @property order La información del pedido.
 */
data class TrackingOrder(
    val folder: String,
    val order: OrderInfoWithProducts
)