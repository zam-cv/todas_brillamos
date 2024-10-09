package mx.cazv.todasbrillamos.model.services

import mx.cazv.todasbrillamos.model.API
import mx.cazv.todasbrillamos.model.ApiConfig
import mx.cazv.todasbrillamos.model.apiCall
import mx.cazv.todasbrillamos.model.models.Post
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Servicio que maneja las solicitudes relacionadas con las publicaciones.
 * @author Carlos Zamudio
 */
class PostsService {
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
     * Obtiene la lista de publicaciones.
     *
     * @param token El token de autenticación.
     * @return La lista de publicaciones.
     */
    suspend fun posts(token: String): List<Post> {
        return apiCall { apiService.getPosts("Bearer $token") }.getOrNull()!!
    }
}