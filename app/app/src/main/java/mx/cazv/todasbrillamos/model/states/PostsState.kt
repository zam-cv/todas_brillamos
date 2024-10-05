package mx.cazv.todasbrillamos.model.states

import mx.cazv.todasbrillamos.model.models.Post

data class PostsState(
    val posts: List<Post> = emptyList()
)
