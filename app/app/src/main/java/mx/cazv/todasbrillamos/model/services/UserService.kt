package mx.cazv.todasbrillamos.model.services

import mx.cazv.todasbrillamos.model.API
import mx.cazv.todasbrillamos.model.ApiConfig
import mx.cazv.todasbrillamos.model.apiCall
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Servicio que maneja las solicitudes relacionadas con el usuario.
 *
 * @author Carlos Zamudio
 */
class UserService {
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
     * Obtiene el nombre completo del usuario.
     *
     * @param token El token de autenticación.
     * @return El nombre completo del usuario.
     */
    suspend fun fullname(token: String): String {
        return apiCall { apiService.getFullName("Bearer $token") }.getOrNull() ?: ""
    }
}