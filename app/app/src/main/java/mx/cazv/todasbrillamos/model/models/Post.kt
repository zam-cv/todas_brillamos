package mx.cazv.todasbrillamos.model.models

data class Post(
    val id: Int,
    val title: String,
    val author: String,
    val date: String,
    val content: String
)