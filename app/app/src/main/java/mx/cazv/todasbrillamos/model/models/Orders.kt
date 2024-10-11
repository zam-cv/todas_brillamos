package mx.cazv.todasbrillamos.model.models

data class OrderInfoWithProducts(
    val delivery_date: String,
    val status: String,
    val order_received_date: String,
    val preparing_order_date: String?,
    val shipped_date: String?,
    val products: List<OrderProduct>
)

data class OrderProduct(
    val product_name: String,
    val quantity: Int,
    val price: Double,
    val hash: String,
    val type: String
)

data class OrderSummary(
    val total_price: Double,
    val total_products: Int,
    val delivery_date: String,
    val products: List<ProductSummary>
)

data class ProductSummary(
    val hash: String,
    val type: String
)

data class Tracking(
    val folder: String,
    val orders: List<OrderSummary>
)

data class TrackingOrder(
    val folder: String,
    val order: OrderInfoWithProducts
)