package mx.cazv.todasbrillamos.model.models

/**
 * Data class que representa los detalles del cliente.
 * @author Carlos Zamudio
 *
 * @property first_name El nombre del cliente.
 * @property last_name El apellido del cliente.
 * @property email El correo electrónico del cliente.
 */
data class ClientDetails(
    val first_name: String,
    val last_name: String,
    val email: String
)