package mx.cazv.todasbrillamos.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import mx.cazv.todasbrillamos.model.services.ProductsService
import mx.cazv.todasbrillamos.model.states.ProductState

/**
 * ViewModel para gestionar la lógica de productos. Se encarga de cargar los detalles de un
 * producto específico.
 * @author Carlos Zamudio
 */
class ProductViewModel : ViewModel() {
    private val productService = ProductsService()

    private val _state = MutableStateFlow(ProductState())
    val state: StateFlow<ProductState> = _state.asStateFlow()

    /**
     * Carga un producto específico usando su ID.
     *
     * @param token El token de autenticación.
     * @param productId El ID del producto.
     */
    fun loadProduct(token: String, productId: String) {
        viewModelScope.launch {
            try {
                val product = productService.product(token, productId)
                _state.value = ProductState(product)
            } catch (e: Exception) {
                _state.value = ProductState()
            }
        }
    }
}
