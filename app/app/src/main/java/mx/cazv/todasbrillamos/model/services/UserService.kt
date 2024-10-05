package mx.cazv.todasbrillamos.model.services

import mx.cazv.todasbrillamos.model.API
import mx.cazv.todasbrillamos.model.ApiConfig
import mx.cazv.todasbrillamos.model.apiCall
import mx.cazv.todasbrillamos.model.models.ClientDetails
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

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
}