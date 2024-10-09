package mx.cazv.todasbrillamos.model.models

/**
 * Clase de datos que representa una publicación.
 * @author Carlos Zamudio
 *
 * @property id El ID de la publicación.
 * @property title El título de la publicación.
 * @property author El autor de la publicación.
 * @property date La fecha de la publicación.
 * @property content El contenido de la publicación.
 */
data class Post(
    val id: Int,
    val title: String,
    val author: String,
    val date: String,
    val content: String
)