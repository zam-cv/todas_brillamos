package mx.cazv.todasbrillamos.viewmodel

import androidx.lifecycle.ViewModel
import mx.cazv.todasbrillamos.model.services.BuyService

class BuyViewModel : ViewModel() {
    private val buyService = BuyService()

    suspend fun createPaymentIntent(token: String): Result<String> {
        return buyService.createPaymentIntent(token)
    }

    suspend fun confirmPayment(token: String, paymentIntentId: String): Result<String> {
        return buyService.confirmPayment(token, paymentIntentId)
    }
}