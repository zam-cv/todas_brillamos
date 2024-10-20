package mx.cazv.todasbrillamos.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import mx.cazv.todasbrillamos.model.models.OrderInfoWithProducts
import mx.cazv.todasbrillamos.model.models.Tracking
import mx.cazv.todasbrillamos.model.models.TrackingOrder
import mx.cazv.todasbrillamos.model.services.TrackingService

/**
 * ViewModel para gestionar el estado del seguimiento de pedidos.
 * @author Carlos Zamudio
 */
class TrackingViewModel : ViewModel() {
    private val trackingService = TrackingService()

    private val _state = MutableStateFlow(Tracking("", emptyList()))
    val state: StateFlow<Tracking> = _state.asStateFlow()

    private val _stateOrder = MutableStateFlow(
        TrackingOrder(
            folder = "",
            order = OrderInfoWithProducts(
                "",
                "",
                "",
                "",
                "",
                emptyList()
            )
        )
    )
    val stateOrder: StateFlow<TrackingOrder> = _stateOrder.asStateFlow()

    /**
     * Carga el seguimiento de un pedido.
     *
     * @param token El token de autenticación.
     */
    fun loadTracking(token: String) {
        viewModelScope.launch {
            try {
                val trackingResponse = trackingService.getTracking(token)
                _state.value = _state.value.copy(
                    folder = trackingResponse.folder,
                    orders = trackingResponse.orders
                )
            } catch (e: Exception) {
                // TODO: Handle error
            }
        }
    }

    /**
     * Carga el seguimiento de un pedido por fecha.
     *
     * @param token El token de autenticación.
     * @param date La fecha de entrega de los pedidos a obtener.
     */
    fun loadTrackingByDate(token: String, date: String) {
        viewModelScope.launch {
            try {
                val trackingOrderResponse = trackingService.getTrackingByDate(token, date)
                _stateOrder.value = trackingOrderResponse
            } catch (e: Exception) {
                // TODO: Handle error
            }
        }
    }
}