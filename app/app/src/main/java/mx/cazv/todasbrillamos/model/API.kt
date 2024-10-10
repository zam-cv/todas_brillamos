package mx.cazv.todasbrillamos.model

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import mx.cazv.todasbrillamos.model.models.CartResponse
import mx.cazv.todasbrillamos.model.models.Category
import mx.cazv.todasbrillamos.model.models.ChatMessage
import mx.cazv.todasbrillamos.model.models.ClientDetails
import mx.cazv.todasbrillamos.model.models.Credentials
import mx.cazv.todasbrillamos.model.models.Exist
import mx.cazv.todasbrillamos.model.models.Favorites
import mx.cazv.todasbrillamos.model.models.Others
import mx.cazv.todasbrillamos.model.models.PasswordUpdate
import mx.cazv.todasbrillamos.model.models.PaymentConfirmationRequest
import mx.cazv.todasbrillamos.model.models.PaymentConfirmationResponse
import mx.cazv.todasbrillamos.model.models.PaymentIntentResponse
import mx.cazv.todasbrillamos.model.models.Post
import mx.cazv.todasbrillamos.model.models.Product
import mx.cazv.todasbrillamos.model.models.ProductList
import mx.cazv.todasbrillamos.model.models.SignInRequest
import mx.cazv.todasbrillamos.model.models.UserInfo
import retrofit2.HttpException
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

/**
 * Interfaz que define las llamadas a la API.
 *
 * @author Carlos Zamudio
 */
interface API {
    /**
     * Inicia sesión con las credenciales proporcionadas.
     *
     * @param request La solicitud de inicio de sesión.
     * @return Las credenciales del usuario.
     */
    @POST("api/auth/user/signin")
    suspend fun signin(@Body request: SignInRequest): Credentials

    /**
     * Registra un nuevo usuario con la información proporcionada.
     *
     * @param request La información del usuario.
     */
    @POST("api/auth/user/register")
    suspend fun register(@Body request: UserInfo)

    /**
     * Verifica el token de autenticación.
     *
     * @param token El token de autenticación.
     */
    @GET("api/auth/user/verify")
    suspend fun verify(@Header("Authorization") token: String)

    /**
     * Obtiene la información del cliente.
     *
     * @param token El token de autenticación.
     * @return Los detalles del cliente.
     */
    @GET("api/clients")
    suspend fun getClient(@Header("Authorization") token: String): ClientDetails


    /**
     * Actualiza la contraseña del cliente.
     *
     * @param token El token de autenticación.
     * @param request La solicitud de actualización de contraseña.
     */
    @PUT("api/clients/update-password")
    suspend fun updatePassword(
        @Header("Authorization") token: String,
        @Body request: PasswordUpdate
    )

    /**
     * Guarda información adicional.
     *
     * @param token El token de autenticación.
     * @param request La información adicional.
     */
    @POST("api/others")
    suspend fun setOthers(
        @Header("Authorization") token: String,
        @Body request: Others
    )

    /**
     * Actualiza los detalles del cliente.
     *
     * @param token El token de autenticación.
     * @param request Los nuevos detalles del cliente.
     * @return Mensaje de confirmación de la actualización.
     */
    @PUT("api/clients")
    suspend fun updateClientDetails(
        @Header("Authorization") token: String,
        @Body request: ClientDetails
    ): String

    /**
     * Obtiene la lista de productos.
     *
     * @param token El token de autenticación.
     * @return La lista de productos.
     */
    @GET("api/products")
    suspend fun getProducts(@Header("Authorization") token: String): ProductList

    /**
     * Obtiene la lista de categorías de productos.
     *
     * @param token El token de autenticación.
     * @return La lista de categorías.
     */
    @GET("api/categories")
    suspend fun getCategories(@Header("Authorization") token: String): List<Category>

    /**
     * Verifica la existencia de información adicional.
     *
     * @param token El token de autenticación.
     * @return Un objeto Exist que indica si hay información adicional.
     */
    @GET("api/others/exist")
    suspend fun exist(@Header("Authorization") token: String): Exist

    /**
     * Obtiene un producto específico por su ID.
     *
     * @param token El token de autenticación.
     * @param id El ID del producto a obtener.
     * @return El producto solicitado.
     */
    @GET("api/products/{id}")
    suspend fun getProduct(
        @Header("Authorization") token: String,
        @Path("id") id: String
    ): Product

