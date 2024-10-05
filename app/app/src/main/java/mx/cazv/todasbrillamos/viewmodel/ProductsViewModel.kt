package mx.cazv.todasbrillamos.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import mx.cazv.todasbrillamos.model.services.ProductsService
import mx.cazv.todasbrillamos.model.states.ProductsState

/**
 * ViewModel para gestionar los productos.
 * @author Carlos Zamudio
 */
class ProductsViewModel : ViewModel() {
    private val randomService = ProductsService()

    private val _state = MutableStateFlow(ProductsState())
    val state: StateFlow<ProductsState> = _state.asStateFlow()

    /**
     * Carga los productos utilizando el token de autenticación.
     *
     * @param token El token de autenticación.
     */
    fun loadProducts(token: String) {
        viewModelScope.launch {
            try {
                val products = randomService.random(token)
                _state.value = ProductsState(products)
            } catch (e: Exception) {
                _state.value = ProductsState()
            }
        }
    }
}