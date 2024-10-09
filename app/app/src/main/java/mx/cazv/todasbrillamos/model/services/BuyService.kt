package mx.cazv.todasbrillamos.model.services

import mx.cazv.todasbrillamos.model.API
import mx.cazv.todasbrillamos.model.ApiConfig
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import mx.cazv.todasbrillamos.model.models.PaymentConfirmationRequest

class BuyService {
    private val retrofitApi by lazy {
        Retrofit.Builder()
            .baseUrl(ApiConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private val apiService by lazy {
        retrofitApi.create(API::class.java)
    }

    suspend fun createPaymentIntent(token: String): Result<String> {
        return try {
            val response = apiService.createPaymentIntent("Bearer $token")
            Result.success(response.clientSecret)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun confirmPayment(token: String, paymentIntentId: String): Result<String> {
        return try {
            val request = PaymentConfirmationRequest(paymentIntentId)
            val response = apiService.confirmPayment("Bearer $token", request)
            Result.success(response.message)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}