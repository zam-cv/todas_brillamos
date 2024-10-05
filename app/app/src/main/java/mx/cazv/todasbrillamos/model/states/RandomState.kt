package mx.cazv.todasbrillamos.model.states

import mx.cazv.todasbrillamos.model.models.ProductList

data class RandomState(
    val products: ProductList = ProductList("", emptyList())
)
