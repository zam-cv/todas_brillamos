package mx.cazv.todasbrillamos.model.services

import mx.cazv.todasbrillamos.model.API
import mx.cazv.todasbrillamos.model.ApiConfig
import mx.cazv.todasbrillamos.model.apiCall
import mx.cazv.todasbrillamos.model.models.Tracking
import mx.cazv.todasbrillamos.model.models.TrackingOrder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Servicio para interactuar con la API de seguimiento.
 * @author Carlos Zamudio
 */


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

    /**
     * Obtiene el seguimiento de un pedido.
     *
     * @param token El token de autenticación.
     * @return El seguimiento del pedido.
     */
    suspend fun getTracking(token: String): Tracking {
        return apiCall { apiService.getTracking("Bearer $token") }.getOrNull()!!
    }

    /**
     * Obtiene el seguimiento de un pedido por fecha.
     *
     * @param token El token de autenticación.
     * @param date La fecha de entrega de los pedidos a obtener.
     * @return Información del pedido.
     */
    suspend fun getTrackingByDate(token: String, date: String): TrackingOrder {
        return apiCall { apiService.getTrackingByDate("Bearer $token", date) }.getOrNull()!!
    }
}