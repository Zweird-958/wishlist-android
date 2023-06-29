package com.example.wishlist_android.api

import com.example.wishlist_android.api.classes.LoginRequest
import com.example.wishlist_android.api.classes.LoginResult
import com.example.wishlist_android.config
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

object RetrofitHelper {

    private val baseUrl = config.api.baseURL

    fun getInstance(): Retrofit {
        return Retrofit.Builder().baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}

interface WishApi {
    @POST("/sign-in")
    suspend fun signIn(@Body loginRequest: LoginRequest): Response<LoginResult>

    @POST("/sign-up")
    suspend fun signUp(@Body loginRequest: LoginRequest): Response<LoginResult>
}