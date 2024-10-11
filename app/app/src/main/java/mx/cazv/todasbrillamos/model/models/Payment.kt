package mx.cazv.todasbrillamos.model.models

/**
 * Data class que representa la respuesta de una solicitud de pago.
 * @author Carlos Zamudio
 *
 * @property clientSecret El secreto de la intención de pago, necesario para completar
 * la transacción.
 */
data class PaymentIntentResponse(
    val clientSecret: String,
    val paymentIntentId: String
)

/**
 * Data class que representa una solicitud de confirmación de pago.
 * Se utiliza para enviar el ID de la intención de pago que se desea confirmar.
 *
 * @property paymentIntentId El ID de la intención de pago que se va a confirmar.
 */
data class PaymentConfirmationRequest(
    val paymentIntentId: String
)

/**
 * Data class que representa la respuesta de una confirmación de pago.
 * Contiene un mensaje que indica el resultado de la confirmación, ya sea éxito o error.
 *
 * @property message El mensaje que indica el resultado de la confirmación del pago.
 */
data class PaymentConfirmationResponse(
    val message: String
)