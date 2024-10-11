package mx.cazv.todasbrillamos.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import mx.cazv.todasbrillamos.model.models.Category
import mx.cazv.todasbrillamos.model.models.Product
import mx.cazv.todasbrillamos.model.models.ProductList
import mx.cazv.todasbrillamos.model.services.ProductsService
import mx.cazv.todasbrillamos.model.states.ProductsState

/**
 * ViewModel para gestionar los productos.
 * @author Carlos Zamudio
 */
class ProductsViewModel : ViewModel() {
    private val productsService = ProductsService()

    private val _state = MutableStateFlow(ProductsState())
    val state: StateFlow<ProductsState> = _state.asStateFlow()

    /**
     * Carga los productos y categorias utilizando el token de autenticación.
     *
     * @param token El token de autenticación.
     */
    fun load(token: String, category: String) {
        viewModelScope.launch {
            var products = ProductList("", emptyList())
            var categories: List<Category> = listOf(
                Category(0, "Ver todos") // Default category
            )

            try {
                products = productsService.products(token, category)
            } catch (e: Exception) {
                // TODO: Handle error
            }

            try {
                val moreCategories = productsService.categories(token)
                categories = categories + moreCategories
            } catch (e: Exception) {
                // TODO: Handle error
            }

            _state.value = ProductsState(products, categories)
        }
    }

    /**
     * Obtiene un producto por su ID.
     *
     * @param token El token de autenticación.
     * @param productId El ID del producto.
     */
    suspend fun getProduct(token: String, productId: String): Product {
        return productsService.product(token, productId)
    }
}