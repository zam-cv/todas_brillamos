package mx.cazv.todasbrillamos.model.models

/**
 * Data class para la actualización de una contraseña ya existente.
 * @author Carlos Zamudio
 *
 * @property old_password La contraseña actual.
 * @property new_password La nueva contraseña.
 */
data class PasswordUpdate(
    val old_password: String,
    val new_password: String
)