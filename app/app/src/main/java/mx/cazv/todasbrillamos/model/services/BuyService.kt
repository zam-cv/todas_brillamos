package mx.cazv.todasbrillamos.model.services

import mx.cazv.todasbrillamos.model.API
import mx.cazv.todasbrillamos.model.ApiConfig
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import mx.cazv.todasbrillamos.model.models.PaymentConfirmationRequest

/**
 * Servicio encargado de gestionar las operaciones relacionadas con la compra,
 * como la creación de intenciones de pago y la confirmación de pagos.
 * @author Carlos Zamudio
 */
class BuyService {

    // Inicializa una instancia de Retrofit configurada con la URL base de la API
    // y con un convertidor de JSON usando Gson.
    private val retrofitApi by lazy {
        Retrofit.Builder()
            .baseUrl(ApiConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // Crea una instancia del servicio de la API usando Retrofit.
    private val apiService by lazy {
        retrofitApi.create(API::class.java)
    }

    /**
     * Método que solicita la creación de una intención de pago.
     *
     * @param token El token de autenticación del usuario.
     * @return El resultado de la operación, ya sea con éxito (el clientSecret) o con error.
     */
    suspend fun createPaymentIntent(token: String): Result<String> {
        return try {
            val response = apiService.createPaymentIntent("Bearer $token")
            Result.success(response.clientSecret)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Método que confirma un pago con base en una intención de pago.
     *
     * @param token El token de autenticación del usuario.
     * @param paymentIntentId El ID de la intención de pago que se desea confirmar.
     * @return El resultado de la confirmación, ya sea éxito (mensaje) o error.
     */
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