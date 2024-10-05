package mx.cazv.todasbrillamos.model.models

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

data class ProductList(
    val folder: String,
    val products: List<ProductRaw>
)