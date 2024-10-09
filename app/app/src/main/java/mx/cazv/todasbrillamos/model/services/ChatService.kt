package mx.cazv.todasbrillamos.model.services

import mx.cazv.todasbrillamos.model.API
import mx.cazv.todasbrillamos.model.ApiConfig
import mx.cazv.todasbrillamos.model.apiCall
import mx.cazv.todasbrillamos.model.models.ChatMessage
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Servicio encargado de gestionar el envío de mensajes en el chat.
 * @author Carlos Zamudio
 */
class ChatService {
    // Inicializa una instancia de Retrofit con la URL base de la API y un convertidor
    // de JSON (Gson).
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
     * Método para enviar un mensaje al chat.
     *
     * @param token El token de autenticación del usuario.
     * @param content El contenido del mensaje que se desea enviar.
     * @return El mensaje de respuesta recibido tras el envío exitoso.
     */
    suspend fun sendMessage(token: String, content: String): String {
        return apiCall { apiService.sendMessage("Bearer $token", ChatMessage(content)) }.getOrNull()!!.message
    }
}