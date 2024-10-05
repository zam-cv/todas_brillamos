package mx.cazv.todasbrillamos.model.states

<<<<<<< HEAD
import mx.cazv.todasbrillamos.model.models.ClientDetails

=======
/**
 * Clase de datos que representa el estado del usuario.
 *
 * @author Carlos Zamudio
 *
 * @property fullName El nombre completo del usuario.
 */
>>>>>>> 523de40f89d420c58d931710bd4b75a670f2caf8
data class UserState(
    val details: ClientDetails = ClientDetails("...", "...", "..."),
)