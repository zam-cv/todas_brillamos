package mx.cazv.todasbrillamos.model.states

import mx.cazv.todasbrillamos.model.models.ProductList

/**
 * Clase de datos que representa el estado de los productos aleatorios.
 *
 * @author Carlos Zamudio
 *
 * @property products La lista de productos aleatorios.
 */
data class RandomState(
    val products: ProductList = ProductList("", emptyList())
)
