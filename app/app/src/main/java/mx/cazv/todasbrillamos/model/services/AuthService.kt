package mx.cazv.todasbrillamos.model.services

import mx.cazv.todasbrillamos.model.API
import mx.cazv.todasbrillamos.model.ApiConfig
import mx.cazv.todasbrillamos.model.apiCall
import mx.cazv.todasbrillamos.model.models.Credentials
import mx.cazv.todasbrillamos.model.models.SignInRequest
import mx.cazv.todasbrillamos.model.models.UserInfo
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Servicio de autenticación que maneja las solicitudes de inicio de sesión, registro y verificación.
 * @author Carlos Zamudio
 */
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

    /**
     * Inicia sesión con las credenciales proporcionadas.
     *
     * @param request La solicitud de inicio de sesión.
     * @return Las credenciales del usuario si el inicio de sesión es exitoso, de lo contrario, null.
     */
    suspend fun signin(request: SignInRequest): Credentials? {
        return apiCall { apiService.signin(request) }.getOrNull()
    }

    /**
     * Registra un nuevo usuario con la información proporcionada.
     *
     * @param request La información del usuario.
     * @return true si el registro es exitoso, de lo contrario, false.
     */
    suspend fun register(request: UserInfo): Boolean {
        return apiCall { apiService.register(request) }.isSuccess
    }

    /**
     * Verifica el token de autenticación.
     *
     * @param token El token de autenticación.
     * @return true si la verificación es exitosa, de lo contrario, false.
     */
    suspend fun verify(token: String): Boolean {
        return apiCall { apiService.verify("Bearer $token") }.isSuccess
    }
}