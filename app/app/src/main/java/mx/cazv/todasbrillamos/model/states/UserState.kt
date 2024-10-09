package mx.cazv.todasbrillamos.model.states

import mx.cazv.todasbrillamos.model.models.ClientDetails

/**
 * Clase de datos que representa el estado del usuario en la aplicación.
 *
 * @property details Un objeto de tipo ClientDetails que contiene la información del cliente.
 *                   Se inicializa con valores predeterminados como cadenas de texto.
 */
data class UserState(
    val details: ClientDetails = ClientDetails("...", "...", "..."),
)