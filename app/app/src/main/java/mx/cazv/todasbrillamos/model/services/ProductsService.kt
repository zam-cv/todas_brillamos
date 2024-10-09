package mx.cazv.todasbrillamos.model.services

import mx.cazv.todasbrillamos.model.API
import mx.cazv.todasbrillamos.model.ApiConfig
import mx.cazv.todasbrillamos.model.apiCall
import mx.cazv.todasbrillamos.model.models.Category
import mx.cazv.todasbrillamos.model.models.Product
import mx.cazv.todasbrillamos.model.models.ProductList
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


/**
 * Servicio que maneja las solicitudes relacionadas con los productos.
 *
 * @author Carlos Zamudio
 */
class ProductsService {
    private val retrofitApi by lazy {
        Retrofit.Builder()
            .baseUrl(ApiConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private val apiService by lazy {
        retrofitApi.create(API::class.java)
    }

    /**
     * Obtiene una lista de productos aleatorios.
     *
     * @param token El token de autenticación.
     * @return La lista de productos aleatorios.
     */
    suspend fun random(token: String): ProductList {
        return apiCall { apiService.getRandomProduct("Bearer $token") }.getOrNull()!!
    }

    /**
     * Obtiene la lista de productos.
     *
     * @param token El token de autenticación.
     * @return La lista de productos.
     */
    suspend fun products(token: String): ProductList {
        return apiCall { apiService.getProducts("Bearer $token") }.getOrNull()!!
    }

    suspend fun product(token: String, id: String): Product {
        return apiCall { apiService.getProduct("Bearer $token", id) }.getOrNull()!!
    }

    suspend fun categories(token: String): List<Category> {
        return apiCall { apiService.getCategories("Bearer $token") }.getOrNull()!!
    }
}