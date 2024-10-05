package mx.cazv.todasbrillamos.model.states

import mx.cazv.todasbrillamos.model.models.ClientDetails

data class UserState(
    val details: ClientDetails = ClientDetails("...", "...", "..."),
)