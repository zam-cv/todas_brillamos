package mx.cazv.todasbrillamos.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import mx.cazv.todasbrillamos.model.models.CartItem
import mx.cazv.todasbrillamos.model.models.CartProduct
import mx.cazv.todasbrillamos.model.models.ProductRaw
import mx.cazv.todasbrillamos.model.services.CartService
import mx.cazv.todasbrillamos.model.states.CartState

/**
 * ViewModel para gestionar el carrito de compras. Maneja la carga, adición, actualización y
 * eliminación de productos en el carrito.
 * @author Carlos Zamudio
 */
class CartViewModel : ViewModel() {
    private val cartService = CartService()

    private val _state = MutableStateFlow(CartState())
    val state: StateFlow<CartState> = _state.asStateFlow()

    /**
    * Carga el carrito del usuario usando el token de autenticación.
    *
    * @param token El token de autenticación del usuario.
    */
    fun loadCart(token: String) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            try {
                val cartResponse = cartService.getCart(token)
                _state.value = _state.value.copy(
                    folder = cartResponse.folder,
                    cart = cartResponse.cart,
                    totalPrice = calculateTotalPrice(cartResponse.cart),
                    isLoading = false
                )
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    isLoading = false,
                    error = e.message ?: "Error al cargar el carrito"
                )
            }
        }
    }

    /**
     * Añade un producto al carrito.
     *
     * @param token El token de autenticación del usuario.
     * @param product El producto a añadir al carrito.
     * @param quantity La cantidad del producto a añadir.
     */
    fun addProductToCart(token: String, product: ProductRaw, quantity: Int) {
        viewModelScope.launch {
            try {
                cartService.addProductToCart(token, product.id, quantity)
                val updatedCart = _state.value.cart.toMutableList()
                val existingItem = updatedCart.find { it.product.product_id == product.id }
                if (existingItem != null) {
                    val index = updatedCart.indexOf(existingItem)
                    updatedCart[index] = existingItem.copy(quantity = existingItem.quantity + quantity)
                } else {
                    val cartProduct = CartProduct(
                        name = product.name,
                        price = product.price.toDouble(),
                        product_id = product.id,
                        hash = product.hash,
                        type = product.type,
                        stock = product.stock
                    )
                    updatedCart.add(CartItem(quantity = quantity, product = cartProduct))
                }
                _state.value = _state.value.copy(cart = updatedCart)
            } catch (e: Exception) {
                _state.value = _state.value.copy(error = e.message ?: "Error al añadir el producto")
            }
        }
    }

    /**
     * Actualiza la cantidad de un producto en el carrito.
     *
     * @param token El token de autenticación del usuario.
     * @param id El ID del producto que se desea actualizar.
     * @param quantity La nueva cantidad del producto.
     */
    fun updateProductQuantityInCart(token: String, id: Int, quantity: Int) {
        if (quantity < 1) return
        viewModelScope.launch {
            try {
                cartService.updateProductQuantityInCart(token, id, quantity)
                val updatedCart = _state.value.cart.map { item ->
                    if (item.product.product_id == id) {
                        item.copy(quantity = quantity)
                    } else {
                        item
                    }
                }
                _state.value = _state.value.copy(cart = updatedCart, totalPrice = calculateTotalPrice(updatedCart))
            } catch (e: Exception) {
                _state.value = _state.value.copy(error = e.message ?: "Error al actualizar la cantidad")
            }
        }
    }

    /**
     * Elimina un producto del carrito.
     *
     * @param token El token de autenticación del usuario.
     * @param id El ID del producto que se desea eliminar.
     */
    fun deleteProductFromCart(token: String, id: Int) {
        viewModelScope.launch {
            try {
                cartService.deleteProductFromCart(token, id)
                val updatedCart = _state.value.cart.filter { it.product.product_id != id }
                _state.value = _state.value.copy(cart = updatedCart, totalPrice = calculateTotalPrice(updatedCart))
            } catch (e: Exception) {
                _state.value = _state.value.copy(error = e.message ?: "Error al eliminar el producto")
            }
        }
    }

    /**
     * Finaliza la compra, vaciando el carrito y estableciendo el precio total a cero.
     */
    fun buy() {
        _state.value = _state.value.copy(cart = emptyList(), totalPrice = 0.0)
    }

    /**
     * Calcula el precio total del carrito.
     *
     * @param cart La lista de artículos en el carrito.
     * @return El precio total del carrito.
     */
    private fun calculateTotalPrice(cart: List<CartItem>): Double {
        return cart.sumOf { it.quantity * it.product.price }
    }
}