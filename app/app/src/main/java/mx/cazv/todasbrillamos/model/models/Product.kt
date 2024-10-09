package mx.cazv.todasbrillamos.model.models

/**
 * Clase de datos que representa un producto sin procesar.
 * @author Carlos Zamudio
 *
 * @property id El ID del producto.
 * @property hash El hash de la imagen del producto.
 * @property type El tipo de imagen del producto.
 * @property model El modelo del producto.
 * @property name El nombre del producto.
 * @property description La descripción del producto.
 * @property price El precio del producto.
 * @property stock La existencia del producto.
 * @property size El tamaño del producto.
 * @property color El color del producto.
 * @property maintenance El mantenimiento del producto.
 * @property material El material del producto.
 * @property absorbency La absorbencia del producto.
 * @property material_feature Las características del material del producto.
 * @property category_id El ID de la categoría del producto.
 */
data class ProductRaw(
    val id: Int,
    val hash: String,
    val type: String,
    val model: String,
    val name: String,
    val description: String,
    val price: Int,
    val stock: Int,
    val size: String,
    val color: String,
    val maintenance: String,
    val material: String,
    val absorbency: String,
    val material_feature: String,
    val category_id: Int
)

/**
 * Datos del producto
 * @author: Min Che Kim, Carlos Zamudio
 *
 * @property folder El nombre de la carpeta del producto.
 * @property product El producto.
 */
data class Product(
    val folder: String,
    val product: ProductRaw
)
