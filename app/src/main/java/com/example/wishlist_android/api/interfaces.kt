package com.example.wishlist_android.api

import com.example.wishlist_android.api.classes.LoginRequest
import com.example.wishlist_android.api.classes.LoginResult
import com.example.wishlist_android.api.classes.WishResult
import com.example.wishlist_android.config
import com.example.wishlist_android.token
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import java.util.Locale


object OkHttpClientHelper {

    fun getInstance(): OkHttpClient {
        return OkHttpClient.Builder()
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
    suspend fun signIn(@Body loginRequest: LoginRequest): Response<LoginResult>

    @POST("/sign-up")
    suspend fun signUp(@Body loginRequest: LoginRequest): Response<LoginResult>

    @GET("/wish")
    suspend fun getWish(): Response<WishResult>
}