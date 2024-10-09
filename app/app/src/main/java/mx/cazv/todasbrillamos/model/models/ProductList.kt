package mx.cazv.todasbrillamos.model.models

/**
 * Clase de datos que representa una lista de productos.
 * @author Carlos Zamudio
 *
 * @property folder La carpeta donde se almacenan los productos.
 * @property products La lista de productos.
 */
data class ProductList(
    val folder: String,
    val products: List<ProductRaw>
)
