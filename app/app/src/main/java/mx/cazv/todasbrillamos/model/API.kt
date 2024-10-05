package mx.cazv.todasbrillamos.model

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import mx.cazv.todasbrillamos.model.models.Credentials
import mx.cazv.todasbrillamos.model.models.Post
import mx.cazv.todasbrillamos.model.models.ProductList
import mx.cazv.todasbrillamos.model.models.SignInRequest
import mx.cazv.todasbrillamos.model.models.UserInfo
import retrofit2.HttpException
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

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
     * Obtiene el nombre completo del usuario.
     *
     * @param token El token de autenticación.
     * @return El nombre completo del usuario.
     */
    @GET("api/clients/fullname")
    suspend fun getFullName(@Header("Authorization") token: String): String

    /**
     * Obtiene la lista de productos.
     *
     * @param token El token de autenticación.
     * @return La lista de productos.
     */
    @GET("api/products")
    suspend fun getProducts(@Header("Authorization") token: String): ProductList

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