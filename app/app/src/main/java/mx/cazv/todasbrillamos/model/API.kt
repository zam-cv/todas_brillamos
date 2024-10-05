package mx.cazv.todasbrillamos.model

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import mx.cazv.todasbrillamos.model.models.ClientDetails
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
import retrofit2.http.PUT

interface API {
    @POST("api/auth/user/signin")
    suspend fun signin(@Body request: SignInRequest): Credentials

    @POST("api/auth/user/register")
    suspend fun register(@Body request: UserInfo)

    @GET("api/auth/user/verify")
    suspend fun verify(@Header("Authorization") token: String)

    @GET("api/clients")
    suspend fun getClient(@Header("Authorization") token: String): ClientDetails

    @PUT("api/clients")
    suspend fun updateClientDetails(
        @Header("Authorization") token: String,
        @Body request: ClientDetails
    ): String

    @GET("api/products")
    suspend fun getProducts(@Header("Authorization") token: String): ProductList

    @GET("api/products/random")
    suspend fun getRandomProduct(@Header("Authorization") token: String): ProductList

    @GET("api/posts")
    suspend fun getPosts(@Header("Authorization") token: String): List<Post>
}

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