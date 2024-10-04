package mx.cazv.todasbrillamos.model.models

import androidx.annotation.DrawableRes

/**
 * Datos del producto
 * @author: Min Che Kim
 */
data class Product(
    @DrawableRes val imagen: Int,
    val producto: String,
    val tipo: String,
    val precio: Double,
    val descuento: Double? = null,
    val favorito: Boolean = false
)
