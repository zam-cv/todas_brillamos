package mx.cazv.todasbrillamos.viewmodel

import androidx.lifecycle.ViewModel
import mx.cazv.todasbrillamos.model.services.CartService

class CartViewModel : ViewModel() {
    private val cartService = CartService()

    suspend fun addProductToCart(token: String, id: Int, quantity: Int) {
        cartService.addProductToCart(token, id, quantity)
    }
}