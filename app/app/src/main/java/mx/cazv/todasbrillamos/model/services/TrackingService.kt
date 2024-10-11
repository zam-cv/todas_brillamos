package mx.cazv.todasbrillamos.model.services

import mx.cazv.todasbrillamos.model.API
import mx.cazv.todasbrillamos.model.ApiConfig
import mx.cazv.todasbrillamos.model.apiCall
import mx.cazv.todasbrillamos.model.models.Tracking
import mx.cazv.todasbrillamos.model.models.TrackingOrder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class TrackingService {
    // Inicializa una instancia de Retrofit con la URL base de la API y un convertidor de JSON (Gson).
    private val retrofitApi by lazy {
        Retrofit.Builder()
            .baseUrl(ApiConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // Crea una instancia del servicio de la API utilizando Retrofit.
    private val apiService by lazy {
        retrofitApi.create(API::class.java)
    }

    suspend fun getTracking(token: String): Tracking {
        return apiCall { apiService.getTracking("Bearer $token") }.getOrNull()!!
    }

    suspend fun getTrackingByDate(token: String, date: String): TrackingOrder {
        return apiCall { apiService.getTrackingByDate("Bearer $token", date) }.getOrNull()!!
    }
}