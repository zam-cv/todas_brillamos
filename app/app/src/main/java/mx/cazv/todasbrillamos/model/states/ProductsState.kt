package mx.cazv.todasbrillamos.model.states

import mx.cazv.todasbrillamos.model.models.Category
import mx.cazv.todasbrillamos.model.models.ProductList

/**
 * Data class que representa el estado de los productos.
 * @author Carlos Zamudio
 *
 * @property products La lista de productos.
 */
data class ProductsState(
    val products: ProductList = ProductList("", emptyList()),
    val categories: List<Category> = emptyList()
)