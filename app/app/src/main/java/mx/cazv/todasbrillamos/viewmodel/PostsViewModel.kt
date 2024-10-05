package mx.cazv.todasbrillamos.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import mx.cazv.todasbrillamos.model.services.PostsService
import mx.cazv.todasbrillamos.model.states.PostsState

class PostsViewModel : ViewModel() {
    private val postsService = PostsService()

    private val _state = MutableStateFlow(PostsState())
    val state: StateFlow<PostsState> = _state.asStateFlow()

    fun loadPosts(token: String) {
        viewModelScope.launch {
            try {
                val posts = postsService.posts(token)
                println(posts)
                _state.value = PostsState(posts)
            } catch (e: Exception) {
                _state.value = PostsState()
            }
        }
    }
}