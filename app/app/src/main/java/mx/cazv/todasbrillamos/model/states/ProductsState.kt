package mx.cazv.todasbrillamos.model.states

import mx.cazv.todasbrillamos.model.models.ProductList

data class ProductsState(
    val products: ProductList = ProductList("", emptyList())
)