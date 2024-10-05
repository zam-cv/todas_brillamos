package mx.cazv.todasbrillamos.model.models

/**
 * Clase de datos que representa una solicitud de inicio de sesión.
 *
 * @author Carlos Zamudio
 *
 * @property email El correo electrónico del usuario.
 * @property password La contraseña del usuario.
 */
data class SignInRequest(
    val email: String,
    val password: String
)
