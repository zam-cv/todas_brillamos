package mx.cazv.todasbrillamos.model.services

import mx.cazv.todasbrillamos.model.API
import mx.cazv.todasbrillamos.model.ApiConfig
import mx.cazv.todasbrillamos.model.apiCall
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CartService {
    private val retrofitApi by lazy {
        Retrofit.Builder()
            .baseUrl(ApiConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private val apiService by lazy {
        retrofitApi.create(API::class.java)
    }

    suspend fun addProductToCart(token: String, id: Int, quantity: Int) {
        apiCall { apiService.addProductToCart("Bearer $token", id, quantity) }
    }
}