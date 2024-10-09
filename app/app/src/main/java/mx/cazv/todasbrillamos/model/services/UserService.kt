package mx.cazv.todasbrillamos.model.services

import mx.cazv.todasbrillamos.model.API
import mx.cazv.todasbrillamos.model.ApiConfig
import mx.cazv.todasbrillamos.model.apiCall
import mx.cazv.todasbrillamos.model.models.ClientDetails
import mx.cazv.todasbrillamos.model.models.Exist
import mx.cazv.todasbrillamos.model.models.Others
import mx.cazv.todasbrillamos.model.models.PasswordUpdate
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

    suspend fun getClient(token: String): ClientDetails {
        return apiCall { apiService.getClient("Bearer $token") }.getOrNull()!!
    }

    suspend fun update(token: String, clientDetails: ClientDetails) {
        apiCall { apiService.updateClientDetails("Bearer $token", clientDetails) }
    }

    suspend fun updatePassword(token: String, passwordUpdate: PasswordUpdate) {
        apiCall { apiService.updatePassword("Bearer $token", passwordUpdate) }
    }

    suspend fun exist(token: String): Exist? {
        val result = apiCall { apiService.exist("Bearer $token") }
        return result.getOrNull()!!
    }

    suspend fun setOthers(token: String, others: Others): Boolean {
        return try {
            apiCall { apiService.setOthers("Bearer $token", others) }.isSuccess
        } catch (e: Exception) {
            false
        }
    }
}