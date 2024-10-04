package mx.cazv.todasbrillamos.model

import mx.cazv.todasbrillamos.model.models.Credentials
import mx.cazv.todasbrillamos.model.models.SignInRequest
import mx.cazv.todasbrillamos.model.models.UserInfo
import retrofit2.http.Body
import retrofit2.http.POST

interface API {
    @POST("api/auth/user/signin")
    suspend fun signin(@Body request: SignInRequest): Credentials

    @POST("api/auth/user/register")
    suspend fun register(@Body request: UserInfo): Credentials
}