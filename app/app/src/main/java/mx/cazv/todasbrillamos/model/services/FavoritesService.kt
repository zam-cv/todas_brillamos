package mx.cazv.todasbrillamos.model.services

import mx.cazv.todasbrillamos.model.API
import mx.cazv.todasbrillamos.model.ApiConfig
import mx.cazv.todasbrillamos.model.apiCall
import mx.cazv.todasbrillamos.model.models.Exist
import mx.cazv.todasbrillamos.model.models.Favorites
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

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

    suspend fun exists(token: String, id: Int): Exist {
        return apiCall { apiService.existFavorite("Bearer $token", id) }.getOrNull()!!
    }

    suspend fun addFavorite(token: String, id: Int) {
        apiCall { apiService.addFavorite("Bearer $token", id) }.getOrNull()!!
    }

    suspend fun removeFavorite(token: String, id: Int) {
        apiCall { apiService.deleteFavorite("Bearer $token", id) }.getOrNull()!!
    }

    suspend fun favorites(token: String): Favorites {
        return apiCall { apiService.getFavorites("Bearer $token") }.getOrNull()!!
    }
}