package mx.cazv.todasbrillamos.model.models

data class PaymentIntentResponse(
    val clientSecret: String
)

data class PaymentConfirmationRequest(
    val paymentIntentId: String
)

data class PaymentConfirmationResponse(
    val message: String
)