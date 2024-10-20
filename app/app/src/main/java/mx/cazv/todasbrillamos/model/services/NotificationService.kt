package mx.cazv.todasbrillamos.model.services

import mx.cazv.todasbrillamos.model.API
import mx.cazv.todasbrillamos.model.ApiConfig
import mx.cazv.todasbrillamos.model.apiCall
import mx.cazv.todasbrillamos.model.models.GroupedNotification
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Servicio para interactuar con la API de notificaciones.
 * @author Mariana Balderrábano, Carlos Zamudio
 */

class NotificationService {
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
     * Obtiene la lista de notificaciones.
     *
     * @param token El token de autenticación.
     * @return La lista de notificaciones agrupadas.
     */
    suspend fun getNotifications(token: String): List<GroupedNotification> {
        return apiCall { apiService.getNotifications("Bearer $token") }.getOrNull()!!
    }

    /**
     * Obtiene el número de notificaciones no leídas.
     *
     * @param token El token de autenticación.
     * @return El número de notificaciones no leídas.
     */
    suspend fun getUnread(token: String): Int {
        return apiCall { apiService.getUnreadNotifications("Bearer $token") }.getOrNull()!!.unread
    }
}