package mx.cazv.todasbrillamos.model.models

/**
 * Clase de datos que representa la información de un usuario.
 *
 *
 * @author Carlos Zamudio
 *
 * @property email El correo electrónico del usuario.
 * @property password La contraseña del usuario.
 * @property first_name El nombre del usuario.
 * @property last_name El apellido del usuario.
 */
data class UserInfo(
    val email: String,
    val password: String,
    val first_name: String,
    val last_name: String
)
