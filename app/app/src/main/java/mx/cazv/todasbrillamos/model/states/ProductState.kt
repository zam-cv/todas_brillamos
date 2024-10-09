package mx.cazv.todasbrillamos.model.states

import mx.cazv.todasbrillamos.model.models.Product
import mx.cazv.todasbrillamos.model.models.ProductRaw

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