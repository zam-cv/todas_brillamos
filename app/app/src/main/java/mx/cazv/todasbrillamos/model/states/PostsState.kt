package mx.cazv.todasbrillamos.model.states

import mx.cazv.todasbrillamos.model.models.Post

/**
 * Clase de datos que representa el estado de las publicaciones de los articulos.
 * @author Carlos Zamudio
 *
 * @property posts La lista de publicaciones.
 */
data class PostsState(
    val posts: List<Post> = emptyList()
)
