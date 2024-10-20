package mx.cazv.todasbrillamos.model.services

import mx.cazv.todasbrillamos.model.API
import mx.cazv.todasbrillamos.model.ApiConfig
import mx.cazv.todasbrillamos.model.apiCall
import mx.cazv.todasbrillamos.model.models.Exist
import mx.cazv.todasbrillamos.model.models.Favorites
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Servicio para interactuar con la API de favoritos.
 * @author Carlos Zamudio
 */

class FavoritesService {
    // Inicializa una instancia de Retrofit con la URL base de la API y un convertidor
    private val retrofitApi by lazy {
        Retrofit.Builder()
            .baseUrl(ApiConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // Crea una instancia del servicio de la API utilizando Retrofit
    private val apiService by lazy {
        retrofitApi.create(API::class.java)
    }

    /**
     * Verifica si un producto existe en la lista de favoritos.
     *
     * @param token El token de autenticación.
     * @param id El ID del producto.
     * @return La existencia del producto en la lista de favoritos.
     */
    suspend fun exists(token: String, id: Int): Exist {
        return apiCall { apiService.existFavorite("Bearer $token", id) }.getOrNull()!!
    }

    /**
     * Añade un producto a la lista de favoritos.
     *
     * @param token El token de autenticación.
     * @param id El ID del producto.
     */
    suspend fun addFavorite(token: String, id: Int) {
        apiCall { apiService.addFavorite("Bearer $token", id) }.getOrNull()!!
    }

    /**
     * Elimina un producto de la lista de favoritos.
     *
     * @param token El token de autenticación.
     * @param id El ID del producto.
     */
    suspend fun removeFavorite(token: String, id: Int) {
        apiCall { apiService.deleteFavorite("Bearer $token", id) }.getOrNull()!!
    }

    /**
     * Obtiene la lista de productos favoritos.
     *
     * @param token El token de autenticación.
     * @return La lista de productos favoritos.
     */
    suspend fun favorites(token: String): Favorites {
        return apiCall { apiService.getFavorites("Bearer $token") }.getOrNull()!!
    }
}