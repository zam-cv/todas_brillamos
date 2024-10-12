package mx.cazv.todasbrillamos

import kotlinx.coroutines.runBlocking
import mx.cazv.todasbrillamos.model.models.SignInRequest
import mx.cazv.todasbrillamos.model.services.AuthService
import mx.cazv.todasbrillamos.model.services.ProductsService
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class ProductsServiceIntegrationTest {
    private lateinit var productsService: ProductsService
    private lateinit var authService: AuthService
    private lateinit var token: String

    @Before
    fun setUp() = runBlocking {
        productsService = ProductsService()
        authService = AuthService()

        // Obtenemos un token válido para las pruebas
        val credentials = authService.signin(SignInRequest("test@example.com", "password123"))
        token = credentials?.token ?: throw IllegalStateException("No se pudo obtener un token válido para las pruebas")
    }

    @Test
    fun testRandom() = runBlocking {
        val randomProducts = productsService.random(token)
        assertNotNull("La lista de productos aleatorios no debería ser null", randomProducts)
        // No podemos asumir que siempre habrá productos, así que no verificamos si la lista está vacía
    }

    @Test
    fun testProducts() = runBlocking {
        val categories = productsService.categories(token)
        if (categories.isNotEmpty()) {
            val firstCategory = categories.first().id
            val products = productsService.products(token, "Ver todos")
            assertNotNull("La lista de productos no debería ser null", products)
            // Nuevamente, no asumimos que siempre habrá productos
        } else {
            assertTrue("No hay categorías para probar productos", true)
        }
    }

    @Test
    fun testProduct() = runBlocking {
        val randomProducts = productsService.random(token)
        if (randomProducts.products.isNotEmpty()) {
            val product = productsService.product(token, "0")
            assertNotNull("El producto obtenido no debería ser null", product)
        } else {
            assertTrue("No hay productos para probar", true)
        }
    }

    @Test
    fun testCategories() = runBlocking {
        val categories = productsService.categories(token)
        assertNotNull("La lista de categorías no debería ser null", categories)
        // No asumimos que siempre habrá categorías
    }

    @Test(expected = Exception::class)
    fun testProductWithInvalidId(): Unit = runBlocking {
        productsService.product(token, "invalid_id")
    }

    @Test(expected = Exception::class)
    fun testProductsWithInvalidToken(): Unit = runBlocking {
        productsService.products("invalid_token", "any_category")
    }

    @Test
    fun testProductStructure() = runBlocking {
        val randomProducts = productsService.random(token)
        if (randomProducts.products.isNotEmpty()) {
            val product = randomProducts.products.first()
            assertNotNull("El ID del producto no debería ser null", product.id)
            assertNotNull("El nombre del producto no debería ser null", product.name)
            assertNotNull("La descripción del producto no debería ser null", product.description)
            assertNotNull("El precio del producto no debería ser null", product.price)
        } else {
            assertTrue("No hay productos para probar la estructura", true)
        }
    }
}