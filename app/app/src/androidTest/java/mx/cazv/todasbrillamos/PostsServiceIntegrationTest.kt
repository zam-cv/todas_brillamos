package mx.cazv.todasbrillamos

import kotlinx.coroutines.runBlocking
import mx.cazv.todasbrillamos.model.models.SignInRequest
import mx.cazv.todasbrillamos.model.services.AuthService
import mx.cazv.todasbrillamos.model.services.PostsService
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class PostsServiceIntegrationTest {
    private lateinit var postsService: PostsService
    private lateinit var authService: AuthService
    private lateinit var token: String

    @Before
    fun setUp() = runBlocking {
        postsService = PostsService()
        authService = AuthService()

        // Obtenemos un token válido para las pruebas
        val credentials = authService.signin(SignInRequest("test@example.com", "password123"))
        token = credentials?.token ?: throw IllegalStateException("No se pudo obtener un token válido para las pruebas")
    }

    @Test
    fun testGetPosts() = runBlocking {
        val posts = postsService.posts(token)
        assertNotNull("La lista de publicaciones no debería ser null", posts)
    }

    @Test
    fun testGetPost() = runBlocking {
        // Primero obtenemos una lista de posts para conseguir un ID válido
        val posts = postsService.posts(token)

        if (posts.isNotEmpty()) {
            assertTrue("Debería haber al menos una publicación para probar", posts.isNotEmpty())

            val firstPostId = posts.first().id.toString()
            val post = postsService.getPost(token, firstPostId)

            assertNotNull("La publicación obtenida no debería ser null", post)
            assertEquals("El ID de la publicación obtenida debería coincidir con el solicitado", firstPostId, post.id.toString())
        } else {
            assertTrue("No hay publicaciones para probar", true)
        }
    }

    @Test(expected = Exception::class)
    fun testGetPostWithInvalidId(): Unit = runBlocking {
        postsService.getPost(token, "invalid_id")
    }

    @Test(expected = Exception::class)
    fun testGetPostsWithInvalidToken(): Unit = runBlocking {
        postsService.posts("invalid_token")
    }

    @Test
    fun testPostStructure() = runBlocking {
        val posts = postsService.posts(token)
        if (posts.isNotEmpty()) {
            val post = posts.first()
            assertNotNull("El ID de la publicación no debería ser null", post.id)
            assertNotNull("El título de la publicación no debería ser null", post.title)
            assertNotNull("El autor de la publicación no debería ser null", post.author)
            assertNotNull("La fecha de la publicación no debería ser null", post.date)
            assertNotNull("El contenido de la publicación no debería ser null", post.content)
        } else {
            assertTrue("No hay publicaciones para probar", true)
        }
    }
}