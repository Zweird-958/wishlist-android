package com.example.wishlist_android.api

import com.example.wishlist_android.api.classes.CurrencyResult
import com.example.wishlist_android.api.classes.LoginResult
import com.example.wishlist_android.api.classes.SignUpResponse
import com.example.wishlist_android.api.classes.SingleWishResult
import com.example.wishlist_android.api.classes.UserFormBody
import com.example.wishlist_android.api.classes.WishResult
import com.example.wishlist_android.config
import com.example.wishlist_android.token
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import java.util.Locale
import java.util.concurrent.TimeUnit


object OkHttpClientHelper {

    fun getInstance(): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .addInterceptor { chain ->
                val language: String = Locale.getDefault().language

                val request = chain.request().newBuilder()
                    .addHeader("Accept-Language", language)
                    .addHeader("Authorization", token)
                    .build()
                chain.proceed(request)
            }.build()
    }
}

object RetrofitHelper {

    private val baseUrl = config.api.baseURL

    fun getInstance(): Retrofit {
        return Retrofit.Builder().baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(OkHttpClientHelper.getInstance())
            .build()
    }
}

interface WishApi {

    @POST("/sign-in")
    suspend fun signIn(@Body loginRequest: UserFormBody): Response<LoginResult>

    @POST("/sign-up")
    suspend fun signUp(@Body loginRequest: UserFormBody): Response<SignUpResponse>

    @GET("/wish")
    suspend fun getWish(): Response<WishResult>

    @Multipart
    @POST("wish")
    suspend fun createWish(
        @Part("currency") currency: RequestBody,
        @Part("price") price: RequestBody,
        @Part("name") name: RequestBody,
        @Part image: MultipartBody.Part?,
        @Part("link") link: RequestBody?
    ): Response<SingleWishResult>

    @GET("currency")
    suspend fun getCurrencies(): Response<CurrencyResult>

    @DELETE("wish/{id}")
    suspend fun deleteWish(@Path("id") id: Int): Response<SingleWishResult>
}