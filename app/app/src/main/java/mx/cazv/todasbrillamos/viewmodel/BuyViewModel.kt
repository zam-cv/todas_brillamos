package mx.cazv.todasbrillamos.viewmodel

import androidx.lifecycle.ViewModel
import mx.cazv.todasbrillamos.model.models.PaymentIntentResponse
import mx.cazv.todasbrillamos.model.services.BuyService

/**
 * ViewModel para gestionar las operaciones de compra. Se encarga de interactuar con el servicio
 * de compra para crear y confirmar pagos.}
 * @author Carlos Zamudio
 */
class BuyViewModel : ViewModel() {
    // Instancia del servicio de compra
    private val buyService = BuyService()

    /**
     * Crea un intent de pago utilizando el token proporcionado.
     *
     * @param token El token de autenticación del usuario.
     * @return Un resultado que contiene el ID del intent de pago o un mensaje de error.
     */
    suspend fun createPaymentIntent(token: String): Result<PaymentIntentResponse> {
        return buyService.createPaymentIntent(token)
    }

    /**
     * Confirma el pago utilizando el token y el ID del intent de pago proporcionados.
     *
     * @param token El token de autenticación del usuario.
     * @param paymentIntentId El ID del intent de pago que se debe confirmar.
     * @return Un resultado que indica el estado de la confirmación del pago o un mensaje de error.
     */
    suspend fun confirmPayment(token: String, paymentIntentId: String): Result<String> {
        return buyService.confirmPayment(token, paymentIntentId)
    }
}