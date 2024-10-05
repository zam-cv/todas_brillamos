package mx.cazv.todasbrillamos.model.services

import mx.cazv.todasbrillamos.model.API
import mx.cazv.todasbrillamos.model.ApiConfig
import mx.cazv.todasbrillamos.model.apiCall
import mx.cazv.todasbrillamos.model.models.ProductList
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

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

    suspend fun random(token: String): ProductList {
        return apiCall { apiService.getRandomProduct("Bearer $token") }.getOrNull()!!
    }

    suspend fun products(token: String): ProductList {
        return apiCall { apiService.getProducts("Bearer $token") }.getOrNull()!!
    }
}