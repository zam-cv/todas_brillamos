package mx.cazv.todasbrillamos.model.services

import mx.cazv.todasbrillamos.model.API
import mx.cazv.todasbrillamos.model.ApiConfig
import mx.cazv.todasbrillamos.model.apiCall
import mx.cazv.todasbrillamos.model.models.CartResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Servicio encargado de gestionar las operaciones del carrito de compras,
 * como agregar productos, obtener el contenido del carrito, actualizar cantidades y eliminar
 * productos.
 * @author Carlos Zamudio
 */
class CartService {

    // Inicializa una instancia de Retrofit con la URL base de la API y un convertidor de JSON (Gson).
    private val retrofitApi by lazy {
        Retrofit.Builder()
            .baseUrl(ApiConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // Crea una instancia del servicio de la API utilizando Retrofit.
    private val apiService by lazy {
        retrofitApi.create(API::class.java)
    }

    /**
     * Método para agregar un producto al carrito de compras.
     *
     * @param token El token de autenticación del usuario.
     * @param id El ID del producto que se va a agregar.
     * @param quantity La cantidad del producto que se va a agregar.
     */
    suspend fun addProductToCart(token: String, id: Int, quantity: Int) {
        apiCall { apiService.addProductToCart("Bearer $token", id, quantity) }
    }

    /**
     * Método para obtener el contenido actual del carrito de compras.
     *
     * @param token El token de autenticación del usuario.
     * @return El contenido del carrito en forma de CartResponse.
     */
    suspend fun getCart(token: String): CartResponse {
        return apiCall { apiService.getCart("Bearer $token") }.getOrNull()!!
    }

    /**
     * Método para actualizar la cantidad de un producto en el carrito.
     *
     * @param token El token de autenticación del usuario.
     * @param id El ID del producto cuya cantidad se va a actualizar.
     * @param quantity La nueva cantidad del producto.
     */
    suspend fun updateProductQuantityInCart(token: String, id: Int, quantity: Int) {
        apiCall { apiService.updateProductQuantityInCart("Bearer $token", id, quantity) }
    }

    /**
     * Método para eliminar un producto del carrito de compras.
     *
     * @param token El token de autenticación del usuario.
     * @param id El ID del producto que se va a eliminar del carrito.
     */
    suspend fun deleteProductFromCart(token: String, id: Int) {
        apiCall { apiService.deleteProductFromCart("Bearer $token", id) }
    }
}