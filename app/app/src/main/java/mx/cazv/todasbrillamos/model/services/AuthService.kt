package mx.cazv.todasbrillamos.model.services

import mx.cazv.todasbrillamos.model.API
import mx.cazv.todasbrillamos.model.ApiConfig
import mx.cazv.todasbrillamos.model.apiCall
import mx.cazv.todasbrillamos.model.models.Credentials
import mx.cazv.todasbrillamos.model.models.SignInRequest
import mx.cazv.todasbrillamos.model.models.UserInfo
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AuthService {
    private val retrofitApi by lazy {
        Retrofit.Builder()
            .baseUrl(ApiConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private val apiService by lazy {
        retrofitApi.create(API::class.java)
    }

    suspend fun signin(request: SignInRequest): Credentials? {
        return apiCall { apiService.signin(request) }.getOrNull()
    }

    suspend fun register(request: UserInfo): Boolean {
        return apiCall { apiService.register(request) }.isSuccess
    }

    suspend fun verify(token: String): Boolean {
        return apiCall { apiService.verify("Bearer $token") }.isSuccess
    }
}