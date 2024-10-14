package mx.cazv.todasbrillamos

import kotlinx.coroutines.runBlocking
import mx.cazv.todasbrillamos.model.models.SignInRequest
import mx.cazv.todasbrillamos.model.services.AuthService
import mx.cazv.todasbrillamos.model.services.FavoritesService
import mx.cazv.todasbrillamos.model.services.ProductsService
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class FavoritesServiceIntegrationTest {
    private lateinit var favoritesService: FavoritesService
    private lateinit var authService: AuthService
    private lateinit var productsService: ProductsService
    private lateinit var token: String

    @Before
    fun setUp() = runBlocking {
        favoritesService = FavoritesService()
        authService = AuthService()
        productsService = ProductsService()

        // Obtenemos un token válido para las pruebas
        val credentials = authService.signin(SignInRequest("test@example.com", "password123"))
        token = credentials?.token ?: throw IllegalStateException("No se pudo obtener un token válido para las pruebas")
    }

    @Test
    fun testAddAndRemoveFavorite() = runBlocking {
        val randomProducts = productsService.random(token)
        if (randomProducts.products.isNotEmpty()) {
            val productId = randomProducts.products.first().id

            // Añadir a favoritos
            favoritesService.addFavorite(token, productId)
            var exists = favoritesService.exists(token, productId)
            assertTrue("El producto debería existir en favoritos después de añadirlo", exists.exists)

            // Remover de favoritos
            favoritesService.removeFavorite(token, productId)
            exists = favoritesService.exists(token, productId)
            assertFalse("El producto no debería existir en favoritos después de removerlo", exists.exists)
        } else {
            assertTrue("No hay productos para probar add/remove favorite", true)
        }
    }

    @Test
    fun testFavorites() = runBlocking {
        val favorites = favoritesService.favorites(token)
        assertNotNull("La lista de favoritos no debería ser null", favorites)
        assertNotNull("La lista de productos favoritos no debería ser null", favorites.favorites)
        // No asumimos que siempre habrá favoritos
    }

    @Test(expected = Exception::class)
    fun testExistsWithInvalidToken(): Unit = runBlocking {
        favoritesService.exists("invalid_token", 1)
    }

    @Test(expected = Exception::class)
    fun testAddFavoriteWithInvalidToken(): Unit = runBlocking {
        favoritesService.addFavorite("invalid_token", 1)
    }

    @Test(expected = Exception::class)
    fun testRemoveFavoriteWithInvalidToken(): Unit = runBlocking {
        favoritesService.removeFavorite("invalid_token", 1)
    }

    @Test(expected = Exception::class)
    fun testFavoritesWithInvalidToken(): Unit = runBlocking {
        favoritesService.favorites("invalid_token")
    }
}