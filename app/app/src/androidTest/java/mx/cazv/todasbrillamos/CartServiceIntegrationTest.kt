package mx.cazv.todasbrillamos

import kotlinx.coroutines.runBlocking
import mx.cazv.todasbrillamos.model.models.SignInRequest
import mx.cazv.todasbrillamos.model.services.AuthService
import mx.cazv.todasbrillamos.model.services.CartService
import mx.cazv.todasbrillamos.model.services.ProductsService
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class CartServiceIntegrationTest {
    private lateinit var cartService: CartService
    private lateinit var productsService: ProductsService
    private lateinit var authService: AuthService
    private lateinit var token: String

    @Before
    fun setUp() = runBlocking {
        cartService = CartService()
        productsService = ProductsService()
        authService = AuthService()

        // Obtenemos un token válido para las pruebas
        val credentials = authService.signin(SignInRequest("test@example.com", "password123"))
        token = credentials?.token ?: throw IllegalStateException("No se pudo obtener un token válido para las pruebas")
    }

    @Test
    fun testAddProductToCart() = runBlocking {
        val randomProducts = productsService.random(token)
        if (randomProducts.products.isNotEmpty()) {
            val productId = randomProducts.products.first().id
            cartService.addProductToCart(token, productId, 1)

            val cart = cartService.getCart(token)
            assertTrue("El producto debería estar en el carrito", cart.cart.any { it.product.product_id == productId })
        } else {
            assertTrue("No hay productos para probar la adición al carrito", true)
        }
    }

    @Test
    fun testGetCart() = runBlocking {
        val cart = cartService.getCart(token)
        assertNotNull("El carrito no debería ser null", cart)
    }

    @Test
    fun testUpdateProductQuantityInCart() = runBlocking {
        val randomProducts = productsService.random(token)
        if (randomProducts.products.isNotEmpty()) {
            val productId = randomProducts.products.first().id
            cartService.addProductToCart(token, productId, 1)

            cartService.updateProductQuantityInCart(token, productId, 2)

            val cart = cartService.getCart(token)
            val updatedItem = cart.cart.find { it.product.product_id == productId }
            assertEquals("La cantidad del producto debería ser 2", 2, updatedItem?.quantity)
        } else {
            assertTrue("No hay productos para probar la actualización de cantidad", true)
        }
    }

    @Test
    fun testDeleteProductFromCart() = runBlocking {
        val randomProducts = productsService.random(token)
        if (randomProducts.products.isNotEmpty()) {
            val productId = randomProducts.products.first().id
            cartService.addProductToCart(token, productId, 1)

            cartService.deleteProductFromCart(token, productId)

            val cart = cartService.getCart(token)
            assertFalse("El producto no debería estar en el carrito", cart.cart.any { it.product.product_id == productId })
        } else {
            assertTrue("No hay productos para probar la eliminación del carrito", true)
        }
    }

    @Test(expected = Exception::class)
    fun testGetCartWithInvalidToken(): Unit = runBlocking {
        cartService.getCart("invalid_token")
    }
}