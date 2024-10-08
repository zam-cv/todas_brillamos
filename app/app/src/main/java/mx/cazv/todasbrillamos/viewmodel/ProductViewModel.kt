package mx.cazv.todasbrillamos.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import mx.cazv.todasbrillamos.model.services.ProductsService
import mx.cazv.todasbrillamos.model.states.ProductState

class ProductViewModel : ViewModel() {
    private val productService = ProductsService()

    private val _state = MutableStateFlow(ProductState())
    val state: StateFlow<ProductState> = _state.asStateFlow()

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
