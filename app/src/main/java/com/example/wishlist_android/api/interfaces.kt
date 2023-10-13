package com.example.wishlist_android.api

import android.content.Context
import com.example.wishlist_android.api.classes.ApiResponse
import com.example.wishlist_android.api.classes.ShareResult
import com.example.wishlist_android.api.classes.ShareWishlistBody
import com.example.wishlist_android.api.classes.SignUpResult
import com.example.wishlist_android.api.classes.UserFormBody
import com.example.wishlist_android.classes.User
import com.example.wishlist_android.classes.Wish
import com.example.wishlist_android.config
import com.example.wishlist_android.utils.getToken
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
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import java.util.Locale
import java.util.concurrent.TimeUnit


object OkHttpClientHelper {

    fun getInstance(context: Context): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .addInterceptor { chain ->
                val language: String = Locale.getDefault().language

                val request = chain.request().newBuilder()
                    .addHeader("Accept-Language", language)
                    .addHeader("Authorization", getToken(context) ?: "")
                    .build()
                chain.proceed(request)
            }.build()
    }
}

object RetrofitHelper {

    private val baseUrl = config.api.baseURL

    fun getInstance(context: Context): Retrofit {
        return Retrofit.Builder().baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(OkHttpClientHelper.getInstance(context))
            .build()
    }
}

interface WishApi {

    @POST("/sign-in")
    suspend fun signIn(@Body loginRequest: UserFormBody): Response<ApiResponse<String>>

    @POST("/sign-up")
    suspend fun signUp(@Body loginRequest: UserFormBody): Response<ApiResponse<SignUpResult>>

    @GET("/wish")
    suspend fun getWish(): Response<ApiResponse<List<Wish?>>>

    @Multipart
    @POST("wish")
    suspend fun createWish(
        @Part("currency") currency: RequestBody,
        @Part("price") price: RequestBody,
        @Part("name") name: RequestBody,
        @Part image: MultipartBody.Part?,
        @Part("link") link: RequestBody?
    ): Response<ApiResponse<Wish?>>

    @GET("currency")
    suspend fun getCurrencies(): Response<ApiResponse<List<String>>>

    @DELETE("wish/{id}")
    suspend fun deleteWish(@Path("id") id: Int): Response<ApiResponse<Wish>>

    @Multipart
    @PATCH("wish/{id}")
    suspend fun editWish(
        @Path("id") id: Int,
        @Part("currency") currency: RequestBody,
        @Part("price") price: RequestBody,
        @Part("name") name: RequestBody,
        @Part image: MultipartBody.Part?,
        @Part("link") link: RequestBody?,
        @Part("purchased") purchased: RequestBody
    ): Response<ApiResponse<Wish>>

    @GET("share/wish")
    suspend fun getSharedUsers(): Response<ApiResponse<List<User>?>>

    @POST("share/wish")
    suspend fun addSharedUser(@Body shareRequest: ShareWishlistBody): Response<ApiResponse<ShareResult>>

    @GET("share/wish/{id}")
    suspend fun getSharedWish(@Path("id") id: Int): Response<ApiResponse<List<Wish>>>

    @GET("share/users")
    suspend fun getUsersSharedWith(): Response<ApiResponse<List<User>?>>

    @DELETE("share/wish/{id}")
    suspend fun unshareWish(@Path("id") id: Int): Response<ApiResponse<ShareResult>>
}