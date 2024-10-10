package mx.cazv.todasbrillamos.model.models

/**
 * Data class que representa la respuesta cuando se verifica si un producto existe.
 * @author Carlos Zamudio
 *
 * @property exists Indica si el producto existe o no.
 */
data class Exist(
    val exists: Boolean
)