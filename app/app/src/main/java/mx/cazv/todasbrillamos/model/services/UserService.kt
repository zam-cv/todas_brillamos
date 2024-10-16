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
 * Permite obtener, actualizar y gestionar la información del usuario, así como validar
 * su existencia.
 * @author Carlos Zamudio
 */
class UserService {
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
     * Obtiene los detalles del cliente autenticado.
     *
     * @param token El token de autenticación del usuario.
     * @return Los detalles del cliente (información personal y de la cuenta).
     */
    suspend fun getClient(token: String): ClientDetails {
        return apiCall { apiService.getClient("Bearer $token") }.getOrNull()!!
    }

    /**
     * Actualiza los detalles del cliente autenticado.
     *
     * @param token El token de autenticación del usuario.
     * @param clientDetails Los nuevos detalles del cliente que se desean actualizar.
     */
    suspend fun update(token: String, clientDetails: ClientDetails) {
        apiCall { apiService.updateClientDetails("Bearer $token", clientDetails) }
    }

    /**
     * Actualiza la contraseña del cliente autenticado.
     *
     * @param token El token de autenticación del usuario.
     * @param passwordUpdate El objeto que contiene la nueva contraseña y posiblemente la antigua.
     */
    suspend fun updatePassword(token: String, passwordUpdate: PasswordUpdate): Boolean {

        return apiCall { apiService.updatePassword("Bearer $token", passwordUpdate) }.isSuccess
    }

    /**
     * Verifica existen los otros datos del cleinte
     *
     * @param token El token de autenticación del usuario.
     * @return Un objeto Exist que indica si existen los otros datos del cliente.
     */
    suspend fun exist(token: String): Exist? {
        val result = apiCall { apiService.exist("Bearer $token") }
        return result.getOrNull()!!
    }

    /**
     * Establece otros detalles adicionales del cliente autenticado.
     *
     * @param token El token de autenticación del usuario.
     * @param others Un objeto que contiene los detalles adicionales que se desean configurar.
     * @return Verdadero si la operación fue exitosa, falso en caso de error.
     */
    suspend fun setOthers(token: String, others: Others): Boolean {
        return try {
            apiCall { apiService.setOthers("Bearer $token", others) }.isSuccess
        } catch (e: Exception) {
            false
        }
    }

    suspend fun getAddress(token: String): String {
        return apiCall { apiService.getAddress("Bearer $token") }.getOrNull()!!
    }
}