package mx.cazv.todasbrillamos.model.states

import mx.cazv.todasbrillamos.model.models.Product
import mx.cazv.todasbrillamos.model.models.ProductRaw

/**
 * Clase de datos que representa el estado de un producto en la aplicación.
 * @author Carlos Zamudio
 *
 * @property product Un objeto de tipo Product que contiene los detalles de un producto. Se
 * inicializa con un objeto predeterminado de tipo ProductRaw.
 */
data class ProductState(
    val product: Product = Product("", product = ProductRaw(
        0,
        "",
        "",
        "",
        "",
        "",
        0,
        0,
        "",
        "",
        "",
        "",
        "",
        "",
        0
    ))
)