    /**
     * Agrega un producto al carrito de compras.
     *
     * @param token El token de autenticación.
     * @param id El ID del producto a agregar.
     * @param quantity La cantidad del producto a agregar.
     */
    @POST("api/cart/{id}/{quantity}")
    suspend fun addProductToCart(
        @Header("Authorization") token: String,
        @Path("id") id: Int,
        @Path("quantity") quantity: Int
    )

    /**
     * Envía un mensaje de chat.
     *
     * @param token El token de autenticación.
     * @param request El mensaje a enviar.
     * @return El mensaje enviado.
     */
    @POST("api/chat")
    suspend fun sendMessage(
        @Header("Authorization") token: String,
        @Body request: ChatMessage
    ): ChatMessage

    /**
     * Crea un intento de pago.
     *
     * @param token El token de autenticación.
     * @return La respuesta del intento de pago.
     */
    @POST("api/buy/create-payment-intent")
    suspend fun createPaymentIntent(@Header("Authorization") token: String): PaymentIntentResponse

    /**
     * Confirma el pago.
     *
     * @param token El token de autenticación.
     * @param request La solicitud de confirmación de pago.
     * @return La respuesta de confirmación de pago.
     */
    @POST("api/buy/confirm")
    suspend fun confirmPayment(
        @Header("Authorization") token: String,
        @Body request: PaymentConfirmationRequest
    ): PaymentConfirmationResponse

    /**
     * Actualiza la cantidad de un producto en el carrito.
     *
     * @param token El token de autenticación.
     * @param id El ID del producto a actualizar.
     * @param quantity La nueva cantidad del producto.
     */
    @PUT("api/cart/{id}/{quantity}")
    suspend fun updateProductQuantityInCart(
        @Header("Authorization") token: String,
        @Path("id") id: Int,
        @Path("quantity") quantity: Int
    )

    /**
     * Elimina un producto del carrito de compras.
     *
     * @param token El token de autenticación.
     * @param id El ID del producto a eliminar.
     */
    @DELETE("api/cart/{id}")
    suspend fun deleteProductFromCart(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    )

    /**
     * Obtiene el contenido del carrito de compras.
     *
     * @param token El token de autenticación.
     * @return La respuesta del carrito, incluyendo los productos y total.
     */
    @GET("api/cart")
    suspend fun getCart(@Header("Authorization") token: String): CartResponse

    /**
     * Obtiene una lista de productos aleatorios.
     *
     * @param token El token de autenticación.
     * @return La lista de productos aleatorios.
     */
    @GET("api/products/random")
    suspend fun getRandomProduct(@Header("Authorization") token: String): ProductList

    /**
     * Obtiene la lista de publicaciones.
     *
     * @param token El token de autenticación.
     * @return La lista de publicaciones.
     */
    @GET("api/posts")
    suspend fun getPosts(@Header("Authorization") token: String): List<Post>

    @GET("api/favorites/exists/{id}")
    suspend fun existFavorite(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ): Exist

    @POST("api/favorites/{id}")
    suspend fun addFavorite(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    )

    @DELETE("api/favorites/{id}")
    suspend fun deleteFavorite(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    )

    @GET("api/favorites")
    suspend fun getFavorites(@Header("Authorization") token: String): Favorites

    /**
     * Obtiene un post por su ID.
     *
     * @param token El token de autenticación.
     * @return El post solicitado.
     */
    @GET("api/posts/{id}")
    suspend fun getPost(@Header("Authorization") token: String, @Path("id") id: String): Post
}

/**
 * Realiza una llamada a la API y maneja las excepciones.
 *
 * @param call La llamada a la API a realizar.
 * @return El resultado de la llamada a la API.
 */
suspend fun <T> apiCall(call: suspend () -> T): Result<T> = withContext(Dispatchers.IO) {
    try {
        Result.success(call())
    } catch (e: HttpException) {
        println("HTTP Error: ${e.code()} - ${e.message()}")
        Result.failure(e)
    } catch (e: Exception) {
        println("Unexpected error: ${e.message}")
        Result.failure(e)
    }
}